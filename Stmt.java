public class Stmt {
    private Assign as;
    private IF if_stm;
    private Loop loop;
    private Out out;
    private IN in;
    private Decl dc;
    private Call call;

    void parse(MyScanner s){
        Core curr = s.currentToken();
        if(curr == Core.ID){
            as = new Assign();
            as.parse(s);
        }
        else if(curr == Core.IF){
            if_stm = new IF();
            if_stm.parse(s);
        }
        else if(curr == Core.WHILE){
            loop = new Loop();
            loop.parse(s);
        }
        else if(curr == Core.IN){
            in = new IN();
            in.parse(s);
        }
        else if(curr == Core.OUT){
            out = new Out();
            out.parse(s);
        }
        else if(curr == Core.INTEGER || curr == Core.ARRAY){
            dc = new Decl();
            dc.parse(s);
        }
        else if(curr == Core.BEGIN){
            call = new Call();
            call.parse(s);
        }
    }

    void print(){
        if(as!=null){
            as.print();
        }
        else if(if_stm!=null){
            if_stm.print();
        }
        else if(loop!=null){
            loop.print();
        }
        else if(out!=null){
            out.print();
        }
        else if(in!=null){
            in.print();
        }
        else if(dc!=null){
            dc.print();
        }
        else if(call!=null){
            call.print();
        }
    }
        
    
    void execute(){
        if(as!=null){
            as.execute();
        }
        else if(if_stm!=null){
            if_stm.execute();
        }
        else if(loop!=null){
            loop.execute();
        }
        else if(out!=null){
            out.execute();
        }
        else if(in!=null){
            in.execute();
        }
        else if(dc!=null){
            dc.execute();
        }
        else if(call!=null){
            call.execute();
        }
    }
}
