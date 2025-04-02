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
            WPMovie newMovie = new WPMovie(id, title, originalTitle, overview, tagline, status, genres, release, budget, revenue, languages, originalLanguage, runtime, homepage, adult, video, poster);
            movies.put(id, newMovie);
            return true;
        }
        return false;
    }

    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * 
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        return movies.remove(id);
    }

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs stored
     */
    @Override
    public int[] getAllIDs() {
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
     * included
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
            if (movies.get(keys[i]).getRelease() != null) {
                WPMovie givenMovie = movies.get(keys[i]);
                LocalDate release = givenMovie.getRelease();

                if (release.isAfter(start) && release.isBefore(end)) {
                    idsReleasedInRange[releasedInRangeCounter++] = givenMovie.getID();
                }
            }
        }

        int[] trimmedIDsReleasedInRange = new int[releasedInRangeCounter];
        System.arraycopy(idsReleasedInRange, 0, trimmedIDsReleasedInRange, 0, releasedInRangeCounter);
        return trimmedIDsReleasedInRange;
    }

    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getTitle();
    }

    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getOriginialTitle();
    }

    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getOverview();
    }

    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getTagline();
    }

    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getStatus();
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getGenres();
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public LocalDate getRelease(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getRelease();
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return -1; 
        } 
        return movie.getBudget();
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return -1; 
        } 
        return movie.getRevenue();
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getLanguages();
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getOriginalLanguage();
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1.0d
     */
    @Override
    public double getRuntime(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return -1.0d; 
        } 
        return movie.getRuntime();
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getHomepage();
    }

    /**
     * Gets weather a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return false; 
        } 
        return movie.getAdult();
    }

    /**
     * Gets weather a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return false; 
        } 
        return movie.getVideo();
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getPoster();
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
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
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1.0d
     */
    @Override
    public double getVoteAverage(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return -1.0d; 
        } 
        return movie.getVoteAverage();
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return -1; 
        } 
        return movie.getVoteCount();
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
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
        System.out.println("Attempting to add film " + filmID + " to collection " + collectionID);
        
        if (movies.get(filmID) == null) {
            System.out.println("Error: Movie with ID " + filmID + " does not exist.");
            return false;
        }
        if (collectionID < 0) {
            System.out.println("Error: Invalid collection ID " + collectionID);
            return false;
        }
        
        if (!collections.containsKey(collectionID)) {
            System.out.println("Collection " + collectionID + " does not exist. Creating new collection.");
            WPCollection collection = new WPCollection(collectionID, collectionName, collectionPosterPath, collectionBackdropPath);
            collections.put(collectionID, collection);
        }
        
        WPMovie movie = movies.get(filmID);
        WPCollection collection = collections.get(collectionID);
        
        System.out.println("Adding movie " + filmID + " to collection " + collectionID);
        movie.addToCollection(collection);
        movies.put(filmID, movie);
        
        System.out.println("Adding collection " + collectionID + " to movie " + filmID);
        collection.addToCollection(movie);
        collections.put(collectionID, collection);
        
        System.out.println("Successfully added movie " + filmID + " to collection " + collectionID);
        return true;
    }
    

    /**
     * Get all films that belong to a given collection
     * 
     * @param collectionID The collection ID to be searched for
     * @return An array of film IDs that correspond to the given collection ID. If
     *         there are no films in the collection ID, or if the collection ID is
     *         not valid, return an empty array.
     */
    @Override
    public int[] getFilmsInCollection(int collectionID) {
        WPCollection collection = collections.get(collectionID);
        if (collection == null) {
            return new int[0]; 
        } 
        return collection.getMovies();
    }

    /**
     * Gets the name of a given collection
     * 
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        WPCollection collection = collections.get(collectionID);
        if (collection == null) {
            return null; 
        } 
        return collection.getCollctionName();
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        WPCollection collection = collections.get(collectionID);
        if (collection == null) {
            return null; 
        } 
        return collection.getCollectionPosterPath();
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        WPCollection collection = collections.get(collectionID);
        if (collection == null) {
            return null; 
        } 
        return collection.getCollectionBackdropPath();
    }

    /**
     * Gets the collection ID of a given film
     * 
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public int getCollectionID(int filmID) {
        WPMovie movie = movies.get(filmID);
        if (movie == null) {
            return -1; 
        } 
        return movie.getCollectionID();
    }

    /**
     * Sets the IMDb ID for a given film
     * 
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
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        WPMovie movie = movies.get(filmID);
        if (movie == null) {
            return null; 
        } 
        return movie.getImbdID();
    }

    /**
     * Sets the popularity of a given film. If the popularity for a film already exists, replace it with the new value
     * 
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
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1.0d. If the popularity has not been set, return 0.0
     */
    @Override
    public double getPopularity(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return -1.0d; 
        } 
        return movie.getPopularity();
    }

    /**
     * Adds a production company to a given film
     * 
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return false; 
        } 
        return movie.addProductionCompany(company);
    }

    /**
     * Adds a production country to a given film
     * 
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return false; 
        } 
        return movie.addProductionCountry(country);
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getProductionCompanies();
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        WPMovie movie = movies.get(id);
        if (movie == null) {
            return null; 
        } 
        return movie.getProductionCountries();
    }

    /**
     * States the number of movies stored in the data structure
     * 
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
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {
        // TODO Implement this function
        return null;
    }
}