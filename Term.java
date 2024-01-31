public class Term {
    private Factor fc;
    private Term t_m;
    private Term t_d;
    
    void parse(MyScanner s){
        fc = new Factor();
        fc.parse(s);
        if(s.currentToken() == Core.MULTIPLY){
            t_m = new Term();
            //consume the *
            s.nextToken();
            t_m.parse(s);
        }
        else if(s.currentToken() == Core.DIVIDE){
            t_d = new Term();
            //consume the /
            s.nextToken();
            t_d.parse(s);
        }
    }

    void print(){
        if(fc!=null){
            fc.print();
        }
        if(t_m!=null){
            System.out.print("*");
            t_m.print();
        }
        else if(t_d!=null){
            System.out.print("/");
            t_d.print();
        }
    }

    int execute(){
        int value = 0;
        if(fc!=null){
            value = fc.execute();
        }
        if(t_m!=null){
            value *= t_m.execute();
        }
        else if(t_d!=null){
            if(t_d.execute() == 0){
                System.out.println("ERROR: division by 0");
                System.exit(1);
            }
            else{
                value /= t_d.execute();
            }
        }

        return value;
    }

}
