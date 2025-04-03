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

    public CastCredit[] getFilmCast() {
        return this.cast;
    }

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
