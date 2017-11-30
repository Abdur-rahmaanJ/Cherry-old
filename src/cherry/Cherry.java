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
package cherry;

import cherry.frontend.parser.Parser;
import cherry.utils.exceptions.FailureToRaiseException;
import cherry.utils.exceptions.FileNotProperException;
import cherry.utils.exceptions.UnknownFlagException;
import cherry.utils.handlers.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the Main class of the Cherry compiler. It is responsible for
 * initiating the compiler, tasking the flag handler to raise flags that were
 * provided on the command line, and tasking the parser to start parsing given
 * files. It ultimately passes each file to the file handler, and each flag to
 * the flag handler altogether, then after the handlers are done, the compiler
 * will return with a success or an error. The process of the handlers include
 * sending each file off to be parsed.
 * 
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public class Cherry {
    
    /**
     * @param args The command line arguments.
     */
    public static void main (String[] args) {
        // A container for the files.
        List<String> filesList = new LinkedList<>();
        // A container for the flags.
        List<String> flagsList = new LinkedList<>();
        
        // Iterate over all arguments and find the flags and files.
        for (String arg : args) {
            if (arg.charAt(0) == '-') { flagsList.add(arg); }
            else { filesList.add(arg); }
        }
        
        // Finalized versions of the Lists in array form for the handlers.
        final String[] files = filesList.toArray(new String[0]);
        final String[] flags = flagsList.toArray(new String[0]);
        
        // construct the handlers on their own threads.
        Thread flagThread, fileThread;
        
        flagThread = new Thread (() -> {
            if (!flagsList.isEmpty()) {
                try {
                    FlagHandler flagHandler = new FlagHandler(flags);
                } catch (UnknownFlagException | FailureToRaiseException ex) {
                    Logger.getLogger(Cherry.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, "FlagHandler-Thread");
        
        flagThread.start();
        
        fileThread = new Thread (() -> {
            if (!filesList.isEmpty()) {
                try {
                    FileHandler fileHandler = new FileHandler(files);
                } catch (FileNotProperException ex) {
                    Logger.getLogger(Cherry.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                throw new NullPointerException("Compiler did not receive files to parse.");
            }
        }, "FileHandler-Thread");
        
        fileThread.start();
        
        // Join the handler threads before continuing to work.
        try {
            if (fileThread != null) { fileThread.join(); }
            if (flagThread != null) { flagThread.join(); }
        } catch (InterruptedException ie) {
            Logger.getLogger(Cherry.class.getName()).log(Level.SEVERE, null, ie);
        }
        
        // Our officially registered files.
        File[] registeredFiles = FileHandler.getRegisteredFiles();
        // A list of the threads that we start so we can stop them before
        // continuing with the rest of compilation.
        List<Thread> threads = new LinkedList<>();

        // Necessary objects for starting up the Parsing processes.
        Thread thread;
        Parser parser;
        int count = 0;

        // We must iterate over all of them and generate threads for each
        // Instance of a Parser to get a file and parse it.
        for (File concreteFile : registeredFiles) {
            parser = new Parser(concreteFile);
            thread = new Thread(parser, "Parser-" + count);
            threads.add(thread);
            thread.start();
        }

        // Once done creating the parsers iterate the threads and join them
        // to this one so that the execution doesn't continue until all of
        // the threads are done.
        threads.forEach((Thread thrd) -> {
            try { thrd.join(); }
            catch (InterruptedException ie) {
                Logger.getLogger(Cherry.class.getName()).log(Level.SEVERE, null, ie);
            }
        });
        
        System.out.println("Success?");
    }
}
