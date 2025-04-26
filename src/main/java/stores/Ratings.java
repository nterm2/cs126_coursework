package stores;

import java.time.LocalDateTime;

import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {
    Stores stores;
    WPHashMap<Integer, WPHashMap<Integer, WPRating>> movieRatings;
    WPHashMap<Integer, WPHashMap<Integer, WPRating>> userRatings;
    WPHashMap<Integer, Integer> movieRatingsSums;
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
        this.movieRatingsSums = new WPHashMap<Integer, Integer>();
    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
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
    
        if (!userRatings.containsKey(userid)) {
            userRatings.put(userid, new WPHashMap<Integer, WPRating>());
        }
        userRatings.get(userid).put(movieid, newRating);
    
        numRatings += 1; // Correct: increment every time a unique rating is added
    
        return true;
    }
    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userid, int movieid) {
        if (!movieRatings.containsKey(movieid) || !movieRatings.get(movieid).containsKey(userid)) {
            return false;
        }
    
        movieRatings.get(movieid).remove(userid);
        if (movieRatings.get(movieid).size() == 0) {
            movieRatings.remove(movieid); // clean up empty movies
        }
    
        userRatings.get(userid).remove(movieid);
        if (userRatings.get(userid).size() == 0) {
            userRatings.remove(userid); // clean up empty users
        }
    
        numRatings -= 1;
        return true;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the new rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userid, int movieid, float rating, LocalDateTime timestamp) {
        remove(userid, movieid); // Guaranteed to remove old if exists
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
        
        Integer[] keys = singleMovieRatings.getKeys();
        float average = 0;

        for (int i = 0; i < singleMovieRatings.size(); i++) {
            average += singleMovieRatings.get(keys[i]).getRating();
        }

        return average / singleMovieRatings.size();
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
     * Gets the top N movies with the most ratings, in order from most to least
     * 
     * @param num The number of movies that should be returned
     * @return A sorted array of movie IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @SuppressWarnings("unchecked")
    @Override
    public int[] getMostRatedMovies(int num) {
        Integer[] keys = movieRatings.getKeys();
        Pair<Integer, Integer>[] mypairs = new Pair[keys.length];
        
        for (int i=0; i < keys.length; i++) {
            mypairs[i] = new Pair<Integer, Integer>(keys[i], movieRatings.get(keys[i]).size());
        }

        QuickSort.quickSort(mypairs, 0, mypairs.length -1);

        int[] sortedMovieIDs = new int[Math.min(num, keys.length)];
        for (int i = 0; i < Math.min(num, keys.length); i++) {
            sortedMovieIDs[i] = (Integer) mypairs[keys.length - 1 - i].getID();
        }
        
        
        return sortedMovieIDs;
    }

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users in Ratings
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        Integer[] keys = userRatings.getKeys();
        Pair<Integer, Integer>[] mypairs = new Pair[keys.length];
        
        for (int i=0; i < keys.length; i++) {
            mypairs[i] = new Pair<Integer, Integer>(keys[i], userRatings.get(keys[i]).size());
        }

        QuickSort.quickSort(mypairs, 0, mypairs.length -1);

        int[] sortedMovieIDs = new int[Math.min(num, keys.length)];
        for (int i = 0; i < Math.min(num, keys.length); i++) {
            sortedMovieIDs[i] = (Integer) mypairs[keys.length - 1 - i].getID();
        }
        
        
        return sortedMovieIDs;
    }

       /**
     * Get the number of ratings that a movie has
     * 
     * @param movieid The movie id to be found
     * @return The number of ratings the specified movie has. 
     *         If the movie exists in the Movies store, but there are no ratings for it, then return 0. 
     *         If the movie does not exist in the Ratings or Movies store, then return -1.
     */
    @Override
    public int getNumRatings(int movieid) {
        // Check if the movie exists in the Movies store
        String movieTitle = stores.getMovies().getTitle(movieid);
        
        // If movie does not exist in Movies store, check if it exists in Ratings store
        WPHashMap<Integer, WPRating> singleMovieRatings = movieRatings.get(movieid);
        
        // If the movie doesn't exist in the Movies store, and there are no ratings for it, return -1
        if (movieTitle == null && singleMovieRatings == null) {
            return -1; // Movie doesn't exist in either store
        }
    
        // If the movie exists in the Movies store but has no ratings in the Ratings store, return 0
        if (movieTitle != null && (singleMovieRatings == null || singleMovieRatings.size() == 0)) {
            return 0; // Movie exists in Movies store but has no ratings
        }
    
        // If the movie exists in the Ratings store and has ratings, return the number of ratings
        if (singleMovieRatings != null) {
            return singleMovieRatings.size(); // Movie has ratings
        }
    
        return 0; // Default case, should never hit this line.
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
        // TODO Implement this function
        return null;
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


class Pair<K, V extends Number> implements Comparable<Pair<K,V>>{
    K id;
    V value; 

    public Pair(K id, V value) {
        this.id = id; 
        this.value = value;
    }

    @Override
    public int compareTo(Pair<K, V> other) {
        return Double.compare(this.value.doubleValue(), other.value.doubleValue());
    }

    public V getValue() {
        return this.value;
    }

    public K getID() {
        return this.id;
    }

}

class QuickSort {

    public static <T extends Comparable<T>> void quickSort(T[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = medianOfThreePartition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static <T extends Comparable<T>> int medianOfThreePartition(T[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        T a = arr[low];
        T b = arr[mid];
        T c = arr[high];

        int medianIndex;

        // Determine median of a (low), b (mid), c (high)
        if ((a.compareTo(b) <= 0 && b.compareTo(c) <= 0) || (c.compareTo(b) <= 0 && b.compareTo(a) <= 0)) {
            medianIndex = mid;
        } else if ((b.compareTo(a) <= 0 && a.compareTo(c) <= 0) || (c.compareTo(a) <= 0 && a.compareTo(b) <= 0)) {
            medianIndex = low;
        } else {
            medianIndex = high;
        }

        // Swap median with high so we can use it as pivot
        swap(arr, medianIndex, high);

        // Standard Lomuto partition using median as pivot
        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
