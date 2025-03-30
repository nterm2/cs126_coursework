package stores;

import java.time.LocalDate;

import interfaces.IMovies;
import structures.WPArrayList;
import structures.WPHashMap;

public class Movies implements IMovies{
    Stores stores;
    WPArrayList<WPHashMap<Object, Object>> films;
    WPArrayList<WPHashMap<Object, Object>> collections;
    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Movies(Stores stores) {
        this.stores = stores;
        this.films = new WPArrayList<WPHashMap<Object, Object>>();
        this.collections = new WPArrayList<WPHashMap<Object, Object>>();
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
        WPHashMap<Object, Object> film = new WPHashMap<>();
        film.put("id", id);
        film.put("title", title);
        film.put("originalTitle", originalTitle);
        film.put("overview", overview);
        film.put("tagline", tagline);
        film.put("status", status);
        film.put("genres", genres);
        film.put("release", release);
        film.put("budget", budget);
        film.put("revenue", revenue);
        film.put("languages", languages);
        film.put("originalLanguage", originalLanguage);
        film.put("runtime", runtime);
        film.put("homepage", homepage);
        film.put("adult", adult);
        film.put("video", video);
        film.put("poster", poster);

        return films.add(film);
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                this.films.remove(film);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs stored
     */
    @Override
    public int[] getAllIDs() {
        int[] allIDs = new int[this.films.size()];
        for (int i=0; i < this.films.size(); i++) {
            allIDs[i] = (int) this.films.get(i).get("id");
        }
        return allIDs;
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
        /**
         * iterate through each film. get the release date
         */
        WPArrayList<Integer> tmpIDs = new WPArrayList<Integer>();
        for (int i=0; i < this.films.size(); i++) {
            LocalDate filmReleaseDate = (LocalDate) this.films.get(i).get("release");
            if (filmReleaseDate.isBefore(end) && filmReleaseDate.isAfter(start)) {
                tmpIDs.add((int) this.films.get(i).get("id"));
            }
        } 
        int[] idsInRange = new int[tmpIDs.size()];
        for (int i=0; i < tmpIDs.size(); i++) {
            idsInRange[i] = tmpIDs.get(i);
        }
        return idsInRange;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String) film.get("title");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String) film.get("originalTitle");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String) film.get("overview");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String) film.get("tagline");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String) film.get("status");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (Genre[]) film.get("genres");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (LocalDate) film.get("release");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (long) film.get("budget");
            }
        }
        return -1;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (long) film.get("revenue");
            }
        }
        return -1;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String[]) film.get("languages");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String) film.get("originalLanguage");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (double) film.get("runtime");
            }
        }
        return -1.0d;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String) film.get("homepage");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (boolean) film.get("adult");
            }
        }
        return false;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (boolean) film.get("video");
            }
        }
        return false;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (String) film.get("poster");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                film.put("voteAverage", voteAverage);
                film.put("voteCount", voteCount);
                return true;
            }
        }
        return false;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (double) film.get("voteAverage");
            }
        }
        return -1.0d;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (int) film.get("voteCount");
            }
        }
        return -1;
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
        WPHashMap<Object, Object> collection = new WPHashMap<Object, Object>();
        collection.put("filmID", filmID);
        collection.put("collectionID", collectionID);
        collection.put("collectionName", collectionName);
        collection.put("collectionPosterPath", collectionPosterPath);
        collection.put("collectionBackdropPath", collectionBackdropPath);
        return this.collections.add(collection);
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
        WPArrayList<Integer> tmpIDs = new WPArrayList<Integer>();

        for (int i=0; i < this.collections.size(); i++) {
            WPHashMap<Object, Object> collection = this.collections.get(i);
            if ((int) collection.get("collectionID") == collectionID) {
                tmpIDs.add((int) collection.get("filmID"));
            }
        }
        if (tmpIDs.size() > 0) {
            int[] filmsInCollection = new int[tmpIDs.size()];
            for (int i=0; i < tmpIDs.size(); i++) {
                filmsInCollection[i] = tmpIDs.get(i);
            }
            return filmsInCollection;
        }
        return new int[0];
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
        for (int i=0; i < this.collections.size(); i++) {
            WPHashMap<Object, Object> collection = this.collections.get(i);
            if ((int) collection.get("collectionID") == collectionID) {
                return (String) collection.get("collectionName");
            }
        }
        return null;
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
        for (int i=0; i < this.collections.size(); i++) {
            WPHashMap<Object, Object> collection = this.collections.get(i);
            if ((int) collection.get("collectionID") == collectionID) {
                return (String) collection.get("collectionPosterPath");
            }
        }
        return null;
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
        for (int i=0; i < this.collections.size(); i++) {
            WPHashMap<Object, Object> collection = this.collections.get(i);
            if ((int) collection.get("collectionID") == collectionID) {
                return (String) collection.get("collectionBackdropPath");
            }
        }
        return null;
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
        for (int i=0; i < this.collections.size(); i++) {
            WPHashMap<Object, Object> collection = this.collections.get(i);
            if ((int) collection.get("filmID") == filmID) {
                return (int) collection.get("collectionID");
            }
        }
        return -1;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == filmID) {
                film.put("imdbID", imdbID);
                return true;
            }
        }
        return false;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == filmID) {
                return (String) film.get("imdbID");
            }
        }
        return null;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                film.put("popularity", popularity);
                return true;
            }
        }
        return false;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                return (double) film.get("popularity");
            }
        }
        return -1.0d;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                film.put("company", company);
                return true;
            }
        }
        return false;
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
        for (int i=0; i < this.films.size(); i++) {
            WPHashMap<Object, Object> film = this.films.get(i);
            if ((int) film.get("id") == id) {
                film.put("productionCountry", country);
                return true;
            }
        }
        return false;
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
        return null;
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
        // TODO Implement this function
        return null;
    }

    /**
     * States the number of movies stored in the data structure
     * 
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
        return this.films.size();
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
