import java.util.HashMap;

public class Loop {
    private Cond c;
    private StmtSeq ss;

    void parse(MyScanner s){
        // get rid of "while"
        s.nextToken();
        c = new Cond();
        c.parse(s);
        if(s.currentToken() != Core.DO){
            System.out.println("ERROR: do expected but is " + s.currentToken());
            System.exit(1);
        }
        else{
            // get rid of "do"
            s.nextToken();
            ss = new StmtSeq();
            ss.parse(s);
            Syntax.checkEND(s);
        }
    }

    void print(){
        System.out.print("while ");
        if(c!=null){
            c.print();
            System.out.println(" do");
            if(ss!=null){
                ss.print();
                System.out.println("    end");
            }
        }
    }

    void execute(){
        Memory.frames.peek().push(new HashMap<>());
        if(c!=null){
            //System.out.println("cond is" + c.execute());
            while(c.execute()){
                //System.out.println("while loop entered");
                if(ss!=null){
                    ss.execute();
                }
            }
        }
        Memory.derefLocal();
        Memory.frames.peek().pop();
    }
    
}
