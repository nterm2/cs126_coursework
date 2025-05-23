package stores;

import java.time.LocalDate;
import interfaces.IMovies;
import structures.*;

public class Movies implements IMovies{
    Stores stores;
    WPHashMap<Integer, WPMovie> movies;
    WPHashMap<Integer, WPCollection> collections;
    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Movies(Stores stores) {
        this.stores = stores;
        this.movies = new WPHashMap<Integer, WPMovie>();
        this.collections = new WPHashMap<Integer, WPCollection>();
    }

    /**
     * Adds data about a film to the data structure
     * 
     * @param id               The unique ID for the film
     * @param title            The English title of the film
     * @param originalTitle    The original language title of the film
     * @param overview         An overview of the film
     * @param tagline          The tagline for the film (empty string if there is no
     *                         tagline)
     * @param status           Current status of the film
     * @param genres           An array of Genre objects related to the film
     * @param release          The release date for the film
     * @param budget           The budget of the film in US Dollars
     * @param revenue          The revenue of the film in US Dollars
     * @param languages        An array of ISO 639 language codes for the film
     * @param originalLanguage An ISO 639 language code for the original language of
     *                         the film
     * @param runtime          The runtime of the film in minutes
     * @param homepage         The URL to the homepage of the film
     * @param adult            Whether the film is an adult film
     * @param video            Whether the film is a "direct-to-video" film
     * @param poster           The unique part of the URL of the poster (empty if
     *                         the URL is not known)
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        if (movies.get(id) == null) {
            // In the case that the movie hasn't already been stored, create a new Movie instance and then store it in the movies hashmap. As a movie has been successfully added, return true.
            WPMovie newMovie = new WPMovie(id, title, originalTitle, overview, tagline, status, genres, release, budget, revenue, languages, originalLanguage, runtime, homepage, adult, video, poster);
            movies.put(id, newMovie);
            return true;
        }
        // In the case that the movie id is already present in the Movies store, cannot add duplicate / overwrite movies, so return false.
        return false;
    }

    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * O(n)
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        // Remove the given movie. As remove returns a boolean as to whether it was able to successful remove a movie, return the resulting boolean.
        return movies.remove(id);
    }

    /**
     * Gets all the IDs for all films
     * O(n)
     */
    @Override
    public int[] getAllIDs() {
        // As keys in movies represents ids, use getKeys() to retrieve all the keys in the movies hashmap. 
        // Since the hashmap utilises generics, can't have int[] keys, only Integer[]. Thus, we need to 
        // create a new int[] array and copy the integers from movies.getKeys() to the int[] array, which
        // we return
        Integer[] keys = movies.getKeys();
        int[] castedKeys = new int[movies.size()];
        for (int i=0; i < movies.size(); i++) {
            castedKeys[i] = (int) keys[i];
        }
        return castedKeys;
    }

