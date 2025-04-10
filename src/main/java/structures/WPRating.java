package structures;
import java.time.LocalDateTime;

public class WPRating {
    private float rating;
    private LocalDateTime timestamp;
    private int movieID;
    private int userID;

    public WPRating(float rating, LocalDateTime timestamp, int movieID, int userID) {
        this.rating = rating;
        this.timestamp = timestamp;
        this.movieID = movieID;
        this.userID = userID;
    }

    public float getRating() {
        return this.rating;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setRating(float newRating) {
        this.rating = newRating;
    }

    public void setTimestamp(LocalDateTime newTimestamp) {
        this.timestamp = newTimestamp;
    }
}
