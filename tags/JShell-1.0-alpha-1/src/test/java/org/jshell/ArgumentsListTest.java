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

import org.junit.Test;
import static junit.framework.Assert.*;

/**
 *
 * @author Fabien Barbero
 */
public class ArgumentsListTest {

    @Test
    public void testArguments() {
        ArgumentsList list = new ArgumentsList("ps", "-e", "aux");
        assertEquals(3, list.size());
        assertEquals("ps", list.getArgumentAt(0));
        assertEquals("-e", list.getArgumentAt(1));
        assertEquals("aux", list.getArgumentAt(2));

        assertTrue(list.containsArgument("-e"));
        assertFalse(list.containsArgument("grep"));

        assertEquals("-e", list.getNextArgumentValue("ps"));
        assertNull(list.getNextArgumentValue("aux"));
    }

}
