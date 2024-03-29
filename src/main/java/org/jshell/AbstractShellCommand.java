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

/**
 *
 * @author Fabien Barbero
 */
public abstract class AbstractShellCommand implements ShellCommand {

    private static final String HELP_ARGUMENT = "-help";

    private final String name;

    public AbstractShellCommand(String name) {
        this.name = name;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final void execute(ArgumentsList args, ShellBuffer inputBuffer, ShellIO handler) throws Exception {
        if(args.containsArgument(HELP_ARGUMENT)) {
            handler.println(getHelpMessage());
        } else {
            executeCommand(args, inputBuffer, handler);
        }
    }

    protected abstract String getHelpMessage();

    protected abstract void executeCommand(ArgumentsList args, ShellBuffer inputBuffer, ShellIO handler) throws Exception;

    @Override
    public String toString() {
        return name;
    }

}
