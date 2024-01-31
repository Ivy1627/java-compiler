import java.util.HashMap;
import java.util.Stack;

class Procedure {
    private static String id;
    private static DeclSeq ds;
    private static StmtSeq ss;

    public static void parse_proc(MyScanner s){
        
        if(s.currentToken() != Core.PROCEDURE){
            System.out.print("ERROR: procedure expected");
            System.exit(1);
        }
        s.nextToken();
        if(s.currentToken() != Core.ID){
            System.out.println("ERROR: ID expected");
            System.exit(1);
        } else{
            id = s.getId();
        }
        s.nextToken();
        if(s.currentToken() != Core.IS){
            System.out.println("ERROR: IS expected");
            System.exit(1);
        }

        s.nextToken();
        if(s.currentToken() == Core.PROCEDURE || s.currentToken() == Core.INTEGER
        || s.currentToken() == Core.ARRAY){
            ds = new DeclSeq();
            ds.parse(s);
        }
        
        if(s.currentToken() != Core.BEGIN){
            System.out.println("ERROR: begin expected but is " + s.currentToken());
        }

        s.nextToken();
        ss = new StmtSeq();
        ss.parse(s);
        
        if(s.currentToken() != Core.END){
            System.out.println("ERROR: end expected but is " + s.currentToken());
            System.exit(1);
        }
        // get rid of "end"
        s.nextToken();
        if(s.currentToken() != Core.EOS){
            System.out.println("ERROR: EOS expected");
            System.exit(1);
        }
    }

    public static void printProcedure(){
        System.out.println("procedure " + id + " is");
        if(ds!=null){
            ds.print();
        }
        System.out.println("begin");
        if(ss!=null){
            ss.print();
        }
        System.out.println("end");
    }

    public static void executeProcedure(){
        if(ds!=null){
            // why dsflag is always false??
            Memory.dsFlag = true;
            ds.execute();
        }
        Memory.dsFlag = false;
        //Memory.local.push(new HashMap<>(Memory.global));
        HashMap<String, Memory.Value> globalCopy = Memory.global;
        Stack<HashMap<String, Memory.Value>> InitialStack = new Stack<>();
        InitialStack.push(globalCopy); 
        Memory.frames.push(InitialStack);
        ss.execute();
        Memory.derefGlobal();
        Memory.frames.peek().pop();
    }
}