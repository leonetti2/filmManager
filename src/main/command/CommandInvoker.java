package main.command;

/**
 * @author lucio
 */
public class CommandInvoker {
    public void executeCommand(Command command){
        command.execute();
    }
}
