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

import java.util.EnumSet;

/**
 * The {@code Token} class is a singular output of the {@code Lexer}. The
 * {@code Lexer} is built to return an array of tokens to the {@code Parser}.
 * <p>
 * A {@code Token} is comprised of: a type, a value (the lexeme found by the
 * {@code Lexer}), a line position in the file from which the value was read,
 * and a column position in the line from which this was read (usually refers to
 * the first character of the lexeme; its column position).
 * </p>
 * 
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public class Token {
    /**
     * The type of a token is found using the methods of the {@code Type} enum
     * found here. Each type is composed of a number value which will be used to
     * fill in the transition table of the {@code Lexer}.
     */
    public enum Type {
        /* Parser dependent types. */
        UNDEFINED, ID, METHODID, NUMBER, LITERAL, LETTER, REAL, HEXADECIMAL,
        OCTAL, BINARY, UNICODE, LONGNUM, TRUE, FALSE, ASM, EOTS,
        
        /* Data and object types. */
        BOOL, BYTE, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, STRING, VOID, PTR,
        REF, CLASS, ENUM, INTERFACE, STRUCT,
        
        /* The modifier types. */
        PUBLIC, PRIVATE, PROTECTED, INTERNAL, ABSTRACT, EXTERNAL, FINAL,
        IMMUTABLE, STATIC, VOLATILE,
        
        /* Other Keywords. */
        BREAK, CASE, CATCH, CONTINUE, DO, DEFAULT, ELSE, FINALLY, FOR,
        FOREACH, GET, IF, IN, INHERITS, LAMBDA, OPERATOR, NEW, PARAMS, RETURN,
        SET, SIZEOF, SKIP, SUPER, SWITCH, THIS, TRY, USE, VALUES, WHILE,
        
        /* Math keywords. */
        EXP, SQRT, LOG, LN, COS, SIN, TAN, CSC, SEC, COT,
        
        /* Symbols. */
        ADD("+"), SUB("-"), MUL("*"), DIV("/"), MOD("%"), ASSIGN("="),
        LESS("<"), GREAT(">"), NOT("!"), ADDEQ("+="), SUBEQ("-="), MULTEQ("*="),
        DIVEQ("/="), MODEQ("%="), LESSEQ("<="), GREATEQ(">="), EQUALS("=="),
        NOTEQ("!="), BWAND("&"), BWOR("|"), BWXOR("^"), BWLSH("<<"), BWRSH(">>"),
        BWURSH(">>>"), BWNOT("~"), BWANDEQ("&="), BWOREQ("|="), BWXOREQ("^="),
        BWLSHEQ("<<="), BWRSHEQ(">>="), BWURSHEQ(">>>="), AND("&&"), OR("||"),
        ELLIP("..."), TERN("?"), COLON(":"), COMMA(","), DOT("."), TEMP("#{"),
        SEMCO(";"), LBRACE("{"), RBRACE("}"), LBRACK("["), RBRACK("]"), LPAREN("("),
        RPAREN(")"), ANNO("@"), INCRE("++"), DECRE("--"), ESCAPE("\\");
        
        /** The number of this type. */
        private final int index;
        /** The name of this type. */
        private final String name;
        
        /**
         * Constructs each value with a number representing their index and a
         * string representing their lowercase form.
         */
        Type () {
            this.index = 1 << this.ordinal();
            this.name = this.name().toLowerCase();
        }
        
        /**
         * Constructs each value with a number representing their index and a
         * string representing their name.
         * 
         * @param name The name of this type.
         */
        Type (String name) {
            this.index = 1 << this.ordinal();
            this.name = name;
        }
        
        /**
         * Gets the number of this type.
         * 
         * @return The number of this type.
         */
        public int index () { return index; }
        
        /**
         * Gets the name of this type.
         * 
         * @return The name of this type.
         */
        public String getName () { return name; }
    }
    
    /** A bit field like structure to hold all of our types. */
    private static final EnumSet<Type> types = EnumSet.allOf(Type.class);
    
    /** The type of this token. */
    private Type type;
    /** The value of this token in terms of a string (lexeme). */
    private String value;
    /** The name of the file this token was found in. */
    private String fileName;
    /** The line in the file this token was found. */
    private int line;
    /** The column of the line this token was found. */
    private int column;
    
    /**
     * Constructs a new Token with all necessary information.
     * 
     * @param type The type of this token.
     * @param value The value of this token.
     * @param fileName The name of the file this token was found in.
     * @param line The line in the file this token was found.
     * @param column The column in the line this token was found.
     */
    public Token (Type type, String value, String fileName, int line, int column) {
        this.type = type;   this.value = value;     this.fileName = fileName;
        this.line = line;   this.column = column;
    }
    
    /**
     * Constructs an empty Token to be assigned each of it's values.
     */
    public Token () {}
    
    /**
     * @param type The type of this token.
     */
    public void setType (Type type) { this.type = type; }
    
    /**
     * @param value The value of this token.
     */
    public void setValue (String value) { this.value = value; }
    
    /**
     * @param fileName The name of the file this token was found in.
     */
    public void setFileName (String fileName) { this.fileName = fileName; }
    
    /**
     * @param line The line in the file this token was found.
     */
    public void setLine (int line) { this.line = line; }
    
    /**
     * @param column The column in the line this token was found.
     */
    public void setColumn (int column) { this.column = column; }
    
    /**
     * @return The types of tokens available.
     */
    public static EnumSet<Type> types () { return types; }
    
    /**
     * @return The type of this token.
     */
    public Type type () { return type; }
    
    /**
     * @return The value of this token.
     */
    public String value () { return value; }
    
    /**
     * @return The name of the file this token was found in. 
     */
    public String fileName () { return fileName; }
    
    /**
     * @return The line in the file this token was found.
     */
    public int line () { return line; }
    
    /**
     * @return The column in the line this token was found.
     */
    public int column () { return column; }
}
