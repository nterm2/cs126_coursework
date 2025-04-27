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

    // Adds a film to the crew member's list, if not already added
    public void addFilm(int filmID) {
        if (!this.films.contains(filmID)) {
            this.films.add(filmID);
        }
    }

    // Removes a film from the crew member's list, if it exists
    public void removeFilm(int filmID) {
        if (this.films.contains(filmID)) {
            this.films.remove(filmID);
        }
    }

    // Checks if the crew member is not associated with any films
    public boolean emptyFilms() {
        return this.films.size() == 0;
    }
    
    // Returns the Person object representing this crew member
    public Person getPerson() {
        return this.person;
    }

    // Returns an array of film IDs that the crew member is associated with
    public int[] getFilms() {
        int[] castedStarredFilms = new int[this.films.size()];
        for (int i = 0; i < this.films.size(); i++) {
            castedStarredFilms[i] = this.films.get(i);
        }
        return castedStarredFilms;
    }
}
