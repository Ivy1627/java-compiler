public class Decl {
    private DeclInt di;
    private DeclArr da;
    void parse(MyScanner s){
        if(s.currentToken() == Core.INTEGER){
            di = new DeclInt();
            di.parse(s);
            //System.out.println("TEST: " + s.currentToken());
        }
        else if(s.currentToken() == Core.ARRAY){
            da = new DeclArr();
            da.parse(s);
            //System.out.println("TEST: " + s.currentToken());
        }
    }

    void print(){
        if(di!=null){
            di.print();
        }
        else if(da!=null){
            da.print();
        }
    }

    void execute(){
        //System.out.println("in decl, dsFlag is " + Memory.dsFlag);
        if(di!=null){
            di.execute();
        }
        else if(da!=null){
            da.execute();
        }
    }
}
