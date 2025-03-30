package structures;

public class WPCollection {
    WPArrayList<WPMovie> movies;
    int collectionID;
    String collectionName; 
    String collectionPosterPath;
    String collectionBackdropPath;

    public WPCollection(int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        this.movies = new WPArrayList<WPMovie>();
        this.collectionID = collectionID;
        this.collectionName = collectionName;
        this.collectionPosterPath = collectionPosterPath;
        this.collectionBackdropPath = collectionBackdropPath;
    }

    public void addToCollection(WPMovie movie) {
        this.movies.add(movie);
    }

    public boolean containsMovie(WPMovie movie) {
        return this.movies.contains(movie);
    }
}
