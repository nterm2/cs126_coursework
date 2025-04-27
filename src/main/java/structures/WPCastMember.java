package structures;

import stores.Person;

public class WPCastMember extends WPCrewMember implements Comparable<WPCastMember>{
    private int appearances;
    private WPArrayList<Integer> starredFilms;

    public WPCastMember(Person person) {
        super(person);
        this.appearances = 0;
        this.starredFilms = new WPArrayList<Integer>();
    }

    @Override
    public void addFilm(int filmID) {
        if (!this.films.contains(filmID)) {
            this.films.add(filmID);
        }
        this.appearances++;
    }

    public int getAppearances() {
        return this.appearances;
    }

    public void addStarredFilm(int filmID) {
        if (!this.films.contains(filmID)) {
            this.starredFilms.add(filmID);
        }
    }

    public boolean containsStarredFilm(int filmID) {
        return this.starredFilms.contains(filmID);
    }
    
    public int[] getStarredFilms() {
        int[] castedStarredFilms = new int[starredFilms.size()];
        for (int i=0; i < starredFilms.size(); i++) {
            castedStarredFilms[i] = starredFilms.get(i);
        }
        return castedStarredFilms;
    }

    public void removeStarredFilm(int filmID) {
        if (this.films.contains(filmID)) {
            this.starredFilms.remove(filmID);
        }
    }

    @Override
    public int compareTo(WPCastMember other) {
        return Integer.compare(other.appearances, this.appearances);
    }

}
