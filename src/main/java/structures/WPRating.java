package structures;

import java.time.LocalDateTime;

// WPRating represents a user's rating of a movie, including the score, time of rating, movie ID, and user ID
public class WPRating {
    // Rating value (e.g., between 0 and 5 stars)
    private float rating;

    // Timestamp when the rating was made
    private LocalDateTime timestamp;

    // ID of the movie that was rated
    private int movieID;

    // ID of the user who gave the rating
    private int userID;

    // Constructor: initializes the rating with its value, timestamp, movie ID, and user ID
    public WPRating(float rating, LocalDateTime timestamp, int movieID, int userID) {
        this.rating = rating;
        this.timestamp = timestamp;
        this.movieID = movieID;
        this.userID = userID;
    }

    // Returns the rating value
    public float getRating() {
        return this.rating;
    }

    // Returns the timestamp of when the rating was made
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    // Updates the rating value
    public void setRating(float newRating) {
        this.rating = newRating;
    }

    // Updates the timestamp to a new value
    public void setTimestamp(LocalDateTime newTimestamp) {
        this.timestamp = newTimestamp;
    }
}
