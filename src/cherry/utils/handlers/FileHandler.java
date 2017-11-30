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
package cherry.utils.handlers;

import cherry.utils.exceptions.FileNotProperException;
import cherry.utils.exceptions.InvalidFileExtensionException;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code FileHandler} class is responsible for checking files for their
 * extensions, making sure they are viable extensions, sending the files to be
 * parsed, and setting appropriate runtime flags not given by the user. For
 * ease of understanding; these flags could be debug informative, special
 * assembler instruction flags, or the like wise.
 * 
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public final class FileHandler {
    private static File[] registeredFiles;
    
    // One of this class's main jobs is to check each file for the appropriate
    // file extensions. In this case those extensions can be .ch, .ry, or .cherry.
    // So, when this class is constructed, it must do this check.
    
    /**
     * Constructs a new FileHandler with the given files. It checks those files
     * for one of the three available extensions: .ch, .ry, or .cherry; then it
     * will register that file with given information that the parser that will
     * parse it, needs to know.
     * 
     * @param files The array of file names passed in via the command line.
     * @throws FileNotProperException If the file to be registered has an
     *			improper extension.
     */
    public FileHandler (String[] files) throws FileNotProperException {
        // A list to collect all the valid Files in.
        List<File> fileList = new LinkedList<>();
        
        // Iterate over all file names, and test them to make sure they are
        // proper names.
        for (String file : files) {
            try {
                // Check for the appropriate extensions.
                checkExtensionAppropriatenessOf(file);

                // If it is appropriate then we should get here where we can
                // register the file we have the name for.
                fileList.add(new File(file));
            } catch (InvalidFileExtensionException cause) {
                String reason = "Invalid File Extension.";

                throw new FileNotProperException(reason, cause);
            }
        }
        
        registeredFiles = fileList.toArray(new File[0]);
    }
    
    /**
     * Takes a "file" name and it checks the extension. If that extension is not
     * one of the three: .ch, .ry, or .cherry; then the file has an invalid file
     * extension. At which point the exception will be thrown and will most
     * likely be wrapped as a FileNotProperException.
     * 
     * @param name A string representing the name of a file.
     * @throws InvalidFileExtensionException If the file name does not end with
     *			an appropriate file extension as deemed here: .ch, .ry, .cherry.
     */
    public static final void checkExtensionAppropriatenessOf (String name)
    throws InvalidFileExtensionException {
        for (int i = 0; i < name.length(); i++) {
            // Check for the extension separator, then make sure the length
            // before the end of the name is near the correct length.
            // If so, then this must be a file extension at which point we
            // should check it's continuity.
            if (name.charAt(i) != '.') { continue; }
            if ((name.length() - i) > 6) { continue; }

            String extension = name.substring(i);

            // We have a potential match, now we need to formally check it.
            switch (extension) {
                // A proper file extension.
                case ".ch": case ".ry": case ".cherry": { return; }
                // Error
                default: {
                    String reason = "\"" + extension + "\" is not a proper file extension.";

                    throw new InvalidFileExtensionException(reason);
                }
            }
        }
    }
    
    /**
     * Since the {@code registeredFiles} are private, we must give particular
     * processes a chance to see it, mainly, the Main thread; whom will use it
     * to generate parser threads.
     * 
     * @return The registered files.
     */
    public static final File[] getRegisteredFiles () { return registeredFiles; }
}
