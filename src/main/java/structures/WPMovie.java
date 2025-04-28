package structures;

import java.time.LocalDate;

import stores.Company;
import stores.Genre;

public class WPMovie {
    // Attributes that are added through the add function in the Movies class
    private int id;
    private String title;
    private String originalTitle;
    private String overview;
    private String tagline;
    private String status;
    private Genre[] genres;
    private LocalDate release;
    private long budget; 
    private long revenue; 
    private String[] languages;
    private String originalLanguage;
    private double runtime;
    private String homepage;
    private boolean adult;
    private boolean video;
    private String poster;

    // Attributes that are added through other methods in the Movies class. 
    private WPCollection belongsToCollection;
    private String imdbID;
    private double popularity;
    private WPArrayList<Company> productionCompanies; 
    private WPArrayList<String> productionCountries;
    private double voteAverage;
    private int voteCount;

    public WPMovie(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.tagline = tagline;
        this.status = status;
        this.genres = genres;
        this.release = release; 
        this.budget = budget; 
        this.revenue = revenue;
        this.languages = languages;
        this.originalLanguage = originalLanguage;
        this.runtime = runtime;
        this.homepage = homepage;
        this.adult = adult;
        this.video = video;
        this.poster = poster;

        this.productionCompanies = new WPArrayList<Company>();
        this.productionCountries = new WPArrayList<String>();
    }

    /**
     * Returns the unique ID of the movie.
     * 
     * This method retrieves the {@code id} field, which represents the unique identifier of the movie.
     * It provides a way to check the specific movie being referenced.
     * 
     * @return The unique ID of the movie.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns the title of the movie.
     * 
     * This method retrieves the {@code title} field, which represents the title of the movie in the language 
     * used by the studio. This is the main title shown on most movie listings.
     * 
     * @return The title of the movie.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the original title of the movie.
     * 
     * This method retrieves the {@code originalTitle} field, which represents the original title of the movie
     * as it was released in its country of origin.
     * 
     * @return The original title of the movie.
     */
    public String getOriginalTitle() {
        return this.originalTitle;
    }

    /**
     * Returns the overview of the movie.
     * 
     * This method retrieves the {@code overview} field, which provides a brief summary or description of the movie's plot.
     * 
     * @return The overview of the movie.
     */
    public String getOverview() {
        return this.overview;
    }

    /**
     * Returns the tagline of the movie.
     * 
     * This method retrieves the {@code tagline} field, which provides a short phrase or slogan associated with the movie.
     * 
     * @return The tagline of the movie.
     */
    public String getTagline() {
        return this.tagline;
    }

    /**
     * Returns the status of the movie.
     * 
     * This method retrieves the {@code status} field, which indicates the current production status of the movie,
     * such as "Released", "Post-production", etc.
     * 
     * @return The status of the movie.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Returns the genres associated with the movie.
     * 
     * This method retrieves the {@code genres} field, which contains an array of genres that classify the movie.
     * 
     * @return An array of genres for the movie.
     */
    public Genre[] getGenres() {
        return this.genres;
    }

    /**
     * Returns the release date of the movie.
     * 
     * This method retrieves the {@code release} field, which represents the date the movie was released to the public.
     * 
     * @return The release date of the movie.
     */
    public LocalDate getRelease() {
        return this.release;
    }

    /**
     * Returns the budget of the movie.
     * 
     * This method retrieves the {@code budget} field, which represents the total budget allocated for the production of the movie.
     * 
     * @return The budget of the movie.
     */
    public long getBudget() {
        return this.budget;
    }

    /**
     * Returns the revenue of the movie.
     * 
     * This method retrieves the {@code revenue} field, which represents the total revenue generated by the movie.
     * 
     * @return The revenue of the movie.
     */
    public long getRevenue() {
        return this.revenue;
    }

    /**
     * Returns the languages in which the movie is available.
     * 
     * This method retrieves the {@code languages} field, which contains an array of languages the movie has been translated into.
     * 
     * @return An array of languages the movie is available in.
     */
    public String[] getLanguages() {
        return this.languages;
    }

    /**
     * Returns the original language of the movie.
     * 
     * This method retrieves the {@code originalLanguage} field, which represents the primary language in which the movie was created.
     * 
     * @return The original language of the movie.
     */
    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    /**
     * Returns the runtime of the movie in minutes.
     * 
     * This method retrieves the {@code runtime} field, which represents the total length of the movie in minutes.
     * 
     * @return The runtime of the movie in minutes.
     */
    public double getRuntime() {
        return this.runtime;
    }

    /**
     * Returns the homepage URL of the movie.
     * 
     * This method retrieves the {@code homepage} field, which provides the URL to the official homepage or website of the movie.
     * 
     * @return The homepage URL of the movie.
     */
    public String getHomepage() {
        return this.homepage;
    }

    /**
     * Returns whether the movie is rated for adult audiences.
     * 
     * This method retrieves the {@code adult} field, which indicates if the movie is intended for adult viewers only.
     * 
     * @return {@code true} if the movie is rated for adults, {@code false} otherwise.
     */
    public boolean getAdult() {
        return this.adult;
    }

    /**
     * Returns whether the movie is available in video format.
     * 
     * This method retrieves the {@code video} field, which indicates whether the movie is available for viewing in video format.
     * 
     * @return {@code true} if the movie is available in video format, {@code false} otherwise.
     */
    public boolean getVideo() {
        return this.video;
    }

