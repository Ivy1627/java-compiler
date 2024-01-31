class Syntax {
    public static void checkRB(MyScanner s){
        //s.nextToken();
        if(s.currentToken() != Core.RBRACE){
            System.out.print("ERROR: ] expected");
            System.out.print(" but is: " + s.currentToken());
            System.exit(1);
        }
        else{
            //System.out.print("]");
            // consume the ]
            s.nextToken();
            //System.out.print("TEST2: " + s.currentToken());
        }
    }

    public static void checkSC(MyScanner s){
        //s.nextToken();
        if(s.currentToken() != Core.SEMICOLON){
            System.out.print("Error: semicolon expected");
            System.exit(1);
        }   
        else {
            // consume the ;
            s.nextToken();
        }
    }

    public static void checkLB(MyScanner s, Expr e4){
        //s.nextToken();
        if(s.currentToken() != Core.LBRACE){
            System.out.println("Error: [ expected");
            System.exit(1);
        }   
        else {
            //System.out.print("[");
            s.nextToken();
            e4.parse(s);
            Syntax.checkRB(s);
        }
    }

    public static void checkRP(MyScanner s){
        if(s.currentToken() != Core.RPAREN){
            System.out.print("Error: ) expected but is " + s.currentToken());
            System.exit(1);
        }   
        else {
            //System.out.print(")");
            // consume the )
            s.nextToken();
        }
    }

    public static void checkLP(MyScanner s){
        if(s.currentToken() != Core.LPAREN){
            System.out.print("Error: ( expected but is " + s.currentToken());
            System.exit(1);
        }   
        else {
            //System.out.print(")");
            // consume the )
            s.nextToken();
        }
    }

    public static void checkEND(MyScanner s){
        if(s.currentToken() != Core.END){
            System.out.println("ERROR: end expected but is " + s.currentToken());
            System.exit(1);
        }
        else{
            //System.out.println("end");
            // consume the end token
            s.nextToken();
        }
    }
}