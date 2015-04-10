Jshell is a simple shell implementation in Java.
The project contains some default commands (ie: cat, grep ...)
Il allows to pipe the out of a command to an other (like in a real shell!)

## Configuration of the shell ##
By default no command are present in the shell, you have to add them and start the shell like in the example below :
```
import org.jshell.impl.Cat;
import org.jshell.impl.Exit;
import org.jshell.impl.Grep;

public class Test {
    
    public static void main(String[] args) {
        try {
            Shell shell = new Shell();
            shell.registerCommand(new Grep());
            shell.registerCommand(new Cat());
            shell.registerCommand(new Exit());

            shell.start();
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}

```