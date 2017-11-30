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
package cherry.utils.exceptions;

/**
 * This exception can be thrown by a few reasons. Two namely are:
 * InvalidFileExtensionException and FileNotFoundException. This is because,
 * assumably, if the file has the wrong extension it is considered improper by
 * the compiler, but if it is not found then it poses a new problem, that we can
 * only describe to the user.
 * 
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public class FileNotProperException extends Exception {
    /**
     * Constructs a new FileNotProperException with a reason and a cause.
     * 
     * @param reason A explanation of what happened.
     * @param cause What caused this exception.
     */
    public FileNotProperException (String reason, Throwable cause) { super(reason, cause); }

    /**
     * Constructs a new FileNotProperException with only a reason.
     * 
     * @param reason A explanation of what happened.
     */
    public FileNotProperException (String reason) { super(reason); }

    /**
     * Constructs a new FileNotProperException with only a cause.
     * 
     * @param cause What caused this exception.
     */
    public FileNotProperException (Throwable cause) { super(cause); }
}
