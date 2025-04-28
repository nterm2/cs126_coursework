package stores;

import java.time.LocalDateTime;

import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {
    Stores stores;
    WPHashMap<Integer, WPHashMap<Integer, WPRating>> movieRatings;
    WPHashMap<Integer, WPHashMap<Integer, WPRating>> userRatings;
    WPHashMap<Integer, Float> movieRatingsSums;
    int numRatings;

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Ratings(Stores stores) {
        this.stores = stores;
        this.movieRatings = new WPHashMap<Integer, WPHashMap<Integer, WPRating>>();
        this.userRatings = new WPHashMap<Integer, WPHashMap<Integer, WPRating>>();
        this.movieRatingsSums = new WPHashMap<Integer, Float>();
    }

    /**
     * Adds a rating to the data structure. The rating must be unique based on the user ID and movie ID combination.
     * 
     * If a rating by the same user for the same movie already exists, return false. 
     * Otherwise, create a new WPRating object using the given rating, timestamp, movie ID, and user ID.
     * If this is the first rating for the movie (i.e., the movie ID does not yet exist in movieRatings), 
     * we create a new inner WPHashMap for that movie ID to store user-specific ratings.
     * 
     * We then insert the new rating into the movieRatings data structure, under movieID → userID → WPRating.
     * 
     * Next, we update the movieRatingsSums structure by adding the new rating to the existing total sum 
     * of ratings for that movie. If no prior sum exists, we initialize it to the given rating.
     * Similarly, if this is the first rating from the user (i.e., the user ID does not yet exist in userRatings), 
     * we create a new WPHashMap for that user ID to store movie-specific ratings.
     * 
     * We then insert the new rating into the userRatings structure under userID → movieID → WPRating.
     * Finally, we increment numRatings to reflect that a new unique rating has been added.
     * 
     * This operation is O(1) on average due to hash map access times.
     * 
     * @param userid    The ID of the user giving the rating
     * @param movieid   The ID of the movie being rated
     * @param rating    The rating value (0 to 5 inclusive)
     * @param timestamp The time when the rating was made
     * @return TRUE if the rating was successfully added, FALSE otherwise
     */
    @Override
    public boolean add(int userid, int movieid, float rating, LocalDateTime timestamp) {
        if (movieRatings.containsKey(movieid) && movieRatings.get(movieid).containsKey(userid)) {
            return false;
        }

        WPRating newRating = new WPRating(rating, timestamp, movieid, userid);

        if (!movieRatings.containsKey(movieid)) {
            movieRatings.put(movieid, new WPHashMap<Integer, WPRating>());
        }
        movieRatings.get(movieid).put(userid, newRating);

        float previousSum = movieRatingsSums.containsKey(movieid) ? movieRatingsSums.get(movieid) : 0;
        movieRatingsSums.put(movieid, previousSum + rating);

        if (!userRatings.containsKey(userid)) {
            userRatings.put(userid, new WPHashMap<Integer, WPRating>());
        }
        userRatings.get(userid).put(movieid, newRating);

        numRatings += 1;

        return true;
    }

    /**
     * Removes a given rating based on the user ID and movie ID combination.
     * 
     * If no rating exists for the given user and movie combination, return false.
     * Otherwise, retrieve the WPRating object associated with the user and movie.
     * Subtract the rating value from the total rating sum for the given movie in movieRatingsSums.
     * 
     * Remove the WPRating from the movieRatings structure:
     * - If this removal leaves no more ratings for that movie, remove the movie entry altogether to avoid dangling keys.
     * 
     * Remove the WPRating from the userRatings structure:
     * - If this removal leaves no more ratings from that user, remove the user entry altogether.
     * 
     * Finally, decrement numRatings to reflect that one rating has been successfully removed.
     * 
     * This operation is O(1) on average due to hash map access times.
     *
     * @param userid  The ID of the user whose rating is to be removed
     * @param movieid The ID of the movie for which the rating is to be removed
     * @return TRUE if the rating was successfully removed, FALSE otherwise
     */
    @Override
    public boolean remove(int userid, int movieid) {
        if (!movieRatings.containsKey(movieid) || !movieRatings.get(movieid).containsKey(userid)) {
            return false;
        }

        WPRating ratingToRemove = movieRatings.get(movieid).get(userid);

        float previousSum = movieRatingsSums.get(movieid);
        movieRatingsSums.put(movieid, previousSum - ratingToRemove.getRating());

        movieRatings.get(movieid).remove(userid);
        if (movieRatings.get(movieid).size() == 0) {
            movieRatings.remove(movieid);
        }

        userRatings.get(userid).remove(movieid);
        if (userRatings.get(userid).size() == 0) {
            userRatings.remove(userid);
        }

        numRatings -= 1;

        return true;
    }
        
    /**
     * Sets (adds or updates) a rating for a given user ID and movie ID.
     * 
     * If the user has already rated the movie, the existing rating is first removed by calling remove(userid, movieid).
     * - This ensures that all associated structures (movieRatings, userRatings, movieRatingsSums, and numRatings) 
     *   are correctly updated to reflect the removal of the old rating.
     * 
     * After removal (or if no prior rating existed), a new rating is added by calling add(userid, movieid, rating, timestamp).
     * 
     * Therefore, the set operation effectively overwrites any existing rating for the same (user, movie) pair,
     * while correctly maintaining the internal consistency of the data structures.
     * 
     * This operation runs in O(1) time on average, because both remove and add are O(1) on average (due to hash map access).
     *
     * @param userid    The ID of the user setting the rating
     * @param movieid   The ID of the movie being rated
     * @param rating    The new rating value (between 0 and 5 inclusive)
     * @param timestamp The time at which the new rating was made
     * @return TRUE if the rating was successfully set (added or updated), FALSE otherwise
     */
    @Override
    public boolean set(int userid, int movieid, float rating, LocalDateTime timestamp) {
        remove(userid, movieid);
        return add(userid, movieid, rating, timestamp);
    }


    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieid) {
        WPHashMap<Integer, WPRating> singleMovieRatings = movieRatings.get(movieid);
        if (singleMovieRatings == null || singleMovieRatings.size() == 0) {
            return new float[0];
        }
        
        Integer[] keys = singleMovieRatings.getKeys();
        float[] ratings = new float[singleMovieRatings.size()];
        for (int i = 0; i < singleMovieRatings.size(); i++) {
            ratings[i] = singleMovieRatings.get(keys[i]).getRating();
        }

        return ratings;
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userid) {
        WPHashMap<Integer, WPRating> singleUserRatings = userRatings.get(userid);
        if (singleUserRatings == null || singleUserRatings.size() == 0) {
            return new float[0];
        }
        
        Integer[] keys = singleUserRatings.getKeys();
        float[] ratings = new float[singleUserRatings.size()];
        for (int i = 0; i < singleUserRatings.size(); i++) {
            ratings[i] = singleUserRatings.get(keys[i]).getRating();
        }

        return ratings;
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. 
     *         If the film cannot be found in Ratings, but does exist in the Movies store, return 0.0f. 
     *         If the film cannot be found in Ratings or Movies stores, return -1.0f.
     */
    @Override
    public float getMovieAverageRating(int movieid) {
        String movieTitle = stores.getMovies().getTitle(movieid);
        WPHashMap<Integer, WPRating> singleMovieRatings = movieRatings.get(movieid);
        if (movieTitle != null && singleMovieRatings == null) {
            return 0.0f;
        } 
        if (movieTitle == null && singleMovieRatings == null) {
            return -1.0f;
        }
        
        return movieRatingsSums.get(movieid) / singleMovieRatings.size();
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found in Ratings, or there are no rating, return -1.0f
     */
    @Override
    public float getUserAverageRating(int userid) {
        WPHashMap<Integer, WPRating> singleUserRatings = userRatings.get(userid);
        if (singleUserRatings == null) {
            return -1.0f;
        } 
        Integer[] keys = singleUserRatings.getKeys();
        float average = 0;

        for (int i = 0; i < singleUserRatings.size(); i++) {
            average += singleUserRatings.get(keys[i]).getRating();
        }

        return average / singleUserRatings.size();
    }

    /**
     * Retrieves the top N movies with the most ratings, ordered from most to least.
     * 
     * First, for each movie ID in movieRatings, create a WPPair where:
     * - The first element is the movie ID
     * - The second element is the number of ratings the movie has
     * 
     * Then, sort these WPPairs in descending order of number of ratings using IntroSort.
     * 
     * After sorting, populate a new array of movie IDs with the top num results.
     * If there are fewer movies than num, return as many as available.
     * 
     * This operation involves:
     * - O(n) to construct the array of WPPairs (where n is the number of rated movies)
     * - O(n log n) for sorting with IntroSort
     * - O(k) for copying the top results (where k = min(num, n))
     * 
     * @param num The maximum number of top-rated movies to return
     * @return A sorted array of movie IDs with the most ratings. Array size is at most num.
     */
    @SuppressWarnings("unchecked")
    @Override
    public int[] getMostRatedMovies(int num) {
        Integer[] keys = movieRatings.getKeys();
        WPPair<Integer, Integer>[] mypairs = new WPPair[keys.length];
        
        for (int i=0; i < keys.length; i++) {
            mypairs[i] = new WPPair<Integer, Integer>(keys[i], movieRatings.get(keys[i]).size());
        }

        IntroSort.introsort(mypairs);

        int[] sortedMovieIDs = new int[Math.min(num, keys.length)];
        for (int i = 0; i < Math.min(num, keys.length); i++) {
            sortedMovieIDs[i] = mypairs[i].getID();
        }
        
        
        return sortedMovieIDs;
    }

    /**
     * Retrieves the top N users who have submitted the most ratings, ordered from most to least.
     * 
     * First, for each user ID in userRatings, create a WPPair where:
     * - The first element is the user ID
     * - The second element is the number of ratings submitted by that user
     * 
     * Then, sort these WPPairs in descending order of number of ratings using IntroSort.
     * 
     * After sorting, populate a new array of user IDs with the top num results.
     * If there are fewer users than num, return as many as available.
     * 
     * This operation involves:
     * - O(n) to construct the array of WPPairs (where n is the number of users)
     * - O(n log n) for sorting with IntroSort
     * - O(k) for copying the top results (where k = min(num, n))
     * 
     * @param num The maximum number of top-rated users to return
     * @return A sorted array of user IDs with the most ratings. Array size is at most num.
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        Integer[] keys = userRatings.getKeys();
        @SuppressWarnings("unchecked")
        WPPair<Integer, Integer>[] mypairs = new WPPair[keys.length];
        
        for (int i=0; i < keys.length; i++) {
            mypairs[i] = new WPPair<Integer, Integer>(keys[i], userRatings.get(keys[i]).size());
        }

        IntroSort.introsort(mypairs);

        int[] sortedMovieIDs = new int[Math.min(num, keys.length)];
        for (int i = 0; i < Math.min(num, keys.length); i++) {
            sortedMovieIDs[i] = mypairs[i].getID();
        }
        
        
        return sortedMovieIDs;
    }

    /**
     * Retrieves the number of ratings associated with a specific movie.
     * 
     * First, check if the movie exists in the Movies store using the movie ID.
     * Then, attempt to retrieve all ratings for that movie from the Ratings structure.
     * 
     * The method handles three cases:
     * - If the movie does not exist in both the Movies and Ratings stores, return -1.
     * - If the movie exists in the Movies store but has no ratings in the Ratings store, return 0.
     * - If ratings exist for the movie, return the count of those ratings.
     * 
     * This method ensures that missing entries are properly distinguished from existing entries
     * with no ratings, according to the specified behavior.
     * 
     * This operation is O(1) on average due to direct hash map lookups.
     *
     * @param movieid The ID of the movie whose number of ratings is to be retrieved
     * @return The number of ratings for the movie, 0 if none, or -1 if the movie does not exist
     */
    @Override
    public int getNumRatings(int movieid) {
        String movieTitle = stores.getMovies().getTitle(movieid);
        WPHashMap<Integer, WPRating> singleMovieRatings = movieRatings.get(movieid);

        if (movieTitle == null && singleMovieRatings == null) {
            return -1; 
        } else if (movieTitle != null && (singleMovieRatings == null || singleMovieRatings.size() == 0)) {
            return 0;
        }
        return singleMovieRatings.size();

    }
    
    /**
     * Get the highest average rated film IDs, in order of there average rating
     * (hightst first).
     * 
     * @param numResults The maximum number of results to be returned
     * @return An array of the film IDs with the highest average ratings, highest
     *         first. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        Integer[] movieIDs = movieRatings.getKeys();
        @SuppressWarnings("unchecked")
        WPPair<Integer, Float>[] pairs = new WPPair[movieIDs.length];
        
        for (int i = 0; i < movieIDs.length; i++) {
            int movieID = movieIDs[i];
            float totalRating = movieRatingsSums.get(movieID);
            int numberOfRatings = movieRatings.get(movieID).size();
            float averageRating = totalRating / numberOfRatings;
            
            pairs[i] = new WPPair<Integer, Float>(movieID, averageRating);
        }
        
        // Sort by average rating
        IntroSort.introsort(pairs);
        
        int[] topMovies = new int[Math.min(numResults, movieIDs.length)];
        for (int i = 0; i < topMovies.length; i++) {
            topMovies[i] = (Integer) pairs[i].getID(); // Highest average first
        }
        
        return topMovies;
    }
    

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return numRatings;
    }
}


