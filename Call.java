import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Call {
    private String id;
    private Params p;
    static ArrayList<String> actual_p;

    void parse(MyScanner s){
        if(s.currentToken() == Core.BEGIN){
            // consume begin and get id
            s.nextToken();
        }
        else{
            System.out.println("ERROR: begin expected but is " + s.currentToken());
            System.exit(1);
        }
        
        if(s.currentToken() != Core.ID){
            System.out.println("ERROR: ID expected but is " + s.currentToken());
            System.exit(1);
        } else{
            id = s.getId();
            // consume id and ( and
            s.nextToken();
            s.nextToken();
        }

        // first set of params is ID
        if(s.currentToken() == Core.ID){
            p = new Params();
            p.parse(s);
            actual_p = p.res;
        }
        else{
            System.out.println("ERROR: ID expected but is " + s.currentToken());
            System.exit(1);
        }
        Syntax.checkSC(s);
    }

    void print(){
        System.out.print("    begin " + id + "(");
        p.print();
        System.out.println(");");
    }

    void execute(){
        Memory.FuncInfo funcInfo = null;
        if(Memory.functionList.containsKey(id)){
            funcInfo = Memory.functionList.get(id);
        }
        else{
            System.out.println("ERROR: calling a function that has not been declared.");
            System.exit(1);
        }
        ArrayList<String> formal_p = funcInfo.p;
        Stack<HashMap<String, Memory.Value>> newStack = new Stack<>();
        HashMap<String, Memory.Value> globalCopy = Memory.global;
        //System.out.println("current global is: "+globalCopy);
        newStack.push(globalCopy); 
        newStack.push(new HashMap<String, Memory.Value>());
        Memory.frames.push(newStack);
        Memory.passParams(actual_p, formal_p);
        Memory.incRef(actual_p);
        //System.out.println("actual_p is: " + actual_p);
        StmtSeq ss = funcInfo.ss;
        ss.execute();
        //Memory.decRef(actual_p);
        Memory.derefLocal();
        Memory.frames.pop();
    }

}
