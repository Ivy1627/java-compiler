public class DeclInt {
    private String id;
    void parse(MyScanner s){
        s.nextToken();
        id = s.getId();
        // consume the ;
        s.nextToken();
        Syntax.checkSC(s);
    }

    void print(){
        System.out.print("    integer " + id + ";\n");
    }

    void execute(){
        Memory.declare(id, Core.INTEGER);
    }
}