    /**
     * Finds the film IDs of all films released within a given range. If a film is
     * released either on the start or end dates, then that film should not be
     * included.
     * 
     * As keys in movies represents ids, use getKeys() to retrieve all the ids in the movies hashmap.
     * Initialise an int[] array to initially be the size of the number of movies we have (it is possible 
     * that all movies are released in the specified range). Then iterate through each movie. In the case
     * that the movie is released after the start and is before the end, then add the id of the movie 
     * to idsReleasedInRange, subsequently incrementing releasedInRangeCounter to the next index.
     * Then create a shrinked version of trimmedIDsReleasedInRange based on how many ids were 
     * within the given range. Use System.arraycopy to copy the contents of the old array to the new
     * array, and return the trimmedIDsReleasedInRange
     * 
     * O(n)
     * 
     * @param start The start point of the range of dates
     * @param end   The end point of the range of dates
     * @return An array of film IDs that were released between start and end
     */
    @Override
    public int[] getAllIDsReleasedInRange(LocalDate start, LocalDate end) {

        Integer[] keys = movies.getKeys();
        int[] idsReleasedInRange = new int[movies.size()];
        int releasedInRangeCounter = 0;

        for (int i = 0; i < movies.size(); i++) {
            WPMovie givenMovie = movies.get(keys[i]);
            LocalDate release = givenMovie.getRelease();

            if (release.isAfter(start) && release.isBefore(end)) {
                idsReleasedInRange[releasedInRangeCounter++] = givenMovie.getID();
            }
        }

        int[] trimmedIDsReleasedInRange = new int[releasedInRangeCounter];
        System.arraycopy(idsReleasedInRange, 0, trimmedIDsReleasedInRange, 0, releasedInRangeCounter);
        return trimmedIDsReleasedInRange;
    }

    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's title.
     * 
     * O(1)
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getTitle();
    }

    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's original 
     * title.
     * 
     * O(1)
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getOriginalTitle();
    }

    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's overview.
     * 
     * O(1)
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getOverview();
    }

    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's tagline.
     * 
     * O(1)
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getTagline();
    }

    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's status.
     * 
     * O(1)
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getStatus();
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's genres.
     * 
     * O(1)
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getGenres();
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     *
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's release.
     * 
     * O(1)
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public LocalDate getRelease(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getRelease();
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return -1. Otherwise return the movie's budget.
     * 
     * O(1)
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? -1 : movie.getBudget();
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return -1. Otherwise return the movie's revenue.
     * 
     * O(1)
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? -1 : movie.getRevenue();
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's languages.
     * 
     * O(1)
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getLanguages();
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's original 
     * language.
     * 
     * O(1)
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getOriginalLanguage();
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return -1.0d. Otherwise return the movie's runtime.
     * 
     * O(1)
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1.0d
     */
    @Override
    public double getRuntime(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? -1.0d : movie.getRuntime();
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's homepage.
     * 
     * O(1)
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getHomepage();
    }

    /**
     * Gets weather a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return false. Otherwise return boolean indicating 
     * whether movie is an adult movie.
     * 
     * O(1)
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? false : movie.getAdult();
    }

    /**
     * Gets weather a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return false. Otherwise return boolean representing whether
     * film is classed as direct-to-video.
     * 
     * O(1)
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? false : movie.getVideo();
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's poster.
     * 
     * O(1)
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getPoster();
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
     * Get the movie based on the id passed in. In the case that it is doesn't exist, 
     * return false. Otherwise, set the vote average to the vote average passed in by the user,
     * and set the vote count to be the vote count passed in by the user. Return true.
     * 
     * O(1)
     * @param id          The movie ID
     * @param voteAverage The average score on IMDb for the film
     * @param voteCount   The number of reviews on IMDb that were used to generate
     *                    the average score for the film
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean setVote(int id, double voteAverage, int voteCount) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return false; 
        } 
        movie.setVoteAverage(voteAverage);
        movie.setVoteCount(voteCount);
        return true;
    }

    /**
     * Gets the average score for IMDb reviews of a particular film, given the ID
     * number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return -1.0d. Otherwise return the movie's vote average.
     * 
     * O(1)
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1.0d
     */
    @Override
    public double getVoteAverage(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? -1.0d : movie.getVoteAverage();
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return -1. Otherwise return the movie's vote count.
     * 
     * O(1)
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? -1 : movie.getVoteCount();
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
     * In the case that we attempt to get the movie by film id and it doesn't exist, return false.
     * Otherwise,  if the collection doesn't already exist in collections, create a new collection and store, for the given collection id,
     * the new collection object. In the case that it does exist, simply retrieve the new collection object. Then retrieve the movie. Add 
     * the collection to the movie, and update the given movie so that the movie's collection is updated. Then add to the collection the 
     * movie included within the collection, and update the given collection so that the collection's movies has the newly added movie.
     * Return true.
     * 
     * O(1)
     * @param filmID                 The movie ID
     * @param collectionID           The collection ID
     * @param collectionName         The name of the collection
     * @param collectionPosterPath   The URL where the poster can
     *                               be found
     * @param collectionBackdropPath The URL where the backdrop can
     *                               be found
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addToCollection(int filmID, int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        if (movies.get(filmID) == null) {
            return false;
        }
        WPCollection collection = null;
        if (!collections.containsKey(collectionID)) {
            collection = new WPCollection(collectionID, collectionName, collectionPosterPath, collectionBackdropPath);
            collections.put(collectionID, collection);
        }
        
        if (collection == null) {
            collection = collections.get(collectionID);
        }

        WPMovie movie = movies.get(filmID);
        movie.addToCollection(collection);
        movies.put(filmID, movie);
        
        collection.addToCollection(movie);
        collections.put(collectionID, collection);
        
        return true;
    }
    

    /**
     * Get all films that belong to a given collection
     * 
     * Get the collection at the given id from the collections hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the collection's movies.
     * 
     * O(1)
     * @param collectionID The collection ID to be searched for
     * @return An array of film IDs that correspond to the given collection ID. If
     *         there are no films in the collection ID, or if the collection ID is
     *         not valid, return an empty array.
     */
    @Override
    public int[] getFilmsInCollection(int collectionID) {
        WPCollection collection = collections.get(collectionID);
        return collection == null ? new int[0] : collection.getMovies();
    }

    /**
     * Gets the name of a given collection
     * 
     * Get the collection at the given id from the collections hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the collection's name.
     * 
     * O(1)
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        WPCollection collection = collections.get(collectionID);
        return collection == null ? null : collection.getCollectionName();
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * Get the collection at the given id from the collections hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the collection's poster path.
     * 
     * O(1)
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        WPCollection collection = collections.get(collectionID);
        return collection == null ? null : collection.getCollectionPosterPath();
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * Get the collection at the given id from the collections hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the collection's backdrop path.
     * 
     * O(1)
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        WPCollection collection = collections.get(collectionID);
        return collection == null ? null : collection.getCollectionBackdropPath();
    }

    /**
     * Gets the collection ID of a given film
     * 
     * Get the movie at the given id from the movies hashmap. In the case that it doesn't
     * exist (returns null), then return -1. Otherwise return the movies's collection id.
     * 
     * O(1)
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public int getCollectionID(int filmID) {
        WPMovie movie = movies.get(filmID);
        return movie == null ? -1 : movie.getCollectionID();
    }

    /**
     * Sets the IMDb ID for a given film
     * 
     * Attempts to get the movie from the filmID passed in - in the case that it does not exist, return false.
     * Otherwise, set the imbdID for the given movie to the imdbID provided, at which we return true. 
     * 
     * O(1)
     * @param filmID The movie ID
     * @param imdbID The IMDb ID
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setIMDB(int filmID, String imdbID) {
        WPMovie movie = movies.get(filmID);
        if (movie == null) {
            return false; 
        } 
        movie.setIMDB(imdbID);
        return true;
    }

    /**
     * Gets the IMDb ID for a given film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's imdbID.
     * 
     * O(1)
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        WPMovie movie = movies.get(filmID);
        return movie == null ? null : movie.getImbdID();
    }

    /**
     * Sets the popularity of a given film. If the popularity for a film already exists, replace it with the new value
     * 
     * Get the movie based on the id passed in. In the case that it is doesn't exist, 
     * return false. Otherwise, set the popularity to the popularity passed in by the user,
     * and return true.
     * 
     * O(1)
     * @param id         The movie ID
     * @param popularity The popularity of the film
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setPopularity(int id, double popularity) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return false; 
        } 
        movie.setPopularity(popularity);
        return true;
    }

    /**
     * Gets the popularity of a given film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return -1.0d. Otherwise return the movie's popularity.
     * 
     * O(1)
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1.0d. If the popularity has not been set, return 0.0
     */
    @Override
    public double getPopularity(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? -1.0d : movie.getPopularity();
    }

    /**
     * Adds a production company to a given fil
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return whether we were successfullly able
     * to add the passed in company to the movie's production companies. 
     * 
     * O(1)
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        WPMovie movie = movies.get(id);
        return movie == null ? false : movie.addProductionCompany(company);
    }

    /**
     * Adds a production country to a given film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return false. Otherwise return boolean indicating whether we 
     * were able to add the passed in country to the movie's production countries.
     * 
     * O(1)
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        WPMovie movie = movies.get(id);
        return movie == null ? false : movie.addProductionCountry(country);
    }

    /**
     * Gets all the production companies for a given film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's production companies.
     * 
     * O(1)
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getProductionCompanies();
    }

    /**
     * Gets all the production companies for a given film
     * 
     * Get the movie at the given id from movies hashmap. In the case that it doesn't
     * exist (returns null), then return null. Otherwise return the movie's production countries.
     * 
     * O(1)
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        WPMovie movie = movies.get(id);
        return movie == null ? null : movie.getProductionCountries();
    }

    /**
     * States the number of movies stored in the data structure
     * O(1)
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
        return movies.size();
    }

    /**
     * Produces a list of movie IDs that have the search term in their title,
     * original title or their overview
     * 
     * Attempt to find a film based on the title, original title or overview. Create an initial int array called 
     * matchingMovies of the size of the number of movies. Iterate through each movie. In the case that the movies title, 
     * originalTitle or overview contains the search term, add the movie's id to matchingMovies, and increment counter to point
     * to the next index to place the next matching movie. Then trim matchingMovies  to remove uneeded null values using System.arraycopy(),
     * and return the trimmed array. 
     * 
     * O(n)
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {
        int[] matchingMovies = new int[movies.size()];
        int counter = 0;
        Integer[] keys = movies.getKeys();
        for (int i=0; i < movies.size(); i++) {
            WPMovie movie = movies.get(keys[i]);
            if (movie.getTitle().contains(searchTerm) || movie.getOriginalTitle().contains(searchTerm) || movie.getOverview().contains(searchTerm)) {
                matchingMovies[counter++] = movie.getID();
            }
        }

        int[] trimmedMatchingMovies = new int[counter];
        System.arraycopy(matchingMovies, 0, trimmedMatchingMovies, 0, counter);
        return trimmedMatchingMovies;
    }
}