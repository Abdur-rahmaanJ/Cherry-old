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
package cherry.frontend.parser;

import java.io.File;

/**
 * The {@code Parser} class is that of a type similar to a liaison class. It's
 * primary goal is to take a file in, send it off to be lexed, then to be parsed.
 * It, itself is just a worker. Transferring data between two other classes.
 * Don't be fooled though, it does do more. It is responsible for doing semantic
 * analysis after syntax analysis. We leave these processes as separate for
 * modularity, but also it makes sense to have a generator check a file's syntax,
 * then to not only confirm that syntax, but also confirm other things about the
 * semantics. Since it is possible for the parser generator to return multiple
 * parse trees due to it's nature; this class will also be responsible for
 * figuring out which one is proper. But, it will be up to the generator to give
 * back ones that actually make sense. If it comes down to a grammar will always
 * and forever produce multiple parse trees, even with the necessary actions,
 * then that grammar is considered to be too ambiguous. But this should never
 * happen with careful planning and implementation. It is a goal to keep the
 * grammar of Cherry as unambiguous as possible, but due to some standard design
 * we can't fully escape it. However, the less there is, the more efficient this
 * compiler is, and the easier it is to debug or throw out parse trees that don't
 * truly produce an expected outcome.
 * 
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public final class Parser implements Runnable {
    /** A reference to the file this Parser must parse. */
    private final File file;
    /** A reference to one instance of the parser generator. */
    // StemParser parser = StemParser.clone();

    /**
     * Constructs a new Parser whom will parse the file passed in from the caller.
     * @param file The file to parse.
     */
    public Parser (File file) { this.file = file; }

    /**
     * Inherited from Runnable. This version is responsible for running the
     * lexer and parser generator processes. Once it has done that it will do
     * semantic checks to assure that the information is proper.
     */
    @Override
    public void run () {
        // Generate a lexer to lexically check the file that it has received.
        // final Lexer lexer = new Lexer(file);
        // lexer.lex();
        // Use the parser reference in the instance fields to parse the tokens
        // found by the Lexer.
        // parser.parse(lexer.getTokens());
        // Take the result of parsing (a parse tree or multiple parse trees) and
        // do proper semantic checks on them. The parser output will be a List
        // of parse trees or just a single one depending on if it managed to
        // parse into two or more parse trees. However, we want to avoid this.
        // finalizeParserOutput(parser.getOutput());
    }
}
