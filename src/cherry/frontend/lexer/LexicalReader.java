/*
 * The MIT License
 *
 * Copyright 2017 SoraKatadzuma.
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
package cherry.frontend.lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public class LexicalReader {
    /** The file this reader will be reading from. */
    public File file;
    /** A reference to the beginning of a file. */
    public static final int ORIGIN = 0;
    /** Tells whether or not the end of a file has been reached. */
    public boolean EOF = false;
    /** A class utilized for file hopping. */
    private FileChannel fchan;
    /** The reader this reader is based off. */
    private FileInputStream stream;
    /** The current position in the file. */
    private int cursor = 0;
    /** The offset between the current cursor and the last cursor. */
    private int offset = 0;
    /** A place to store the cursors that. */
    private final Stack<Integer> cursors = new Stack<>();

    /**
     * Default constructor.
     * 
     * @param input
     */
    public LexicalReader (File input) {
        try {
            this.stream = new FileInputStream(input);
            this.fchan = this.stream.getChannel();
            this.file = input;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LexicalReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The standard integer read method. It is the origin of all reads within the
     * compiler.
     * 
     * @return The integer representation of an input in a file.
     */
    public int read () {
        int in = -1;
        
        try {
            in = stream.read();
        } catch (IOException ex) {
            Logger.getLogger(LexicalReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cursor++;
        
        if (in == -1) { EOF = true; }
        
        return(in);
    }

    /**
     * The {@code skip()} method is a way of "skipping" unnecessary input without
     * reading it explicitly.
     * 
     * @param n The number of inputs within a file, to skip.
     * @return The number of inputs skipped.
     */
    public long skip (long n) {
        cursors.push(cursor);
        cursor += n;
        long ff = 0;
        
        try {
            ff = stream.skip(n);
        } catch (IOException ex) {
            Logger.getLogger(LexicalReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return(ff);
    }

    /**
     * The {@code seek()} method is a way of moving a specified amount of inputs
     * forward or backwards. It utilizes the FileChannel class that places the cursor
     * at a given position in the file. It is effective in small amounts. Larger
     * amounts may cause efficiency decrease.
     * 
     * @param n The number of inputs to seek.
     */
    public void seek (int n) {
        try {
            cursor += n;
            fchan.position(cursor);
        } catch (IOException ex) {
            Logger.getLogger(LexicalReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** A method by which the file stream can be closed. */
    public void close () {
        try {
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(LexicalReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** A method for keeping track of the current file position. */
    public void setNewCursor () {
        offset = cursor;
        cursors.push(offset);
    }

    /** A method for returning to the previous file position set by {@code setNewCursor()}. */
    public void previousCursor () {
        if (!cursors.empty()) {
            try {
                fchan.position(cursors.pop());
            } catch (IOException ex) {
                Logger.getLogger(LexicalReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * A character formatted version of the {@code read()} method.
     * 
     * @return The character format of the {@code read()} method.
     */
    public char readChar () { return((char) read()); }

    /**
     * If necessary, this method allows the reader to read and entire line.
     * @return A string containing the entire contents of a line in the source.
     */
    public String readLine () {
        StringBuilder sb = new StringBuilder();
        char ch = ' ';

        /* readChar() which stems from read() will throw an error if one occurs */
        while (ch != '\n' && !EOF) { ch = readChar(); sb.append(ch); }

        return(sb.toString());
    }
}
