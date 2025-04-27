package structures;

/**
 * Represents a collection (or series) of movies that belong together.
 */
public class WPCollection {
    // List of movies that are part of this collection
    private WPArrayList<WPMovie> movies;
    
    // ID of the collection
    private int collectionID;
    
    // Name of the collection
    private String collectionName;
    
    // Path to the poster image for the collection
    private String collectionPosterPath;
    
    // Path to the backdrop image for the collection
    private String collectionBackdropPath;

    /**
     * Constructs a new WPCollection object with the given details.
     * 
     * @param collectionID           Unique ID of the collection
     * @param collectionName         Name of the collection
     * @param collectionPosterPath   URL/path to the collection's poster
     * @param collectionBackdropPath URL/path to the collection's backdrop
     */
    public WPCollection(int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        this.movies = new WPArrayList<WPMovie>();
        this.collectionID = collectionID;
        this.collectionName = collectionName;
        this.collectionPosterPath = collectionPosterPath;
        this.collectionBackdropPath = collectionBackdropPath;
    }

    /**
     * Adds a movie to this collection.
     * 
     * @param movie The movie to add.
     */
    public void addToCollection(WPMovie movie) {
        this.movies.add(movie);
    }

    /**
     * Returns the name of the collection.
     * 
     * @return Collection name.
     */
    public String getCollectionName() {
        return this.collectionName;
    }

    /**
     * Returns the path to the collection's poster image.
     * 
     * @return Poster path.
     */
    public String getCollectionPosterPath() {
        return this.collectionPosterPath;
    }

    /**
     * Returns the path to the collection's backdrop image.
     * 
     * @return Backdrop path.
     */
    public String getCollectionBackdropPath() {
        return this.collectionBackdropPath;
    }

    /**
     * Checks if a given movie is part of this collection.
     * 
     * @param movie The movie to check.
     * @return True if the movie is in the collection, false otherwise.
     */
    public boolean containsMovie(WPMovie movie) {
        return this.movies.contains(movie);
    }

    /**
     * Returns the ID of this collection.
     * 
     * @return Collection ID.
     */
    public int getCollectionID() {
        return this.collectionID;
    }

    /**
     * Returns an array of movie IDs that are part of this collection.
     * 
     * @return Array of movie IDs.
     */
    public int[] getMovies() {
        int[] movieIDs = new int[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            movieIDs[i] = movies.get(i).getID();
        }
        return movieIDs;
    }
}
