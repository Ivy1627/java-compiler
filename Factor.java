import java.util.HashMap;

public class Factor {
    // e1 is the expression in id[e1]
    private Expr e1;
    // e2 is the expression in (e2)
    private Expr e2;
    private String id;
    private String cst;

    void parse(MyScanner s){
        Core curr = s.currentToken();
        if(curr == Core.ID){
            id = s.getId();
            /*
             if(!Semantics.exists(id)){
                System.out.println("ERROR: " + id + " is not declared yet");
                System.exit(1);
             }
             else{
                // consume the id
                s.nextToken();
                if(s.currentToken() == Core.LBRACE){
                    s.nextToken();
                    e1 = new Expr();
                    e1.parse(s);
                    Syntax.checkRB(s);
                }
             }
             */
            s.nextToken();
            if(s.currentToken() == Core.LBRACE){
                s.nextToken();
                e1 = new Expr();
                e1.parse(s);
                Syntax.checkRB(s);
            }
        }
        else if(curr == Core.CONST){
            cst = Integer.toString(s.getConst());
            //consume the symbol after the number
            s.nextToken();
        }
        else if(curr == Core.LPAREN){
            e2 = new Expr();
            //consume "(";
            s.nextToken();
            e2.parse(s);
            Syntax.checkRP(s);
        }
        else {
            id = s.getId();
            // consume the * or /
            s.nextToken();
        }
    }

    void print(){
        if(id!=null){
            System.out.print(id);
            if(e1!=null){
                System.out.print("[");
                e1.print();
                System.out.print("]");
            }
        }
        else if(cst!=null){
            System.out.print(cst);
        }
        else if(e2!=null){
            System.out.print("(");
            e2.print();
            System.out.print(")");
        }
    }

    int execute(){
        int result = 0;
        if(id!=null){
            HashMap<String, Memory.Value> scope = Memory.findVar(id);
            //System.out.println("the scope containing x is " + scope);
            if(e1!=null){
                if(scope == null){
                    System.out.println(id + " is out of scope");
                    System.exit(1);
                }
                else{
                    // why arr is null??
                    int[] arr = scope.get(id).arrVal;
                    int index = e1.execute();
                    result = arr[index];
                }
            }
            else{
                if(scope == null){
                    System.out.println(id + " is out of scope");
                    System.exit(1);
                }
                // if id is array, set result to id[0]
                else if(scope.get(id).type == Core.ARRAY){
                    result = scope.get(id).arrVal[0];
                }
                // else if id is int, set result to id
                else{
                    result = scope.get(id).intVal;
                }
            }
        }
        else if(cst!=null){
            result = Integer.parseInt(cst);
        }
        else if(e2!=null){
            result = e2.execute();
        }
        
        return result;
    }
}
