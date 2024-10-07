package twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Filter {

	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
	    List<Tweet> result = new ArrayList<>();
	    for (Tweet tweet : tweets) {
	        if (isAuthorMatch(tweet.getAuthor(), username)) {
	            result.add(tweet);
	        }
	    }
	    return result;
	}

	private static boolean isAuthorMatch(String author, String username) {
	    return author != null && author.equalsIgnoreCase(username);
	}
	
	public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
	    List<Tweet> result = new ArrayList<>();
	    for (Tweet tweet : tweets) {
	        if (tweet.getTimestamp().isAfter(timespan.getStart()) && 
	            tweet.getTimestamp().isBefore(timespan.getEnd())) {
	            result.add(tweet);
	        }
	    }
	    return result;
	}

    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            String tweetText = tweet.getText().toLowerCase();
            for (String word : words) {
                if (tweetText.contains(word.toLowerCase())) {
                    result.add(tweet);
                    break; // Break to avoid adding the same tweet multiple times
                }
            }
        }
        return result;
    }
}
