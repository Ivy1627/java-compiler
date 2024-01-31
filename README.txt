Jingxin Zhang

No files were added. Memory.java, Cal.java and Assign.java, IF.java, Loop.java, and Procedure.java were modified to implement the garbage collection.
And the modifications are as follows:

Memory.java: updateRef, incRef, derefLocal and derefGlobal were added to track how many objects are active.
Call.java: Once the actual parameters are passed to the actual parameters, incRef is called so that the reference count for the object that the formal parameter is pointing at increments by 1.
Assign.java: updateRef is performed when a:= array b
IF.java: derefLocal is performed before the current scope is poped off.
While.java: derefLocal is performed before the current scope is poped off.
Procedure.java: derefGlobal is performed before the program ends.


The project contains:
MyScanner.java: The scanner that tokenizes the stream from project 1.
Main.java: create the stack to track variables and parse and execute the procedure.
Memory.java: contains global, frames, dsFlag, functionList and gcCount as static members.=, and Value as a nested class. 
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

Description:
An element is added to the last index of each Value.arrVal to represent the reference count for this array object.
The ref count for an object is incremented whenever it is created, or another var is assigned to point to it.
The ref count for an object is decremented when it goes out of the current scope or when an object pointing at it points to another object(a:=array b).

Known bugs:
The program had a stackoverflow for test case 8, which is due to the improper handling of parameters.

Commets:
All print functions were kept.
