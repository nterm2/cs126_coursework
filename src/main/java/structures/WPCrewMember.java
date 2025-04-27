package structures;

import stores.Person;

public class WPCrewMember {
    protected WPArrayList<Integer> films;
    protected Person person;

    public WPCrewMember(Person person) {
        this.person = person;
        this.films = new WPArrayList<Integer>();
    }

    public void addFilm(int filmID) {
        if (!this.films.contains(filmID)) {
            this.films.add(filmID);
        }
    }

    public void removeFilm(int filmID) {
        if (this.films.contains(filmID)) {
            this.films.remove(filmID);
        }
    }

    public boolean emptyFilms() {
        return this.films.size() == 0;
    }
    
    public Person getPerson() {
        return this.person;
    }

    public int[] getFilms() {
        int[] castedStarredFilms = new int[this.films.size()];
        for (int i = 0; i < this.films.size(); i++) {
            castedStarredFilms[i] = this.films.get(i);
        }
        return castedStarredFilms;
    }
}
