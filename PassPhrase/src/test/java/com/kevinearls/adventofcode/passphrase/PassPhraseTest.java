package com.kevinearls.adventofcode.passphrase;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


 */
public class PassPhraseTest {
    private PassPhrase passPhrase = new PassPhrase();

    @Test
    public void testExamplePassPhrases() {
        assertTrue("aa bb cc dd ee should be valid", passPhrase.approve("aa bb cc dd ee"));
        assertFalse("aa bb cc dd aa should NOT be valid", passPhrase.approve("aa bb cc dd aa"));
        assertTrue("aa bb cc dd aaa should be valid", passPhrase.approve("aa bb cc dd aaa"));
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
            //System.out.println("Testing: [" + line + "]");
            if (passPhrase.approve(line)) {
                goodPhraseCount++;
            }
            line = br.readLine();
        }

        System.out.println("Got " + goodPhraseCount + " good pass phrases out of " + totalPhraseCount);
        assertEquals(386, goodPhraseCount);
    }
}
