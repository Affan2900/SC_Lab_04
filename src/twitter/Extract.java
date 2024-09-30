package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Extract {

    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return new Timespan(Instant.now(), Instant.now()); // or handle this case differently
        }

        Instant start = Instant.MAX;
        Instant end = Instant.MIN;

        for (Tweet tweet : tweets) {
            Instant timestamp = tweet.getTimestamp();
            if (timestamp.isBefore(start)) {
                start = timestamp;
            }
            if (timestamp.isAfter(end)) {
                end = timestamp;
            }
        }
        return new Timespan(start, end);
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            // Use regex to find mentions
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("@([A-Za-z0-9_]{1,15})").matcher(text);
            while (matcher.find()) {
                mentionedUsers.add(matcher.group(1).toLowerCase()); // Store usernames in lowercase
            }
        }
        return mentionedUsers;
    }
    private static boolean isValidUsername(String username) {
        // Simple validation for username (you can improve this based on actual rules)
        return username.matches("[A-Za-z0-9_]{1,15}"); // Twitter usernames: 1-15 chars, alphanumeric + underscore
    }
}
