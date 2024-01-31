public class Assign {
    private Expr e1;
    private Expr e2;
    private Expr e3;
    private Expr e4;
    private String id1;
    private String id2;

    void parse(MyScanner s){
        id1 = s.getId();
        s.nextToken();
        if(s.currentToken() == Core.ASSIGN){
            //consume ":=";
            s.nextToken();
            if(s.currentToken() == Core.ARRAY){
                //consume "array";
                s.nextToken();
                id2 = s.getId();
                //consume id2;
                s.nextToken();
                Syntax.checkSC(s);
            }
            else if(s.currentToken() == Core.NEW){
                s.nextToken();
                if(s.currentToken() != Core.INTEGER){
                    System.out.println("ERROR: integer expected");
                    System.exit(1);
                }
                else{
                    e4 = new Expr();
                    //consume "integer";
                    s.nextToken();
                    Syntax.checkLB(s, e4);
                    Syntax.checkSC(s);
                }
            }
            else{
                e1 = new Expr();
                e1.parse(s);
                //s.nextToken();
                Syntax.checkSC(s);
            }
        }
        else if(s.currentToken() == Core.LBRACE){
            e2 = new Expr();
            //consume "[";
            s.nextToken();
            e2.parse(s);
            //!!NO NEED to do nextToken after parsing exp, scanner already moves on
            Syntax.checkRB(s);
            if(s.currentToken() != Core.ASSIGN){
                System.out.println("ERROR: ':=' expected");
                System.exit(1);
            }
            else{
                // get rid of the :=
                s.nextToken();
                e3 = new Expr();
                e3.parse(s);
                Syntax.checkSC(s);
            }
        }
        else if(s.currentToken() == Core.SEMICOLON){
            System.out.println("ERROR: missing assignment");
            System.exit(1);
        }
        else{
            System.out.println("ERROR: invalid assignment");
            System.exit(1);
        }
    }

    void print(){
        if(id1!=null){
            System.out.print("    " + id1);
            if(e2!=null){
                System.out.print("[");
                e2.print();
                System.out.print("]:=");
                e3.print();
            }
            else{
                System.out.print(":=");
                if(e1!=null){
                    e1.print();
                }
                else if(id2!=null){
                    System.out.print(" array " + id2);
                }
                else if(e4!=null){
                    System.out.print("new integer[");
                    e4.print();
                    System.out.print("]");
                }
            }
        }
        System.out.print(";\n");
    }

    void execute(){
        if(id1!=null){
            if(e2!=null){
                int index = e2.execute();
                int val = e3.execute();
                Memory.storeArray(id1, index, val);
                //System.out.println(Memory.local.peek().get(id1).arrVal[0]);
            }
            else{
                if(e1!=null){
                    int res = e1.execute();
                    if(Memory.isInt(id1)){
                        Memory.store(id1, res);
                    }
                    else{
                        Memory.storeArray(id1, 0, res);
                    }
                    //System.out.println(id1 + " stored as " + Memory.local.peek().get(id1).intVal);
                }
                else if(id2!=null){
                    //System.out.print(" array " + id2);
                    //Memory.copyArray(id1, id2);
                    Memory.updateRef(id1, id2);
                }
                else if(e4!=null){
                    //System.out.print("new integer");
                    int length = e4.execute();
                    Memory.initArray(id1, length);

                }
            }
        }
    }

}
