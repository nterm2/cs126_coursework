package structures;
import java.time.LocalDateTime;

public class WPRating {
    private float rating;
    private LocalDateTime timestamp;

    public WPRating(float rating, LocalDateTime timestamp) {
        this.rating = rating;
        this.timestamp = timestamp;
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
