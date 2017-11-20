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
package cherry.frontend.grammar;

/**
 * The {@code Token} class is a singular output of the {@code Lexer}. The
 * {@code Lexer} is built to return an array of tokens to the {@code Parser}.
 * <p>
 * A {@code Token} is comprised of: a type, a value (the lexeme found by the
 * {@code Lexer}), a line position in the file from which the value was read,
 * and a column position in the line from which this was read (usually refers to
 * the first character of the lexeme; its column position).
 * </p>
 * @author SoraKatadzuma
 */
public class Token {
    /**
     * The type of a token is found using the methods of the {@code Type} enum
     * found here. Each type is composed of a number value which will be used to
     * fill in the transition table of the {@code Lexer}.
     */
    public enum Type {
        id(0);
        
        /** The number of this type. */
        private final int index;
        
        /**
         * Constructs each value with a number representing their index.
         * @param index The number of this type.
         */
        Type (int index) {
            this.index = index;
        }
    }
}
