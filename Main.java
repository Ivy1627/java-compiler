import java.util.HashMap;
import java.util.Stack;

class Main {
	private static MyScanner scanner;
	public static void main(String[] args) {
		scanner = new MyScanner(args[0]);
        Memory.s2 = new MyScanner(args[1]);
        Memory.global = new HashMap<>();
        Memory.frames = new Stack<>();
        Memory.functionList = new HashMap<>();
        Procedure.parse_proc(scanner);
        Procedure.executeProcedure();
    }
}
