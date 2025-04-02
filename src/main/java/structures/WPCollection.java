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

    public String getCollctionName() {
        return this.collectionName;
    }

    public String getCollectionPosterPath() {
        return this.collectionPosterPath;
    }

    public String getCollectionBackdropPath() {
        return this.collectionBackdropPath;
    }

    public boolean containsMovie(WPMovie movie) {
        return this.movies.contains(movie);
    }

    public int getCollectionID() {
        return this.collectionID;
    }

    public int[] getMovies() {
        int[] movieIDs = new int[movies.size()];
        for (int i=0; i < movies.size(); i++) {
            movieIDs[i] = movies.get(i).getID();
        }
        return movieIDs;
    }
}
