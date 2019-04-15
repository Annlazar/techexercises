to execute as jar file, run 
mvn package
in the project, and run the folders-0.0.1-SNAPSHOT.jar with 
java -jar folders-0.0.1-SNAPSHOT.jar
command, but before test it with the jar file, copy src\main\resources\ next to the jar file, as:
-folders-0.0.1-SNAPSHOT.jar
-testtree.txt
-readable.txt
-writable.txt

The 3 txt files contains the initial test TreeItem, the readable folders  and the writable folders, these can be changed for tests.
The result treeitem is printed out in the command line / console in the last line, like:
"Only writable treeitem: [[var[users[]]]]"
