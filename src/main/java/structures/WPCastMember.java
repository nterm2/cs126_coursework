package structures;

import stores.Person;

public class WPCastMember {
    private WPArrayList<Integer> starredFilms;
    private Person person;
    private int appearances;

    public WPCastMember(Person person) {
        this.person = person;
        this.starredFilms = new WPArrayList<Integer>();
        this.appearances = 0;
    }

    public void addStarredFilm(int filmID) {
        if (!starredFilms.contains(filmID)) {
            starredFilms.add(filmID);
        }
        // increment appearances regardless of whether the castmember has already starred in a film or not.
        this.appearances++;
    }

    public Person getPerson() {
        return this.person;
    }

    public int getAppearances() {
        return this.appearances;
    }

    public int[] getStarredFilms() {
        int[] castedStarredFilms = new int[starredFilms.size()];
        for (int i=0; i < starredFilms.size(); i++) {
            castedStarredFilms[i] = starredFilms.get(i);
        }
        return castedStarredFilms;
    }

}
