class DeclSeq {
    private Decl d;
    private DeclSeq ds;
    private Function f;
    private DeclSeq ds2;

    void parse(MyScanner s){
        if(s.currentToken() == Core.INTEGER || s.currentToken() == Core.ARRAY){
            d = new Decl();
            d.parse(s);
            if(s.currentToken() == Core.INTEGER || s.currentToken() == Core.ARRAY || s.currentToken() == Core.PROCEDURE){
                ds = new DeclSeq();
                ds.parse(s);
            }
        }
        else if(s.currentToken() == Core.PROCEDURE){
            f = new Function();
            f.parse(s);
            if(s.currentToken() == Core.INTEGER || s.currentToken() == Core.ARRAY || s.currentToken() == Core.PROCEDURE){
                ds2 = new DeclSeq();
                ds2.parse(s);
            }
        }
    }

    void print(){
        if(d!=null){
            d.print();
        }
        if(ds!=null){
            ds.print();
        }
        if(f!=null){
            f.print();
        }
        if(ds2!=null){
            ds2.print();
        }
    }

    public void execute(){
        if(d!=null){
            d.execute();
        }
        if(ds!=null){
            ds.execute();
        }
        if(ds2!=null){
            ds2.execute();
        }
    }
}