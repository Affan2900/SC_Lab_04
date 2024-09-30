/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import org.junit.Before;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

  private Tweet tweet1;
    private Tweet tweet2;
    private Tweet tweet3;

    @Before
    public void setUp() {
        tweet1 = new Tweet(1L, "user1", "Hello @user2 and @User3!",Instant.parse("2023-01-01T10:00:00Z"));
        tweet2 = new Tweet(2L, "user2", "Goodbye World!",Instant.parse("2023-01-02T11:00:00Z"));
        tweet3 = new Tweet(3L, "user3", "Another Tweet.",Instant.parse("2023-01-01T12:00:00Z"));
    }

    @Test
    public void testGetTimespan() {
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3);
        Timespan timespan = Extract.getTimespan(tweets);

        assertEquals(Instant.parse("2023-01-01T10:00:00Z"), timespan.getStart());
        assertEquals(Instant.parse("2023-01-02T11:00:00Z"), timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsers() {
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3);
        Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);

        Set<String> expectedUsers = new HashSet<>(Arrays.asList("user2", "user3"));
        assertEquals(expectedUsers, mentionedUsers);
    }

    @Test
    public void testGetMentionedUsersNoMentions() {
        Tweet tweet = new Tweet(1, "user1", "This tweet has no mentions.", Instant.now());
        List<Tweet> tweets = Arrays.asList(tweet);
        Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);

        assertTrue(mentionedUsers.isEmpty());
    }

    @Test
    public void testGetTimespanEmptyList() {
        List<Tweet> tweets = Arrays.asList();
        Timespan timespan = Extract.getTimespan(tweets);

        // Assuming that an empty list returns the current time as both start and end
        assertEquals(timespan.getStart(), timespan.getEnd());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
