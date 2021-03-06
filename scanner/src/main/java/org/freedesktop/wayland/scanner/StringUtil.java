/*
 * Copyright © 2012-2013 Jason Ekstrand.
 *  
 * Permission to use, copy, modify, distribute, and sell this software and its
 * documentation for any purpose is hereby granted without fee, provided that
 * the above copyright notice appear in all copies and that both that copyright
 * notice and this permission notice appear in supporting documentation, and
 * that the name of the copyright holders not be used in advertising or
 * publicity pertaining to distribution of the software without specific,
 * written prior permission.  The copyright holders make no representations
 * about the suitability of this software for any purpose.  It is provided "as
 * is" without express or implied warranty.
 * 
 * THE COPYRIGHT HOLDERS DISCLAIM ALL WARRANTIES WITH REGARD TO THIS SOFTWARE,
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS, IN NO
 * EVENT SHALL THE COPYRIGHT HOLDERS BE LIABLE FOR ANY SPECIAL, INDIRECT OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE,
 * DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER
 * TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
 * OF THIS SOFTWARE.
 */
package org.freedesktop.wayland.scanner;

import java.io.Writer;
import java.io.StringWriter;

class StringUtil
{
    public static String toJNIName(String name)
    {
        StringWriter writer = new StringWriter();
        for (int i = 0; i < name.length(); ++i) {
            char c = name.charAt(i);
            switch (c) {
            case '/':
                writer.write("_");
                break;
            case '_':
                writer.write("_1");
                break;
            case ';':
                writer.write("_2");
                break;
            case '[':
                writer.write("_3");
                break;
            default:
                writer.write(c);
            }
        }
        return writer.toString();
    }

    public static String toUpperCamelCase(String string)
    {
        String[] words = string.split("_");
        string = "";
        for (int i = 0; i < words.length; ++i) {
            String firstChar = words[i].substring(0, 1);
            string = string + firstChar.toUpperCase() + words[i].substring(1);
        }
        return string; 
    }

    public static String toLowerCamelCase(String string)
    {
        String[] words = string.split("_");
        string = words[0];
        for (int i = 1; i < words.length; ++i) {
            String firstChar = words[i].substring(0, 1);
            string = string + firstChar.toUpperCase() + words[i].substring(1);
        }
        return string; 
    }
}

