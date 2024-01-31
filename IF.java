import java.util.HashMap;

public class IF {
    private Cond c;
    private StmtSeq ss1;
    private StmtSeq ss2;

    void parse(MyScanner s){
        s.nextToken();
        c = new Cond();
        //System.out.print("    if ");
        c.parse(s);
        //System.out.println("token before parsing then is " + s.getId());
        if(s.currentToken() == Core.THEN){
            ss1 = new StmtSeq();
            // consume "then"
            s.nextToken();
            ss1.parse(s);
            if(s.currentToken() == Core.ELSE){
                ss2 = new StmtSeq();
                // consume "else"
                s.nextToken();
                ss2.parse(s);
            }
            //System.out.println("the current scope is " + Memory.local.peek());
            Syntax.checkEND(s);
        }
        else{
            System.out.println("ERROR: then expected but is " + s.currentToken());
            System.exit(1);
        }
    }

    // the indentation of nested if is not handled, all if,else,end are aligned
    void print(){
        System.out.print("    if ");
        c.print();
        if(ss1!=null){
            System.out.println(" then");
            ss1.print();
            if(ss2!=null){
                System.out.println("    else");
                ss2.print();
                System.out.println("    end");
            }
            else{
                System.out.println("    end");
            }
        }
    }

    void execute(){
        // simply create an empty scope instead of the top scope,
        // otherwise updated values won't be stored in the correct scope
        Memory.frames.peek().push(new HashMap<>());
        if(c.execute()==true){
            if(ss1!=null){
                ss1.execute();
            }
        }
        else{
            if(ss2!=null){
                ss2.execute();
            }
        }
        Memory.derefLocal();
        Memory.frames.peek().pop();
    }
}
