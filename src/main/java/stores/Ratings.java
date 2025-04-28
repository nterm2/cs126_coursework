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
     * Retrieves the average rating for a given movie based on user ratings.
     * 
     * First, checks whether the movie exists:
     * - If the movie exists in the Movies store but has no ratings in the Ratings store, return 0.0f.
     * - If the movie cannot be found in either the Movies store or the Ratings store, return -1.0f.
     * 
     * If the movie exists and has ratings:
     * - Fetch the total sum of ratings for the movie from movieRatingsSums.
     * - Divide the total sum by the number of ratings to compute the average rating.
     * 
     * This operation is O(1) on average due to hash map access times.
     *
     * @param movieid The ID of the movie
     * @return The average rating of the movie, 0.0f if no ratings exist, or -1.0f if the movie doesn't exist
     */
    @Override
    public float getMovieAverageRating(int movieid) {
        String movieTitle = stores.getMovies().getTitle(movieid);
        
        if (movieTitle == null && movieRatings.get(movieid) == null) {
            return -1.0f;
        }
        
        WPHashMap<Integer, WPRating> singleMovieRatings = movieRatings.get(movieid);
        if (singleMovieRatings == null || singleMovieRatings.size() == 0) {
            return 0.0f;
        }
    
        return movieRatingsSums.get(movieid) / singleMovieRatings.size();
    }
    

    /**
     * Retrieves the average rating given by a specific user across all their rated movies.
     * 
     * First, checks whether the user has any ratings:
     * - If the user does not exist in the Ratings store, or has no ratings, return -1.0f.
     * 
     * If the user exists and has ratings:
     * - Iterate through all of the user's ratings and compute the sum of their rating values.
     * - Divide the total by the number of ratings to produce the average rating.
     * 
     * This operation is O(N) where N is the number of ratings by the user.
     *
     * @param userid The ID of the user
     * @return The average rating the user has given, or -1.0f if no ratings exist
     */
    @Override
    public float getUserAverageRating(int userid) {
        WPHashMap<Integer, WPRating> singleUserRatings = userRatings.get(userid);
        if (singleUserRatings == null || singleUserRatings.size() == 0) {
            return -1.0f;
        }
    
        Integer[] movieIDs = singleUserRatings.getKeys();
        float total = 0.0f;
    
        for (Integer movieID : movieIDs) {
            total += singleUserRatings.get(movieID).getRating();
        }
        return total / singleUserRatings.size();
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
    @SuppressWarnings("unchecked")
    @Override
    public int[] getMostRatedUsers(int num) {
        Integer[] keys = userRatings.getKeys();
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
     * Retrieves the top N movies based on their average ratings, ordered from highest to lowest.
     * 
     * For each movie ID in the Ratings store:
     * - Calculate the average rating by dividing the total sum of ratings by the number of ratings.
     * - Create a WPPair containing the movie ID and its corresponding average rating.
     * 
     * After collecting all movie-average pairs:
     * - Sort the pairs in descending order based on their average ratings using IntroSort.
     * 
     * Return an array of the movie IDs corresponding to the highest average ratings,
     * up to a maximum of numResults. 
     * If there are fewer movies than numResults, return an array containing all available movies.
     * 
     * This operation depends on sorting and therefore runs in O(N log N) time,
     * where N is the number of movies with ratings.
     *
     * @param numResults The maximum number of top-rated movies to return
     * @return An array of movie IDs with the highest average ratings, sorted in descending order
     */
    @SuppressWarnings("unchecked")
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        Integer[] movieIDs = movieRatings.getKeys();
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


