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
package org.freedesktop.wayland;

import java.io.StringWriter;

public class Interface
{
    public static class Message
    {
        private String name;
        private String signature;
        private String javaSignature;
        private Interface[] types;

        public Message(String name, String signature, Interface[] types)
        {
            this.name = name;
            this.signature = signature;
            this.types = types;

            StringWriter writer = new StringWriter();

            writer.write("(");
            int tpos = 0;
            for (int spos = 0; spos < signature.length(); ++spos) {
                switch(signature.charAt(spos)) {
                case '?':
                    // Skip '?' characters
                    continue;
                case 'i':
                    writer.write("I");
                    break;
                case 'u':
                    writer.write("I");
                    break;
                case 'f':
                    writer.write("Lorg.freedesktop.wayland.Fixed;");
                    break;
                case 's':
                    writer.write("Ljava.lang.String;");
                    break;
                case 'o':
                    writer.write("L" + types[tpos].clazz.getName() + ";");
                    break;
                case 'n':
                    writer.write("I");
                    break;
                case 'a':
                    writer.write("[B");
                    break;
                case 'h':
                    writer.write("I");
                    break;
                default:
                    // Skip unknown characters. Throw exception here?
                    continue;
                }
                ++tpos;
            }
            writer.write(")V");
        }

        public Message(String name, String signature, String javaSignature,
                Interface[] types)
        {
            this.name = name;
            this.signature = signature;
            this.javaSignature = javaSignature;
            this.types = types;
        }
    }

    private String name;
    private Class<?> clazz;
    private int version;
    private Message[] requests;
    private Message[] events;
    private long interface_ptr;

    // I need to get rid of this ASAP
    private long implementation_ptr;

    public Interface(String name, Class<?> clazz, int version,
            Message[] requests, Message[] events)
    {
        this.name = name;
        this.clazz = clazz;
        this.version = version;
        this.requests = requests;
        this.events = events;
        this.interface_ptr = 0;
        this.implementation_ptr = 0;

        createNative();
    }

    public Interface(String name, Class<?> clazz, int version,  
            Message[] requests, Message[] events, long implementation_ptr)
    {
        this(name, clazz, version, requests, events);

        this.implementation_ptr = implementation_ptr;
    }

    private native void createNative();
    private native void destroyNative();

    @Override
    public void finalize() throws Throwable
    {
        destroyNative();
        super.finalize();
    }

    private static native void initializeJNI();
    static {
        System.loadLibrary("wayland-java-server");
        initializeJNI();
    }
}

