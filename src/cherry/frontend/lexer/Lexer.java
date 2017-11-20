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

/**
 * The sole purpose of Lexer is to take a file and make sure that is has proper
 * grammatical strings and then turn those strings into tokens that the parser
 * will use and understand. The string from here on will be known as lexemes and
 * the process of turning them into tokens, tokenization. It is important that
 * the {@code Lexer} be built as a finite automata. We can do this in two ways:
 *      1. Provide the {@code Lexer} the proper and needed information to be a
 *         Finite Automaton.
 *      2. Have a class dedicated to building simple and effective Finite
 *         Automata for not only this class, but others within this project.
 * <p>
 * We choose to do the former to keep our code simple and connected without
 * introducing classes that don't necessarily need to be in this project. This
 * also means that the Lexer has more control over how it's Automaton is made.
 * </p>
 * <p>
 * We will use a similar method to that of the Parser Generator by creating a
 * pseudo Lexer-Generator. To do this is simple, we create a static instance on
 * the first call to the Lexer, or perhaps before the first instance that
 * generates the transition table that each Lexer will use. Accesses to this
 * table will need to be synchronizable, as to prevent interruptions and other
 * nasty concurrency issues. 
 * </p>
 * <p>
 * In order to do what we are talking about, the {@code Lexer} must have a set
 * of states that it can be in, and a set of symbols that it could potentially
 * find. So we provide the {@code State} in the {@code Lexer} for this very
 * reason. We can easily get our inputs from the {@code Token.Type} enum. Values
 * from {@code Token.Type} will be put together with the {@code State} enum
 * values to create keys. A two dimensional array using the OR'd version of
 * these two values will access the second dimension of the array which will
 * contain the {@code Lexer}'s available state transition. If there is a
 * transition to multiple states, we will need to do a slight lookahead to peek
 * at the value of the next character to decide the transition to take.
 * </p>
 * @author SoraKatadzuma
 */
public final class Lexer {
    /**
     * The {@code State} enum is a utility enum that allows the Lexer to build
     * its automaton transition table from. It contains 7 states that the
     * {@code Lexer} can be in at any given time.
     */
    private enum State {
        /** The start state of the {@code Lexer}. */
        start(1),
        /** The word state of the {@code Lexer}. */
        word(2),
        /** The number state of the {@code Lexer}. */
        number(3),
        /** The literal state of the {@code Lexer}. */
        literal(4),
        /** The symbol state of the {@code Lexer}. */
        symbol(5),
        /** The error state of the {@code Lexer}. */
        error(6),
        /** The final state of the {@code Lexer}. */
        stop(7);
        
        /** The index of the state. */
        private final int index;
        
        /**
         * Constructs each {@code State} value with an number corresponding to
         * its index.
         * @param index The numbered index of this state.
         */
        State (int index) { this.index = index; }
        
        /**
         * Retrieves the index of this state.
         * @return The number of this state.
         */
        public int getStateNo () { return index; }
    }
    
    /** Sets up the transition table. */
    static {
        transTable = buildTransitions();
    }
    
    /** The transition table of the {@code Lexer}. */
    private static final int[][] transTable;
    /** File that this Lexer will be reading. */
    private final File file;
    /** The reader for this Lexer. */
    private final LexicalReader reader;
    
    /**
     * Constructs a new Lexer to lex the file passed in from the Parser that
     * instantiated this Lexer instance.
     * @param file The file to lex.
     */
    public Lexer (File file) {
        this.file = file;
        reader = new LexicalReader(file);
    }
    
    /**
     * Builds the corresponding transition table for the {@code Lexer}.
     * @return A integer two dimensional array filled with every key and state
     *      transition.
     */
    private static int[][] buildTransitions () {
        int[][] result = new int[7][5];
        // fill table
        return result;
    }
}
