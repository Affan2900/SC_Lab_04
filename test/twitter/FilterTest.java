package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T09:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T13:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "talking about cryptography", d3);
    private static final Tweet tweet4 = new Tweet(4, "user1", "no relevant content", d4);
    private static final Tweet tweet5 = new Tweet(5, "alyssa", "another tweet not about rivest", d5);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // ensure assertions are enabled
    }

    @Test
    public void testWrittenByMultipleAuthors() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");
        assertEquals("expected list of 2 tweets by alyssa", 2, writtenBy.size());
        assertTrue("expected list to contain tweet1", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet3", writtenBy.contains(tweet3));
    }

    @Test
    public void testWrittenByNonExistentAuthor() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "nonexistent");
        assertTrue("expected empty list for nonexistent author", writtenBy.isEmpty());
    }


    @Test
    public void testInTimespanNoTweetsWithinRange() {
        Instant testStart = Instant.parse("2016-02-17T14:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T15:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        assertTrue("expected empty list when no tweets are in range", inTimespan.isEmpty());
    }

    @Test
    public void testContainingMultipleKeywords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("talk", "rivest"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
    }

    @Test
    public void testContainingNoMatches() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("unrelated", "content"));
        assertTrue("expected empty list for no matching keywords", containing.isEmpty());
    }

    @Test
    public void testContainingCaseInsensitive() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("Rivest"));
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweet2", containing.contains(tweet2));
    }
}
