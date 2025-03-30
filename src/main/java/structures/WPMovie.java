package structures;

import java.time.LocalDate;

import stores.Company;
import stores.Genre;

public class WPMovie {
    // Attributes that are added through the add function in the Movies class
    int id;
    String title;
    String originalTitle;
    String overview;
    String tagline;
    String status;
    Genre[] genres;
    LocalDate release;
    long budget; 
    long revenue; 
    String[] languages;
    String originalLanguage;
    double runtime;
    String homepage;
    boolean adult;
    boolean video;
    String poster;

    // Attributes that are added through other methods in the Movies class. 
    WPArrayList<WPCollection> belongsToCollection;
    String imdbID;
    double popularity;
    WPArrayList<Company> productionCompanies; 
    WPArrayList<WPCountry> productionCountries;
    double voteAverage;
    int voteCount;

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
    }

    public int getID() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOriginialTitle() {
        return this.originalTitle;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getTagline() {
        return this.tagline;
    }

    public String getStatus() {
        return this.status;
    }

    public Genre[] getGenres() {
        return this.genres;
    }

    public LocalDate getRelease() {
        return this.release;
    }

    public long getBudget() {
        return this.budget;
    }

    public long getRevenue() {
        return this.revenue;
    }

    public String[] getLanguages() {
        return this.languages;
    }

    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    public double getRuntime() {
        return this.runtime;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public boolean getAdult() {
        return this.adult;
    }

    public boolean getVideo() {
        return this.video;
    }

    public String getPoster() {
        return this.poster;
    }

    public WPArrayList<WPCollection> getBelongsToCollection() {
        return this.belongsToCollection;
    }

    public String getImbdID() {
        return this.imdbID;
    }

    public double getPopularity() {
        return this.popularity;
    }


    public WPArrayList<Company> getProductionCompanies() {
        return this.productionCompanies;
    }

    public WPArrayList<WPCountry> getProductionCountries() {
        return this.productionCountries;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    public double getVoteCount() {
        return this.voteCount;
    }

    public void setTitle(String title) {
        this.title = title; 
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setIMDB(String imdbID) {
        this.imdbID = imdbID;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean addToCollection(WPCollection collection) {
        return this.belongsToCollection.add(collection);
    }

    public boolean addProductionCompany(Company company) {
        return this.productionCompanies.add(company);
    }

    public boolean addProductionCountry(WPCountry country) {
        return this.productionCountries.add(country);
    }

    
}
