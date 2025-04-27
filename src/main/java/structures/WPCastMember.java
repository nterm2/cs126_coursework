package structures;

import stores.Person;

// WPCastMember represents a cast member (actor/actress) associated with films
// Extends WPCrewMember and implements Comparable for sorting based on appearances
public class WPCastMember extends WPCrewMember implements Comparable<WPCastMember> {
    // Number of total film appearances (including starred and non-starred roles)
    private int appearances;

    // List of films where the cast member starred (important roles)
    private WPArrayList<Integer> starredFilms;

    // Constructor: initializes a cast member with a person, 0 appearances, and an empty starred films list
    public WPCastMember(Person person) {
        super(person);
        this.appearances = 0;
        this.starredFilms = new WPArrayList<Integer>();
    }

    // Adds a film to the crew member's film list and increments appearances
    @Override
    public void addFilm(int filmID) {
        if (!this.films.contains(filmID)) {
            this.films.add(filmID);
        }
        this.appearances++;
    }

    // Returns the number of appearances the cast member has
    public int getAppearances() {
        return this.appearances;
    }

    // Adds a starred film (only if it's not already marked as starred)
    public void addStarredFilm(int filmID) {
        if (!this.starredFilms.contains(filmID)) {
            this.starredFilms.add(filmID);
        }
    }

    // Checks if a particular film ID is in the starred films list
    public boolean containsStarredFilm(int filmID) {
        return this.starredFilms.contains(filmID);
    }
    
    // Returns an array of all film IDs where the member starred
    public int[] getStarredFilms() {
        int[] castedStarredFilms = new int[starredFilms.size()];
        for (int i = 0; i < starredFilms.size(); i++) {
            castedStarredFilms[i] = starredFilms.get(i);
        }
        return castedStarredFilms;
    }

    // Removes a film from the starred films list, if it exists
    public void removeStarredFilm(int filmID) {
        if (this.starredFilms.contains(filmID)) {
            this.starredFilms.remove(filmID);
        }
    }

    // Compares two cast members based on their number of appearances (more appearances come first)
    @Override
    public int compareTo(WPCastMember other) {
        return Integer.compare(other.appearances, this.appearances);
    }
}
