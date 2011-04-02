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
import jline.ConsoleReader;

/**
 *
 * @author Fabien Barbero
 */
public class ShellIO {

    private final ShellBuffer buffer = new ShellBuffer();
    private final ConsoleReader reader;

    ShellIO(ConsoleReader reader) {
        this.reader = reader;
    }


    /**
     * Ask a text in the shell.
     * @return The text typed in the shell.
     */
    public final String readLine() {
        try {
            return reader.readLine().trim();
        } catch (IOException ex) {
            throw new UnsupportedOperationException("Error reading line", ex);
        }
    }

    /**
     * Print a line in the shell.
     * @param str The line to print.
     */
    public void println(String str) {
        buffer.addLine(str);
    }

    void print(String str) {
    }

    /**
     * Print an empty line in the shell.
     */
    public void println() {
    }

    /**
     * Get the input buffer (can be provided by an other command).
     * @return The buffer.
     */
    ShellBuffer getBuffer() {
        return buffer;
    }

}