    /**
     * Returns the poster image URL of the movie.
     * 
     * This method retrieves the {@code poster} field, which provides the URL to the movie's poster image.
     * 
     * @return The poster image URL of the movie.
     */
    public String getPoster() {
        return this.poster;
    }

    /**
     * Returns the ID of the collection the movie belongs to, if applicable.
     * 
     * This method retrieves the collection ID from the {@code belongsToCollection} field, which represents the collection that the movie
     * belongs to, such as a movie series or franchise. If the movie doesn't belong to a collection, it returns {@code -1}.
     * 
     * @return The collection ID or {@code -1} if the movie doesn't belong to a collection.
     */
    public int getCollectionID() {
        if (this.belongsToCollection == null) {
            return -1;
        }
        return this.belongsToCollection.getCollectionID();
    }

    /**
     * Returns the IMDb ID of the movie.
     * 
     * This method retrieves the {@code imdbID} field, which represents the IMDb identifier of the movie.
     * 
     * @return The IMDb ID of the movie.
     */
    public String getImbdID() {
        return this.imdbID;
    }

    /**
     * Returns the popularity score of the movie.
     * 
     * This method retrieves the {@code popularity} field, which represents the relative popularity of the movie based on viewership or
     * other factors.
     * 
     * @return The popularity score of the movie.
     */
    public double getPopularity() {
        return this.popularity;
    }

    /**
     * Returns the list of production companies for the movie.
     * 
     * This method retrieves the {@code productionCompanies} field, which contains a list of companies involved in the production of the movie.
     * 
     * @return An array of {@code Company} objects representing the production companies.
     */
    public Company[] getProductionCompanies() {
        Company[] companies = new Company[this.productionCompanies.size()];
        for (int i = 0; i < this.productionCompanies.size(); i++) {
            companies[i] = this.productionCompanies.get(i);
        }
        return companies;
    }

    /**
     * Returns the list of production countries for the movie.
     * 
     * This method retrieves the {@code productionCountries} field, which contains a list of countries where the movie was produced.
     * 
     * @return An array of strings representing the production countries.
     */
    public String[] getProductionCountries() {
        String[] stringCountries = new String[this.productionCountries.size()];
        for (int i = 0; i < this.productionCountries.size(); i++) {
            stringCountries[i] = this.productionCountries.get(i);
        }
        return stringCountries;
    }

    /**
     * Returns the average rating of the movie.
     * 
     * This method retrieves the {@code voteAverage} field, which represents the average rating given to the movie by viewers.
     * 
     * @return The average rating of the movie.
     */
    public double getVoteAverage() {
        return this.voteAverage;
    }

    /**
     * Returns the total number of votes the movie has received.
     * 
     * This method retrieves the {@code voteCount} field, which represents the total number of votes the movie has received.
     * 
     * @return The total number of votes the movie has received.
     */
    public int getVoteCount() {
        return this.voteCount;
    }
    
    /**
     * Sets the title of the movie.
     * 
     * This method updates the {@code title} field with the new title for the movie. 
     * The title is usually the primary name of the movie shown in most listings.
     * 
     * @param title The new title of the movie.
     */
    public void setTitle(String title) {
        this.title = title; 
    }

    /**
     * Sets the original title of the movie.
     * 
     * This method updates the {@code originalTitle} field with the new original title for the movie, 
     * as it was released in its country of origin.
     * 
     * @param originalTitle The new original title of the movie.
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Sets the IMDb ID of the movie.
     * 
     * This method updates the {@code imdbID} field with the new IMDb identifier for the movie.
     * The IMDb ID is used to reference the movie on IMDb's platform.
     * 
     * @param imdbID The new IMDb ID of the movie.
     */
    public void setIMDB(String imdbID) {
        this.imdbID = imdbID;
    }

    /**
     * Sets the popularity score of the movie.
     * 
     * This method updates the {@code popularity} field with a new value representing the movie's popularity 
     * relative to other movies. A higher popularity value typically indicates higher viewership or public interest.
     * 
     * @param popularity The new popularity score of the movie.
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * Sets the average rating of the movie.
     * 
     * This method updates the {@code voteAverage} field with the new average rating of the movie, 
     * reflecting the overall user sentiment based on ratings and votes.
     * 
     * @param voteAverage The new average rating of the movie.
     */
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * Sets the total number of votes the movie has received.
     * 
     * This method updates the {@code voteCount} field with the new total count of votes given by viewers to the movie.
     * 
     * @param voteCount The new total number of votes the movie has received.
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * Adds the movie to a specific collection.
     * 
     * This method updates the {@code belongsToCollection} field, linking the movie to a particular collection, 
     * such as a movie series or franchise.
     * 
     * @param collection The collection to which the movie belongs.
     */
    public void addToCollection(WPCollection collection) {
        this.belongsToCollection = collection;
    }

    /**
     * Adds a production company to the list of companies involved in the movie's production.
     * 
     * This method adds a {@code Company} to the {@code productionCompanies} list. A production company is responsible for financing, 
     * creating, or distributing the movie.
     * 
     * @param company The production company to be added.
     * @return {@code true} if the company was successfully added, {@code false} otherwise.
     */
    public boolean addProductionCompany(Company company) {
        return this.productionCompanies.add(company);
    }

    /**
     * Adds a production country to the list of countries where the movie was produced.
     * 
     * This method adds a country to the {@code productionCountries} list. These are the countries where the movie was created or distributed.
     * 
     * @param country The production country to be added.
     * @return {@code true} if the country was successfully added, {@code false} otherwise.
     */
    public boolean addProductionCountry(String country) {
        return this.productionCountries.add(country);
    }

}
