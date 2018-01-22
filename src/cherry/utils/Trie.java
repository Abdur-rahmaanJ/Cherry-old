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
package cherry.utils;

/**
 * This is a lightweight way for the {@code Lexer} to create a dictionary out of
 * the keywords in the {@code Token.Type} enum.
 * 
 * <p>
 * Keywords are broken down letter by letter and placed into nodes which make up
 * a tree data structure. When a word is sought out, it is checked letter by
 * letter along the the tree until it determines whether or not the {@code Trie}
 * contains that word. Its complexity is O(n) but has the potential to have its
 * performance increased.
 * </p>
 * 
 * @author SoraKatadzuma
 * @version Alpha 0.0.1
 * @since 11/20/2017
 */
public class Trie {
    /**
     * The Trie contains nodes that transport a traverser to each other node.
     * A single node represents one of 52 characters (both of upper and lower
     * characters). It also contains an array pointing to the next available
     * character nodes.
     */
    protected class Node {
        // the array containing the references to the next nodes.
        private final Node[] children = new Node[52];

        protected Node() { }

        /**
         * Returns the child node of the given character.
         * If the character is uppercase the array position is the
         * ascii value of the character minus 65 (the number of uppercase A).
         * For lowercase letters, we subtract 97 and add 27 to force the
         * characters to the other half of the array. The combined number of
         * (97 - 27) is intuitively 70, but we must add one to account for
         * offset as Z (90) - 65 = 25, but a (97) - 70 = 27 != 26 like we need
         * it to.
         *
         * @param child The child node being called for.
         * @return The child node of the given character.
         */
        protected Node getChild(char child) {
            return Character.isUpperCase(child)
                    ? children[child - 65]
                    : children[child - 71];
        }

        /**
         * Adds a child node to the array of child nodes for this node.
         * Uses same method of hashing the nodes into the array as it gets
         * them from getChild().
         *
         * @param child The child node to add.
         * @return The node added if successful.
         * @see this.getChild
         */
        protected boolean addChild(char child) {
            int indice = Character.isUpperCase(child)
                    ? child - 65
                    : child - 71;

            if (children[indice] != null) {
                return false;
            }

            children[indice] = new Node();
            return true;
        }
    }

    // The root node of this Trie.
    private final Node root = new Node();

    public Trie() { }

    /**
     * Inserts a word into the trie by adding each character to the prefixes.
     *
     * @param word The word to insert.
     * @return true if and only if the word was fully added.
     */
    public boolean insert(String word) {
        boolean added = false;
        Node node = root;
        char ch;

        for (int i = 0; i < word.length(); i++) {
            ch = word.charAt(i);

            if (node.getChild(ch) == null) {
                node.addChild(ch);
                node = node.getChild(ch);
                added = true;
				continue;
            }

			node = node.getChild(ch);
        }

        return added;
    }

    /**
     * Checks if this trie contains the given word or prefix.
     *
     * @param word The word or prefix to search for.
     * @return true if this true contains the word.
     */
    public boolean contains(String word) {
        Node node = root;
        char ch;

        for (int i = 0; i < word.length(); i++) {
            ch = word.charAt(i);

            if ((node = node.getChild(ch)) == null) { return false; }
        }

        return true;
    }

    public Node root() { return root; }
}
