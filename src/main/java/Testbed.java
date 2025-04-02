import java.time.LocalDate;

import stores.*;
import structures.*;
import utils.*;

public class Testbed {

    public static void main(String args[]) {
        LocalDate releaseDate = LocalDate.of(2022, 7, 15); // Example release date

        Genre[] genres = {new Genre(0, "Action"), new Genre(0, "Adventure")};
        String[] languages = {"English", "French"};

        WPMovie movie = new WPMovie(
            1,                                  // id
            "Example Movie",                     // title
            "Original Example Movie",            // originalTitle
            "An exciting journey of discovery.", // overview
            "The adventure begins.",             // tagline
            "Released",                           // status
            genres,                               // genres
            releaseDate,                          // release
            50000000L,                            // budget
            200000000L,                           // revenue
            languages,                            // languages
            "English",                            // originalLanguage
            120.5,                                // runtime (in minutes)
            "https://www.example.com",            // homepage
            false,                                // adult
            false,                                // video
            "https://www.example.com/poster.jpg"  // poster
        );
        WPCollection collection = new WPCollection(
        101,                                    // collectionID
        "Epic Adventure Collection",            // collectionName
        "https://www.example.com/poster.jpg",   // collectionPosterPath
        "https://www.example.com/backdrop.jpg"  // collectionBackdropPath
    );

        System.out.println();
    }
}
