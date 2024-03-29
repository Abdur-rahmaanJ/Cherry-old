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
 * This exception is thrown if for some reason the name of a flag fails to raise
 * its RuntimeFlag equivalent.
 * 
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public class FailureToRaiseException extends Exception {
    /**
     * Constructs a new FailureToRaiseException with a reason and a cause.
     * 
     * @param reason A explanation of what happened.
     * @param cause What caused this exception.
     */
    public FailureToRaiseException (String reason, Throwable cause) { super(reason, cause); }

    /**
     * Constructs a new FailureToRaiseException with only a reason.
     * 
     * @param reason A explanation of what happened.
     */
    public FailureToRaiseException (String reason) { super(reason); }

    /**
     * Constructs a new FailureToRaiseException with only a cause.
     * 
     * @param cause What caused this exception.
     */
    public FailureToRaiseException (Throwable cause) { super(cause); }
}
