package structures;

import stores.Person;

/**
 * Represents a cast member (actor/actress) associated with films.
 * This class extends {@link WPCrewMember} to inherit crew member properties and implements 
 * {@link Comparable} to allow sorting based on the number of appearances or other criteria.
 * A {@code WPCastMember} object contains details specific to cast members, including 
 * their name, role, and the films they have appeared in.
 * 
 * {@code WPCastMember} can be compared to other cast members based on the number of 
 * appearances they have made, enabling sorting by this attribute.
 * 
 * @see WPCrewMember for crew-related attributes and methods
 * @see Comparable for the sorting mechanism based on appearances
 */
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

    /**
     * Adds a film to the crew member's film list and increments the number of appearances.
     * 
     * This method checks if the film, identified by filmID, is already present in the crew member's
     * list of films. If not, it adds the film to the list and increments the appearances counter by one. 
     * This helps keep track of how many films the crew member has appeared in or worked on.
     * 
     * @param filmID The unique identifier for the film to be added to the crew member's film list.
     */
    @Override
    public void addFilm(int filmID) {
        if (!this.films.contains(filmID)) {
            this.films.add(filmID);
        }
        this.appearances++;
    }

    /**
     * Returns the number of appearances the cast member has made.
     * 
     * This method retrieves the value of the appearances field, which tracks how many films 
     * the cast member has appeared in. It provides a way to check the total number of appearances 
     * for the cast member across all their films.
     * 
     * @return The total number of appearances made by the cast member.
     */
    public int getAppearances() {
        return this.appearances;
    }

    /**
     * Adds a starred film to the cast member's list of starred films.
     * 
     * This method adds the provided film ID to the list of starred films for the cast member, 
     * but only if it is not already marked as starred. The list of starred films helps keep track 
     * of the films that the cast member has starred in.
     * 
     * @param filmID The ID of the film to be added to the starred films list.
     */
    public void addStarredFilm(int filmID) {
        if (!this.starredFilms.contains(filmID)) {
            this.starredFilms.add(filmID);
        }
    }

    /**
     * Checks if a particular film ID is in the cast member's list of starred films.
     * 
     * This method checks whether the provided film ID is present in the list of starred films 
     * for the cast member. The starred films list contains films in which the cast member has 
     * played a starring role.
     * 
     * @param filmID The ID of the film to check for in the starred films list.
     * @return true if the film ID is found in the starred films list, 
     *         false otherwise.
     */
    public boolean containsStarredFilm(int filmID) {
        return this.starredFilms.contains(filmID);
    }
    
    /**
     * Returns an array of all film IDs in which the cast member starred.
     * 
     * This method retrieves the list of films where the cast member has played a starring role 
     * and returns it as an array. It iterates through the list of starred films and stores each 
     * film's ID in the array.
     * 
     * @return An array of integers representing the film IDs in which the cast member starred.
     */
    public int[] getStarredFilms() {
        int[] castedStarredFilms = new int[starredFilms.size()];
        for (int i = 0; i < starredFilms.size(); i++) {
            castedStarredFilms[i] = starredFilms.get(i);
        }
        return castedStarredFilms;
    }

    /**
     * Removes a film from the starred films list if it exists.
     * 
     * This method checks if the specified film ID is present in the list of starred films. 
     * If the film is found, it removes it from the list. This ensures that only films marked 
     * as starred by the cast member are present in the list.
     * 
     * @param filmID The ID of the film to be removed from the starred films list.
     */
    public void removeStarredFilm(int filmID) {
        if (this.starredFilms.contains(filmID)) {
            this.starredFilms.remove(filmID);
        }
    }

    /**
     * Compares two cast members based on their number of appearances.
     * 
     * This method compares the current cast member to another based on the total number of appearances they have made in films. 
     * The comparison is made in descending order, so cast members with more appearances will come first.
     * 
     * @param other The cast member to be compared with the current one.
     * @return A negative integer, zero, or a positive integer if this cast member has fewer, equal, or more appearances than the other.
     */
    @Override
    public int compareTo(WPCastMember other) {
        return Integer.compare(other.appearances, this.appearances);
    }
}
