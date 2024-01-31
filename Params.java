import java.util.ArrayList;

public class Params {
    private String id;
    private Params p;
    ArrayList<String> res = new ArrayList<>();

    Params(ArrayList<String> result) {
        this.res = result;
    }

    // used when call and funcion to call parse p, where res is initally null
    Params(){
    }

    void parse(MyScanner s){
        id = s.getId();
        if(!res.contains(id)){
            res.add(id);
        }
        else{
            System.out.println("ERROR: duplicate parameters");
            System.exit(1);
        }
        // consume id
        s.nextToken();
        // if more than 1 param
        if(s.currentToken() == Core.COMMA){
            // consume ","
            s.nextToken();
            p = new Params(res);
            p.parse(s);
        }else if(s.currentToken() == Core.RPAREN){
            s.nextToken();
            return;
        }
        else{
            System.out.println("ERROR: ) expected but is " + s.currentToken());
            System.exit(1);
        }
        return;
    }

    void print(){
        System.out.print(id);
        if(p!=null){
           System.out.print(", ");
           p.print();
        }
    }
}
