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
 * This exception is thrown when a flag is denounced as non-existent. This could
 * happen due to it being spelled incorrectly or being wrong in general.
 * @author SoraKatadzuma
 */
public class FlagDoesNotExistException extends Exception {
    /**
     * Constructs a new FlagDoesNotExistException with a reason and a cause.
     * @param reason A explanation of what happened.
     * @param cause What caused this exception.
     */
    public FlagDoesNotExistException (String reason, Throwable cause) { super(reason, cause); }

    /**
     * Constructs a new FlagDoesNotExistException with only a reason.
     * @param reason A explanation of what happened.
     */
    public FlagDoesNotExistException (String reason) { super(reason); }

    /**
     * Constructs a new FlagDoesNotExistException with only a cause.
     * @param cause What caused this exception.
     */
    public FlagDoesNotExistException (Throwable cause) { super(cause); }
}
