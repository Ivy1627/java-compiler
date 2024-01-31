public class Expr {
    private Term t;
    private Expr e_plus;
    private Expr e_minus;

    //assume expression is term + expr for now
    void parse(MyScanner s){
        t = new Term();
        t.parse(s);
        //get the ; or + or -
        String curr = s.currentToken().name();
        if(curr.equals("ADD")){
            e_plus = new Expr();
            //consume the +
            s.nextToken();
            if(s.currentToken() == Core.SEMICOLON){
                System.out.println("ERROR: missing expression");
                System.exit(1);
            }
            e_plus.parse(s);
        }
        else if(curr.equals("SUBTRACT")){
            e_minus = new Expr();
            //consume the -
            s.nextToken();
            if(s.currentToken() == Core.SEMICOLON){
                System.out.println("ERROR: missing expression");
                System.exit(1);
            }
            e_minus.parse(s);
        }
    }

    void print(){
        t.print();
        if(e_plus!=null){
            System.out.print("+");    
            e_plus.print();
        }
        else if(e_minus!=null){
            System.out.print("-");
            e_minus.print();
        }
    }

    int execute(){
        int value = t.execute();
        if(e_plus!=null){
            value += e_plus.execute();
        }
        else if(e_minus!=null){
            value -= e_minus.execute();
        }
        return value;
    }

}
