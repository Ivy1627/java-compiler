Introduction:
The program parses and executes a program given the input program defined by the following grammer, syntax and semantics are fully checked.

<procedure> ::= procedure ID is <decl-seq> begin <stmt-seq> end 
 | procedure ID is begin <stmt-seq> end
<decl-seq> ::= <decl > | <decl><decl-seq> | <function> | <function><decl-seq>
<stmt-seq> ::= <stmt> | <stmt><stmt-seq> 
<decl> ::= <decl-integer> | <decl-array> 
<decl-integer> ::= integer id ; 
<decl-array> ::= array id ; 
<function> ::= procedure ID ( <parameters> ) is <stmt-seq> end
<parameters> ::= ID | ID , <parameters> 
<stmt> ::= <assign> | <if> | <loop> | <out> | <in> | <decl> | <call> 
<call> ::= begin ID ( <parameters> ) ;
<assign> ::= id := <expr> ; | id [ <expr> ] := <expr> ; | id := new integer [ <expr> ]; | id := array id ; 
<out> ::= out ( <expr> ) ; 
<in> ::= in ( id ) ;
<if> ::= if <cond> then <stmt-seq> end 
 | if <cond> then <stmt-seq> else <stmt-seq> end
<loop> ::= while <cond> do <stmt-seq> end
<cond> ::= <cmpr> | not <cond> | <cmpr> or <cond> | <cmpr> and <cond> 
<cmpr> ::= <expr> = <expr> | <expr> < <expr> 
<expr> ::= <term> | <term> + <expr> | <term> – <expr> 
<term> ::= <factor> | <factor> * <term> | <factor> / <term> 
<factor> ::= id | id [ <expr> ] | const | ( <expr> )

An example is:
procedure t5 is
	procedure A(x) is
		array y;
		y := new integer[1]; 
		y[0] := 8; 
		out(x); 
		x := array y; 
	end
	integer m;
	integer n;
	array z;
begin
	m := 10;
	n := 5;
	z := new integer[1];
	z[0] := m+n;
	begin A(z);
	out(m);
end

A simple data file is served as the input the program takes.

The project contains:
MyScanner.java: The scanner that tokenizes the stream from project 1.
Main.java: create the stack to track variables and parse and execute the procedure.
Memory.java: contains global, frames, dsFlag, functionList and gcCount as static members, and Value as a nested class. 
args[0] is the scanner passed to parseProcedure(); args[1] is the scanner to get the input. 
Procedure.java: contains members decl-seq, stmt-seq and id; parse and execute the procedure tree.
DeclSeq.java: contains members decl and decl-seq; parse and execute the decl-seq tree
Decl.java: contains members declInt and declArr; parse and execute the decl tree.
DeclInt.java: contains imember d; parse and execute the decl-int tree.
DeclArr.java: contains member id; parse and execute the declArr tree.
Stmt.java: contains members assign, if, loop, out, in and decl; parse and execute the assign tree.
Assign.java: has two ids and four expressions as member of the tree; parse and execute the assign tree.
Out.java: has exp as the member; parse and execute the out tree.
In.java: has id as the member; parse and execute the in tree.
If.java: has a cond and two stmtseqs as members; assign, parse and execute the if tree.
Loop.java: contains a cond and a stmtseq; parse and execute the loop tree.
Cond.java: has one cmpr and three cond as members; parse and execute the cond tree.
Cmpr.java: has 3 expression as members; parse and execute the cmpr tree.
Expr.java: has a term and 2 expr as members; parse and execute the expr tree.
Term.java: has a factor and 2 terms as members; parse and execute the term tree.
Factor.java: has 2 expr and 2 strings (const and id) as members; parse and execute the factor tree.
Function.java: parse and execute the Function tree.
Params.java:  parse and ececute the Params tree.
Call.java: parse and execute the Call tree.

Special features:
Memory.java: updateRef, incRef, derefLocal and derefGlobal were added to track how many objects are active.
An element is added to the last index of each Value.arrVal to represent the reference count for this array object.
The ref count for an object is incremented whenever it is created, or another var is assigned to point to it.
The ref count for an object is decremented when it goes out of the current scope or when an object pointing at it points to another object(a:=array b).
Call.java: Once the actual parameters are passed to the actual parameters, incRef is called so that the reference count for the object that the formal parameter is pointing at increments by 1. Recursion is supported.
Assign.java: updateRef is performed when a:= array b
IF.java: derefLocal is performed before the current scope is popped off.
While.java: derefLocal is performed before the current scope is popped off.
Procedure.java: derefGlobal is performed before the program ends.

How to run the program:
In a java compiler, run javac Main.java, then Pass the name of the program filen as the first argument, and the name of the data file as the second argument, then run:
java Main arg1 arg2
The program should give the expected output for any program following the grammer, otherwise errors will be reported.
