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
import java.util.List;

/**
 *
 * @author Fabien Barbero
 */
public final class ArgumentsList {

    private final List<String> argsList;

    ArgumentsList(String... args) {
        argsList = Arrays.asList(args);
    }

    /**
     * Get the number of arguments.
     * @return The number of arguments.
     */
    public int size() {
        return argsList.size();
    }

    /**
     * Get an argument at a given index.
     * @param index The index reached.
     * @return      The argument found.
     */
    public String getArgumentAt(int index) {
        return argsList.get(index);
    }

    /**
     * Indicates if a given argument is present.
     * @param arg   The argument to find.
     * @return      true if the argument is present, otherwise false.
     */
    public boolean containsArgument(String arg) {
        return argsList.contains(arg);
    }

    /**
     * Get the next value of an argument. If there is the arguments "-pid 2547"
     * and you give <I>arg="-pid"</I>, the method will return <I>2547</I>.
     * @param arg   The argument reached.
     * @return      The following argument of the given argument. It will returns
     *              null if the given argument is not found or if there are no
     *              following argument.
     */
    public String getNextArgumentValue(String arg) {
        for (int i = 0; i < argsList.size(); i++) {
            if (argsList.get(i).equals(arg)) {
                if (i == argsList.size() - 1) {
                    return null;    // No following argument
                } else {
                    return argsList.get(i + 1);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return argsList.toString();
    }

}
