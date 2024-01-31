import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Memory {

    static class Value{
        Core type;
        int intVal;
        int[] arrVal;
    }

    static class FuncInfo{
        StmtSeq ss;
        ArrayList<String> p;
    }

    static HashMap<String, Value> global;
    //static Stack<HashMap<String, Value>> local;
    static Stack<Stack<HashMap<String, Value>>> frames;
    static boolean dsFlag;
    static MyScanner s2;
    static HashMap<String, FuncInfo> functionList;
    static int gcCount = 0;
    

    // var is already declared, look up based on var to get its location and type
    // var = val or var[0] = val
    static void store(String var, int val){
        // look for the map where var is stored, not necessarily the top
        HashMap<String, Value> scopeToAdd = findVar(var);
        Core type = scopeToAdd.get(var).type;
        // note that no need to create a new var since the recursion will need the same var.
        setByType(type, scopeToAdd.get(var), val);
        //System.out.println(var + " updated to :" + scopeToAdd.get(var).intVal);
    }

    // declare var, var is put on top of the stack
    static void declare(String var, Core type){
        Value value = new Value();
        if(type == Core.INTEGER){
            value.type = Core.INTEGER;
        }
        else{
            value.type = Core.ARRAY;
        }
        // need to handle the recursion case, but how to check if a function calls itself??
        if(!dsFlag){
            frames.peek().peek().put(var, value);
        }
        // if var is global
        else{
            global.put(var, value);
        }
    }

    /*
     * var is a declared array
     * var[inedx] = valToSet
     * pass global as m if the array to change is in the global scope
     */
    static void storeArray(String var, int index, int valToSet){
        HashMap<String, Value> scope = findVar(var);
        Value value = scope.get(var);
        if(value.arrVal==null){
            System.out.println("assignment to null array "+ var);
            System.exit(1);
        }
        else if(index<value.arrVal.length){
            value.arrVal[index] = valToSet;
        }
        else{
            System.out.println("ERROR: Index " + index + " out of bounds for length " + value.arrVal.length);
            System.exit(1);
        }
        //local.peek().put(var, value);
        frames.peek().peek().put(var, value);
        //System.out.println("index " + index + " set to " + valToSet);
    }

    static void initArray(String var, int length){
        HashMap<String, Value> scope = findVar(var);
        if (scope.containsKey(var)) {
            // Find the array to change and update its value
            Value value = scope.get(var);
            value.type = Core.ARRAY;
            value.arrVal = new int[length+1];
            value.arrVal[value.arrVal.length - 1] = 1;
            gcCount++;
            System.out.println("gc:" + gcCount);
            //local.peek().put(var, value);
            frames.peek().peek().put(var, value);
            //System.out.println("array " + var + " of length " + length + " added to memory");
        }
        else{
            System.out.println("ERROR: " + var + "not declared yet.");
            System.exit(1);
        }
    }
    
    // make var1 refer to the array that var2 refers to
    static void copyArray(String var1, String var2){
        HashMap<String, Value> scope = findVar(var2);
        if (scope.containsKey(var2)) {
            //local.peek().put(var1, scope.get(var2));
            frames.peek().peek().put(var1, scope.get(var2));
            //updateRef(var1, var2);
        }
        else{
            System.out.println("ERROR: " + var2 + "not declared yet.");
            System.exit(1);
        }
    }

    static void setByType(Core type, Value value, int val){
        if(type == Core.INTEGER){
            value.type = Core.INTEGER;
            value.intVal = val;
        }
        else{
            if(value.arrVal!=null){
                value.type = Core.ARRAY;
                value.arrVal[0] = val;
            }
        }
    }

    // find the scope(hashmap) that var is in
    static HashMap<String, Value> findVar(String var){
        HashMap<String, Value> scope = null;
        // Iterate through the outermost stack
        for (Stack<HashMap<String, Value>> f : frames) {
            for (HashMap<String, Value> innerMap : f) {
                if (innerMap.containsKey(var)) {
                    scope = innerMap;
                    break;
                }
            }
        }

        return scope;
    }

    static Boolean isInt(String var){
        if(findVar(var).get(var).type == Core.INTEGER){
            return true;
        }
        else{
            return false;
        }
    }

    // ceate a new varaible var2(copy of var1) and put it on top of the frames
    static void copyVar(String var1, String var2){
        HashMap<String, Value> scope = findVar(var1);
        Value value = scope.get(var1);
        Value newVal = new Value();
        newVal.arrVal = value.arrVal;
        newVal.type = value.type;
        //System.out.println("top map is: " + frames.peek().peek());
        frames.peek().peek().put(var2, newVal);
    }

    static void passParams(ArrayList<String> actual_p, ArrayList<String> formal_p){
        for(int i = 0; i<formal_p.size(); i++){
            copyVar(actual_p.get(i), formal_p.get(i));
        }
    }

    static boolean declared(String var){
        if(findVar(var) != null){
            return true;
        }else{
            return false;
        }
    }

    // increment the reference count for the actual parameter, since formal param refers to it
    static void incRef(ArrayList<String> actual_p){
        for(int i = 0; i < actual_p.size(); i++){
            String var = actual_p.get(i);
            Value v = findVar(var).get(var);
            v.arrVal[v.arrVal.length-1]++;
        }
    }

    // increase refCount on the object that var2 is referring to when var1:= array var2 only when var2 is initialized
    // and decrement refCount on the object that var1 was referring to 
    static void updateRef(String var1, String var2){
        Value v1 = findVar(var1).get(var1);
        Value v2 = findVar(var2).get(var2);

        // if var2 is initialized
        if(v2.arrVal != null){
            v2.arrVal[v2.arrVal.length-1]++;
        }
        if(v1.arrVal != null) {
            v1.arrVal[v1.arrVal.length-1]--;
            if (v1.arrVal[v1.arrVal.length-1] == 0) {
                gcCount--;
                System.out.println("gc:" + gcCount);
                System.out.println("ref updated");
            }
        }

        v1.arrVal = v2.arrVal;
    }

    // whenever reaching end,  derefLocal all non-global objects on top of the stack
    static void derefLocal() {
        HashMap<String, Value> scope = frames.peek().peek();
        for (Map.Entry<String, Value> entry : scope.entrySet()) {
            Value value = entry.getValue();
            // derefence any objects other than the global ones
            if (value.type == Core.ARRAY && value.arrVal != null) {
                value.arrVal[value.arrVal.length-1]--;
                if(value.arrVal[value.arrVal.length-1] == 0){
                    gcCount--;
                    System.out.println("gc:" + gcCount);
                    System.out.println("local gone out of scope");
                }
            }
        }
    }

    static void derefGlobal(){
        for(Map.Entry<String, Value> entry : global.entrySet()){
            Value value = entry.getValue();
            if (value.type == Core.ARRAY && value.arrVal != null) {
                value.arrVal[value.arrVal.length-1]--;
                if(value.arrVal[value.arrVal.length-1] == 0){
                    gcCount--;
                    System.out.println("gc:" + gcCount);
                    System.out.println("global gone out of scope");
                }
            }
        }
    }
}