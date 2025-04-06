package structures;

import stores.Person;

public class WPCastMember extends WPCrewMember {
    private int appearances;

    public WPCastMember(Person person) {
        super(person);
        this.appearances = 0;
    }

    @Override
    public void addStarredFilm(int filmID) {
        if (!this.films.contains(filmID)) {
            this.films.add(filmID);
        }
        this.appearances++;
    }

    public int getAppearances() {
        return this.appearances;
    }
}
