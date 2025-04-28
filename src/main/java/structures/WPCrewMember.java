package structures;

import stores.Person;

public class WPCrewMember {
    // List of films this crew member is associated with
    protected WPArrayList<Integer> films;

    // The person object representing the crew member
    protected Person person;

    // Constructor: initializes crew member with a person and an empty list of films
    public WPCrewMember(Person person) {
        this.person = person;
        this.films = new WPArrayList<Integer>();
    }

    /**
     * Adds a film to the crew member's list of films, if it is not already included.
     * 
     * This method checks whether the specified {@code filmID} is already in the crew member's list of films.
     * If the film is not already associated with the crew member, it is added to the list.
     * 
     * @param filmID The ID of the film to be added.
     */
    public void addFilm(int filmID) {
        if (!this.films.contains(filmID)) {
            this.films.add(filmID);
        }
    }

    /**
     * Removes a film from the crew member's list of films, if it exists.
     * 
     * This method checks whether the specified {@code filmID} is present in the crew member's list of films.
     * If the film exists, it is removed from the list.
     * 
     * @param filmID The ID of the film to be removed.
     */
    public void removeFilm(int filmID) {
        if (this.films.contains(filmID)) {
            this.films.remove(filmID);
        }
    }

    /**
     * Checks if the crew member is not associated with any films.
     * 
     * This method returns {@code true} if the crew member has no films associated with them, 
     * and {@code false} otherwise.
     * 
     * @return {@code true} if the crew member has no films, {@code false} otherwise.
     */
    public boolean emptyFilms() {
        return this.films.size() == 0;
    }

    /**
     * Returns the {@code Person} object representing this crew member.
     * 
     * This method retrieves the {@code Person} associated with the crew member, 
     * which provides details about the crew member's personal information.
     * 
     * @return The {@code Person} object representing the crew member.
     */
    public Person getPerson() {
        return this.person;
    }

    /**
     * Returns an array of film IDs that the crew member is associated with.
     * 
     * This method creates an array of {@code int} containing the film IDs 
     * representing the films the crew member has worked on.
     * 
     * @return An array of film IDs associated with the crew member.
     */
    public int[] getFilms() {
        int[] castedStarredFilms = new int[this.films.size()];
        for (int i = 0; i < this.films.size(); i++) {
            castedStarredFilms[i] = this.films.get(i);
        }
        return castedStarredFilms;
    }

}
