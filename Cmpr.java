public class Cmpr {
    private Expr e1;
    private Expr e2;
    private Expr e3;

    void parse(MyScanner s){
        e1 = new Expr();
        e1.parse(s);
        
        if(s.currentToken() == Core.EQUAL) {
            e2 = new Expr();
            //get rid of =;
            s.nextToken();
            e2.parse(s);
        }
        else if(s.currentToken() == Core.LESS){
            e3 = new Expr();
            //get rid of <;
            s.nextToken();
            e3.parse(s);
        }
        else{
            System.out.println("ERROR: missing the right expression");
            System.exit(1);
        }
    }

    void print(){
        if(e1!=null){
            e1.print();
            if(e2!=null){
                System.out.print("=");
                e2.print();
            }
            else if(e3!=null){
                System.out.print("<");
                e3.print();              
            }
        }
    }

    boolean execute(){
        boolean res = false;
        if(e1!=null){
            int left = e1.execute();
            int right = 0;
            if(e2!=null){
                right = e2.execute();
                if(left == right){
                    res = true;
                }
            }
            else if(e3!=null){
                right = e3.execute();
                if(left < right){
                    res = true;
                }         
            }
        }
        return res;
    }
}