public class DeclArr {
    private String id;
    void parse(MyScanner s){
        // consume "array"
        s.nextToken();
        id = s.getId();
        // consume the ;
        s.nextToken();
        Syntax.checkSC(s);
    }

    void print(){
        System.out.print("    array " + id + ";\n");
    }

    void execute(){
        Memory.declare(id, Core.ARRAY);
    }

}
