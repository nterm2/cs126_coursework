package structures;
import stores.*;

public class WPCredit {
    CastCredit[] cast;
    CrewCredit[] crew;
    int id;

    public WPCredit(CastCredit[] cast, CrewCredit[] crew, int id) {
        this.cast = cast;
        this.crew = crew; 
        this.id = id;
    }

    // todo - add sorting based on 'order' of cast
    // something like, for each cast
    public CastCredit[] getFilmCast() {
        return this.cast;
    }

    // todo - add sorting based on 'id' of cast
    public CrewCredit[] getFilmCrew() {
        return this.crew;
    }

    public int getMovieID() {
        return this.id;
    }

    public int getCastSize() {
        return this.cast.length;
    }

    public int getCrewSize() {
        return this.crew.length;
    }
}
