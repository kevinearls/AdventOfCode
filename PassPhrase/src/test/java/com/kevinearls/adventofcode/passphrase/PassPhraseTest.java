package com.kevinearls.adventofcode.passphrase;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

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

 --- Part Two ---

 For added security, yet another system policy has been put in place. Now, a valid passphrase must contain no two words that are anagrams of each other - that is, a passphrase is invalid if any word's letters can be rearranged to form any other word in the passphrase.

 For example:

 abcde fghij is a valid passphrase.
 abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.
 a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.
 iiii oiii ooii oooi oooo is valid.
 oiii ioii iioi iiio is not valid - any of these words can be rearranged to form any other word.
 Under this new system policy, how many passphrases are valid?

 Although it hasn't changed, you can still get your puzzle input.

 */
public class PassPhraseTest {
    private PassPhrase passPhrase = new PassPhrase();

    @Test
    public void testExamplePassPhrases() {
        /*
        abcde fghij is a valid passphrase.
 abcde xyz ecdab is not valid - the letters from the third word can be rearranged to form the first word.
 a ab abc abd abf abj is a valid passphrase, because all letters need to be used when forming another word.
 iiii oiii ooii oooi oooo is valid.
 oiii ioii iioi iiio is not valid -
         */
        assertTrue("abcde fghij should be valid", passPhrase.approve("abcde fghij"));
        assertFalse("abcde xyz ecdab should NOT be valid", passPhrase.approve("abcde xyz ecdab"));
        assertTrue("a ab abc abd abf abj should be valid", passPhrase.approve("a ab abc abd abf abj"));
        assertTrue("iiii oiii ooii oooi oooo should be valid", passPhrase.approve("iiii oiii ooii oooi oooo"));
        assertFalse("oiii ioii iioi iiio should NOT be valid", passPhrase.approve("oiii ioii iioi iiio"));
    }


    @Test
    public void testWithRealInput() throws Exception {
        File target = Paths.get(getClass().getClassLoader().getResource("TestPassPhrases.txt").toURI()).toFile();
        BufferedReader br = new BufferedReader(new FileReader(target));

        int goodPhraseCount = 0;
        int totalPhraseCount = 0;

        String line = br.readLine();
        while (line != null) {
            totalPhraseCount++;
            if (passPhrase.approve(line)) {
                goodPhraseCount++;
            }
            line = br.readLine();
        }

        System.out.println("Got " + goodPhraseCount + " good pass phrases out of " + totalPhraseCount);
        assertEquals(208, goodPhraseCount);
    }

    @Ignore
    @Test
    public void eraseMe() {
        Set<String> anagrams = passPhrase.generateAnagrams("abcde");
        System.out.println("GOT " + anagrams.size());
        List<String> blah = new ArrayList<>(anagrams);
        Collections.sort(blah);
        for (String anagram : blah) {
            System.out.println(anagram);
        }
    }
}
