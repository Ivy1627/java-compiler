public class Out {
    private Expr e;

    void parse(MyScanner s){
        s.nextToken();
        if(s.currentToken() != Core.LPAREN){
            System.out.println("ERROR: ( expected");
        }
        else{
            // get rid of"("
            s.nextToken();
            e = new Expr();
            e.parse(s);
            Syntax.checkRP(s);
            Syntax.checkSC(s);
        }
    }

    void print(){
        System.out.print("    out(");
        if(e!=null){
            e.print();
        }
        System.out.print(");\n");
    }

    void execute(){
        if(e!=null){
            System.out.println(e.execute());
        }
    }
}
