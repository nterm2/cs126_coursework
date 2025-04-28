package structures;

import java.time.LocalDateTime;

/**
 * Represents a user's rating of a movie, including the score, time of rating, movie ID, and user ID.
 * 
 * This class is used to store and manage the rating information for a movie, including the rating score, 
 * the timestamp of when the rating was made, the associated movie ID, and the user ID of the person who 
 * provided the rating. It provides methods for retrieving and updating this information.
 */
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

    /**
     * Returns the rating value.
     * 
     * This method retrieves the current rating value, which is a float. The rating 
     * can be used to evaluate the quality or value of an entity, such as a movie or product.
     * 
     * @return The current rating value.
     */
    public float getRating() {
        return this.rating;
    }

    /**
     * Returns the timestamp of when the rating was made.
     * 
     * This method retrieves the {@code timestamp} field, which stores the exact date 
     * and time when the rating was given. It helps track when the rating was made.
     * 
     * @return The timestamp of when the rating was made.
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Updates the rating value.
     * 
     * This method sets the rating value to the provided new value. It allows you to 
     * update the rating for an entity.
     * 
     * @param newRating The new rating value to set.
     */
    public void setRating(float newRating) {
        this.rating = newRating;
    }

    /**
     * Updates the timestamp to a new value.
     * 
     * This method sets the timestamp to the provided new timestamp. It allows you to 
     * update the time when the rating was given.
     * 
     * @param newTimestamp The new timestamp to set.
     */
    public void setTimestamp(LocalDateTime newTimestamp) {
        this.timestamp = newTimestamp;
    }
}
