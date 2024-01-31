import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Function {
    private String id;
    private Params p;
    private StmtSeq ss;
    static ArrayList<String> formal_p;

    void parse(MyScanner s){
        if(s.currentToken() != Core.PROCEDURE){
            System.out.print("ERROR: procedure expected");
            System.exit(1);
        }
        s.nextToken();
        if(s.currentToken() != Core.ID){
            System.out.println("ERROR: ID expected but is " + s.currentToken());
            System.exit(1);
        } else{
            id = s.getId();
        }
        s.nextToken();
        Syntax.checkLP(s);
        // first set of params is ID
        if(s.currentToken() == Core.ID){
            p = new Params();
            if(containsDuplicates(p.res)){
                formal_p = null;
                System.out.println("ERROR: " + id + " duplicate formal parameters");
                System.exit(1);
            }
            else{
                p.parse(s);
                formal_p = p.res;
            }
        }
        else{
            System.out.println("ERROR: ID expected but is " + s.currentToken());
            System.exit(1);
        }

        if(s.currentToken() != Core.IS){
            System.out.println("ERROR: IS expected but is " + s.currentToken());
            System.exit(1);
        }
        // get rid of "is"
        s.nextToken();
        if(s.currentToken()==Core.END){
            System.out.println("Error function body missing (no stmt-seq).");
            System.exit(1);
        }
        ss = new StmtSeq();
        ss.parse(s);
        Syntax.checkEND(s);

        Memory.FuncInfo fi = new Memory.FuncInfo();
        fi.ss = ss;
        fi.p = formal_p;
        if(!Memory.functionList.containsKey(id)){
            Memory.functionList.put(id, fi);
        }
        else{
            System.out.println("ERROR: duplicate function name");
            System.exit(1);
        }
    }

    static boolean containsDuplicates(ArrayList<String> res) {
        Set<String> set = new HashSet<>();
        for (String element : res) {
            if (set.contains(element)) {
                return true;
            }
            set.add(element);
        }
        return false;
    }

    void print(){
        System.out.print("  procedure " + id + "(");
        if(p!=null){
            p.print();
        }
        System.out.println(") is ");
        if(ss!=null){
            ss.print();
        }
        System.out.println("end");
    }

}
