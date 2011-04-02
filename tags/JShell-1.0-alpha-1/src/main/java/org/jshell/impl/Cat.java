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
package org.jshell.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import org.jshell.AbstractShellCommand;
import org.jshell.ArgumentsList;
import org.jshell.ShellBuffer;
import org.jshell.ShellIO;

/**
 *
 * @author Fabien Barbero
 */
public class Cat extends AbstractShellCommand {

    public Cat() {
        super("cat");
    }

    @Override
    protected String getHelpMessage() {
        return "prints the content of a file";
    }

    @Override
    protected void executeCommand(ArgumentsList args, ShellBuffer inputBuffer, ShellIO handler) throws Exception {
        String filePath = args.getArgumentAt(0);
        if(filePath == null) {
            handler.println("No file to print");
            return;
        }
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = fileReader.readLine()) != null) {
                handler.println(line);
            }
        } finally {
            if(fileReader != null) {
                fileReader.close();
            }
        }
    }

}
