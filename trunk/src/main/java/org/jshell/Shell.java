/**
 * Copyright (c) 2010 Fabien Barbero
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jshell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jline.ConsoleReader;
import jline.FileNameCompletor;
import jline.SimpleCompletor;

/**
 *
 * @author Fabien Barbero
 */
public class Shell {

    private static final String SPLIT_REGEX = "\\s+(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))";

    private final Set<ShellCommand> commands = new HashSet<ShellCommand>();

    private String header;
    private String prompt = "> ";
    

    /**
     * Set a header to the shell. This text will be displed at the start of the shell.
     * @param header The text to display at the start.
     */
    public final void setHeader(String header) {
        this.header = header;
    }

    /**
     * Set a prompt text to the shell.
     * @param prompt The value of the prompt.
     */
    public final void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Register a command.
     * @param command The command to register.
     */
    public final synchronized void registerCommand(ShellCommand command) {
        commands.add(command);
    }

    /**
     * Unregister a command.
     * @param command The command to unregister.
     */
    public final synchronized void unregisterCommand(ShellCommand command) {
        commands.remove(command);
    }

    /**
     * Get the list of the registered commands.
     * @return The registered commands.
     */
    public ShellCommand[] getRegisteredCommands() {
        return commands.toArray(new ShellCommand[]{});
    }

    /**
     * Start of the shell.
     */
    public final synchronized void start() throws IOException {
        ConsoleReader reader = new ConsoleReader();
        List<String> commandsName = new ArrayList<String>();
        for(ShellCommand command : commands) {
            commandsName.add(command.name());
        }
        reader.addCompletor(new FileNameCompletor());
        reader.addCompletor(new SimpleCompletor(commandsName.toArray(new String[]{})));
        reader.setDefaultPrompt(prompt);

        ShellIO shellIO = new SystemShellIO(reader);

        if(header != null) {
            shellIO.println(header);
            shellIO.println();
        }
        
        while(true) {
            shellIO.print(prompt);
            String line = shellIO.readLine();

            ShellBuffer buffer = null;
            String[] commandsStr = line.split("\\|");

            for(int i=0; i<commandsStr.length; i++) {
                ShellIO commandIO;
                if(i == commandsStr.length -1) {
                    commandIO = shellIO;
                } else {
                    commandIO = new ShellIO(reader);
                }
                String[] splitCommand = splitCommandLine(commandsStr[i].trim());
                if(splitCommand.length > 0) {
                    ShellCommand shellCommand = findCommandByName(splitCommand[0].trim());
                    if(shellCommand == null) {
                        shellIO.println("No command found with name '" + splitCommand[0] + "'");
                        break;
                    } else {
                        try {
                            ArgumentsList argumentsList = new ArgumentsList(Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
                            shellCommand.execute(argumentsList, buffer, commandIO);
                            buffer = commandIO.getBuffer();
                        } catch(Exception ex) {
                            ex.printStackTrace();
                            break;
                        }
                    }
                }
            }
            
            shellIO.println();
        }
    }

    // FIXME : to improve
    // Method tested in unit test
    final String[] splitCommandLine(String line) {
        String[] split = line.split(SPLIT_REGEX);
        for(int i=0; i<split.length; i++) {
            split[i] = split[i].replace("\"", "");
        }
        return split;
    }

    private ShellCommand findCommandByName(String name) {
        for(ShellCommand command : commands) {
            if(command.name().equals(name)) {
                return command;
            }
        }
        return null;
    }
}
