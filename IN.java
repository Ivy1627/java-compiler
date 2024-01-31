public class IN {
    private String id;
    private int input;

    void parse(MyScanner s){
        // get rid of "in"
        s.nextToken();
        if(s.currentToken() != Core.LPAREN){
            System.out.println("ERROR: ( expected");
        }
        else{
            //System.out.print("(");
            s.nextToken();
            id = s.getId();
            s.nextToken();
            Syntax.checkRP(s);
            Syntax.checkSC(s);
        }
    }
    
    void print(){
        System.out.print("    in(");
        if(!id.isEmpty()){
            System.out.print(id);
        }
        System.out.print(");\n");
    }

    void execute(){
        if(Memory.s2.currentToken() != Core.EOS){
            input = Memory.s2.getConst();
            Memory.store(id, input);
            //System.out.println("input from data is " +input);
            Memory.s2.nextToken();
        }
        else{
            System.out.println("ERROR: .data file not having enough values.");
            System.exit(1);
        }
    }

}
