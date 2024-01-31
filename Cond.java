public class Cond {
    private Cmpr cp;
    private Cond cd_not;
    private Cond cd_or;
    private Cond cd_and;
    
    void parse(MyScanner s){
        String curr = s.currentToken().name();
        if(curr.equals("NOT")){
            // consume NOT
            s.nextToken();
            cd_not = new Cond();
            cd_not.parse(s);
        }
        else{
            cp = new Cmpr();
            cp.parse(s);
            curr = s.currentToken().name();
            if(curr.equals("OR")){
                //get rid of "or"
                s.nextToken();
                cd_or = new Cond();
                cd_or.parse(s);
            }
            else if(curr.equals("AND")){
                //get rid of and
                s.nextToken();
                cd_and = new Cond();
                cd_and.parse(s);
            }
        }
    }

    void print(){
        if(cd_not!=null){
            System.out.print("not ");
            cd_not.print();
        }
        else{
            if(cp!=null){
                cp.print();
                if(cd_or!=null){
                    System.out.print(" or ");
                    cd_or.print();
                }
                else if(cd_and!=null){
                    System.out.print(" and ");
                    cd_and.print();
                }
            }
        }
    }

    boolean execute(){
        boolean res = true;
        if(cd_not!=null){
            boolean cn = cd_not.execute();
            res = !cn;
        }
        else if(cp!=null){
            boolean first = cp.execute();
            if(cd_or!=null){
                res = first || cd_or.execute();
            }
            else if(cd_and!=null){
                res = first && cd_and.execute();
            }
            else{
                res = first;
            }
        }
        return res;
    }

}
