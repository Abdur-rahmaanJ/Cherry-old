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

import cherry.utils.exceptions.FailureToRaiseException;
import cherry.utils.exceptions.FlagDoesNotExistException;
import cherry.utils.exceptions.RaiseIncapabilityException;
import cherry.utils.exceptions.UnknownFlagException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The {@code FlagHandler} class is one which was specifically designed to check
 * command line flags: to make sure they exist, to make sure they are raise
 * capable flags, and to subsequently raise them.
 * <p>
 * Command line flags should have the form: "--flag" or "-f(lag)".
 * The prior is a full flag, these flags are checked for stipulations, such as
 * conditions or sub-flags to raise. The latter is a short flag, used to raise
 * full flags as default and or convenience.
 * </p>
 * 
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public final class FlagHandler {
    /**
     * Every flag placed on the command line, if it exists, will be turned into
     * a runtime flag. A runtime flag is essentially the compiler's version of
     * the command line flag. Any raised runtime flag can be seen by any part of
     * compiler. We do this so that the individual processes that have a role in
     * producing some kind of diagnostics, or that need to pass data to the
     * FlagHandler, can do so when that process is ready to, rather than having
     * the FlagHandler request the information and risk having a hold up in
     * execution so that the FlagHandler can get it's data.
     */
    public enum RuntimeFlag {
        FLAG("--flag");

        /** This is the command line string that represents this flag. */
        private final String name;
        /** This is a map of the name of the flag to the actual flag. */
        private static final Map<String, RuntimeFlag> mappedFlags = generateMappedFlags();

        /**
         * Constructs the values of this enum.
         * @param name The command line string that represents this flag.
         */
        RuntimeFlag (String name) { this.name = name; }

        /**
         * Generates the map containing the name of a flag to the flag itself.
         * @return {@code Map<String, RuntimeFlag>}, flags mapped by their names.
         */
        private static Map<String, RuntimeFlag> generateMappedFlags () {
            Map<String, RuntimeFlag> result = new HashMap<>(values().length);

            // Iterate over every flag and put them into the map.
            for (RuntimeFlag flag : values()) { result.put(flag.name, flag); }

            return result;
        }

        /**
         * Checks if a particular flag exists, or in other words, RuntimeFlag
         * contains a value with the name of the command line argument in
         * question.
         * 
         * @param name The name of the command line argument being checked.
         * @throws FlagDoesNotExistException If the flag is not contained inside
         *			of {@code mappedFlags}.
         */
        private static void exists (String name)
        throws FlagDoesNotExistException {
            // We have this prepared in case of failure.
            String reason = "Flag: \"" + name + "\" does not exist.";

            // Check if the map even contains the key.
            if (!mappedFlags.containsKey(name)) {
                // An extended resoning.
                String augmentedReason = reason +
                    " No key matching this flag\'s name was found.";

                throw new FlagDoesNotExistException(augmentedReason);
            }

            // Check if the map contains a value at that key.
            if (mappedFlags.get(name) == null) {
                // An extended reasoning.
                String augmentedReason = reason +
                    " No value for this flag name was found.";

                throw new FlagDoesNotExistException(augmentedReason);
            }
        }
    }
    
    /**
     * A container for all the runtime flags that are raised from command line
     * flags. This container is the Java equivalent to an array of bit flags in
     * enum format. It actually is quite similar to the Java equivalent an array
     * of bit flags called the BitSet. Since we decided to go with the Enum
     * {@code RuntimeFlag} for convenience and flexibility, we will use the
     * EnumSet to store our raised flags.
     */
    private static final EnumSet<RuntimeFlag> RAISEDFLAGS = EnumSet.noneOf(RuntimeFlag.class);
    
    /**
     * Constructs the FlagHandler with the command line flags that should be
     * raised if they so exist.
     * 
     * @param flags The command line flag strings that assumably represent
     *      a runtime flag.
     * @throws UnknownFlagException If the flag cannot be found in the mappedFlags.
     * @throws FailureToRaiseException If a flag was did not make it into the
     *      {@code raisedFlags} EnumSet.
     *
     * @see RuntimeFlag#exists(String)
     * @see this#raiseFlags(String[])
     */
    public FlagHandler (String[] flags)
    throws UnknownFlagException, FailureToRaiseException {
        // A list of name marked as unknown flags, helps us raise flags that do
        // exist according to the RuntimeFlag enum.
        List<String> acceptedNames = new LinkedList<>();

        // Iterate over all flag names and check if they exist as a RuntimeFlag.
        for (String name : flags) {
            // If the name does not exist as a RuntimeFlag, throw a new
            // {@code UnknownFlagException}.
            try {
                RuntimeFlag.exists(name);

                // If this succeded then this next part will happen.
                acceptedNames.add(name);
            } catch (FlagDoesNotExistException cause) {
                // A reason why this exception was thrown.
                String reason = "A flag was marked as unknown.";

                throw new UnknownFlagException(reason, cause);
            }
        }

        // Now that we know all the existing flags that we can raise, we will do
        // so.
        try {
            raiseFlags(acceptedNames.toArray(new String[0]));
        } catch (RaiseIncapabilityException cause) {
            String reason = "Failed to while attempting to raise flags.";

            throw new FailureToRaiseException(reason, cause);
        }
    }
    
    /**
     * An encapsulation method, useful for raising an array of flags. All
     * flags should be checked for their existence before they are raised.
     * This method will not be responsible for that however.
     * 
     * @param flagsToRaise Is the array of flags to raise, denoted by their
     *			name.
     * @throws RaiseIncapabilityException if a flag was not added to
     *			{@code raisedFlags}.
     */
    private static void raiseFlags (String[] flagsToRaise)
    throws RaiseIncapabilityException {
        // Iterate over all strings, getting their RuntimeFlag value and
        // attempt to raise them. Throw an new
        // {@code RaiseIncapabilityException} if the the flag wasn't raised.
        for (String flag : flagsToRaise) {
            try {
                raiseFlag(RuntimeFlag.mappedFlags.get(flag));
            } catch (Throwable cause) {
                String reason = "Incapable of raising flag: \"" + flag + "\".";
                throw new RaiseIncapabilityException(reason, cause);
            }
        }
    }
    
    /**
     * <i>Raises</i> a flag by adding it to an EnumSet of {@code raisedFlags}.
     * The flag can only be raised if the flag exists. Since every flag
     * should be checked before they are thrown, we should do that before
     * passing them to this method.
     * 
     * @param flagToRaise Is the flag to raise.
     * @return {@code true} if the flag was placed into the EnumSet
     *			{@code raisedFlags}.
     */
    private static boolean raiseFlag (RuntimeFlag flagToRaise) {
        // Make sure the flag was added, returning false if it wasn't.
        // Return true if it was.
        return RAISEDFLAGS.add(flagToRaise);
    }
    
    /**
     * This provides a way for individual processes to see if a flag that they
     * are supposed to respond to, has been raised.
     * @return The {@code raisedFlags} EnumSet.
     */
    public static EnumSet<RuntimeFlag> getRaisedFlags () { return RAISEDFLAGS; };
}
