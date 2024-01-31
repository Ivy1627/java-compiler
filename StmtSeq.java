class StmtSeq{
    private StmtSeq ss;
    private Stmt st;

    void parse(MyScanner s){
        //Memory.local.push(Memory.global);
        st = new Stmt();
        st.parse(s);

        String curr = s.currentToken().name();
        if(curr.equals("ID") || curr.equals("IF") 
        || curr.equals("WHILE") || curr.equals("OUT") 
        || curr.equals("INTEGER") || curr.equals("ARRAY") || curr.equals("IN")
        || curr.equals("BEGIN")){
            ss = new StmtSeq();
            ss.parse(s);
        }
    }

    void print(){
        if(st!=null){
            st.print();
        }
        if(ss!=null){
            ss.print();
        }
    }

    void execute(){
        if(st!=null){
            st.execute();
        }
        if(ss!=null){
            ss.execute();
        }
    }
}