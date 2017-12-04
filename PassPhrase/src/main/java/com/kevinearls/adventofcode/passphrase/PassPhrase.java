package com.kevinearls.adventofcode.passphrase;

import java.util.HashSet;
import java.util.Set;

/**
 * --- Day 4: High-Entropy Passphrases ---

 A new system policy has been put in place that requires all accounts to use a passphrase instead of simply a password.
 A passphrase consists of a series of words (lowercase letters) separated by spaces.

 To ensure security, a valid passphrase must contain no duplicate words.

 For example:

 aa bb cc dd ee is valid.
 aa bb cc dd aa is not valid - the word aa appears more than once.
 aa bb cc dd aaa is valid - aa and aaa count as different words.
 The system's full passphrase list is available as your puzzle input. How many passphrases are valid?


 */
public class PassPhrase {
    public boolean approve(String passphrase) {
        Set<String> words = new HashSet<>();
        String[] parts = passphrase.split("\\s+");
        for (String part : parts) {
            Set<String> anagrams = generateAnagrams(part);
            for (String anagram : anagrams) {
                if (words.contains(anagram)) {
                    return false;
                } else {
                    words.add(anagram);
                }
            }
        }
        return true;
    }


    /**
     * Return a set of all possible anagrams of the given string
     * @param target
     * @return
     */
    protected Set<String> generateAnagrams(String target) {
        Set<String> anagrams = new HashSet<>();

        target = target.trim();
        if (target.length() == 1) {
            anagrams.add(target);
            return anagrams;
        } else {
            for (int i=0; i < target.length(); i++) {
                char current = target.charAt(i);
                String remainder = new String();
                for (int j=0; j< target.length(); j++) {
                    if (j != i) {
                        remainder += target.charAt(j);
                    }
                }
                Set<String> subAnagrams = generateAnagrams(remainder);
                for (String sub : subAnagrams) {
                    anagrams.add(current + sub);
                }
            }
        }

        return anagrams;
    }
}
