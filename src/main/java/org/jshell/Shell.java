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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Fabien Barbero
 */
public class Shell {

    private static final String SPLIT_REGEX = "\\s+(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))";

    private final ShellHandler shellHandler = new ShellHandler();
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
    public final synchronized void start() {
        if(header != null) {
            shellHandler.println(header);
            shellHandler.println();
        }
        
        while(true) {
            shellHandler.println(prompt);
            String line = shellHandler.readLine();

            String[] splittedLine = splitCommandLine(line);
            if(splittedLine.length > 0) {
                ShellCommand command = findCommandByName(splittedLine[0]);
                if(command == null) {
                    
                } else {
                    try {
                        command.execute(new ArgumentsList(Arrays.copyOfRange(splittedLine, 1, splittedLine.length)),
                                        shellHandler);
                    } catch(Exception ex) {
                        shellHandler.printException(ex);
                    }
                }
            }
            shellHandler.println();
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
            if(command.name().equals(name));
        }
        return null;
    }
}
