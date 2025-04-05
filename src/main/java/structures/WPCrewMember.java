package structures;

import stores.Person;

public class WPCrewMember {
    private WPArrayList<Integer> starredFilms;
    private Person person;

    public WPCrewMember(Person person) {
        this.person = person;
        this.starredFilms = new WPArrayList<Integer>();
    }

    public void addStarredFilm(int filmID) {
        if (!starredFilms.contains(filmID)) {
            starredFilms.add(filmID);
        }
    }

    public Person getPerson() {
        return this.person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }


    public int[] getStarredFilms() {
        int[] castedStarredFilms = new int[starredFilms.size()];
        for (int i=0; i < starredFilms.size(); i++) {
            castedStarredFilms[i] = starredFilms.get(i);
        }
        return castedStarredFilms;
    }
}
