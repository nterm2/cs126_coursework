package stores;

import structures.*;

import interfaces.ICredits;

public class Credits implements ICredits{
    Stores stores;
    WPHashMap<Integer, WPCredit> credits;
    WPHashMap<Integer, WPCastMember> castData; 
    WPHashMap<Integer, WPCrewMember> crewData;
    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores, 
     *               including itself
     */
    public Credits (Stores stores) {
        this.stores = stores;
        this.credits = new WPHashMap<Integer, WPCredit>();
        this.castData = new WPHashMap<Integer, WPCastMember>();
        this.crewData = new WPHashMap<Integer, WPCrewMember>();
    }

    /**
     * Adds data about the people who worked on a given film. The movie ID should be
     * unique
     * 
     * In the case that a movie sharing the same movie id we want to add already exists 
     * in credits, return false. Otherwise, create a new credit, using WPCredit. As in later parts of Credits 
     * it is required that both cast and crew need to be sorted (which is achieved using an in-place sorting algorithm),
     * we pass into the constructor a clone of cast and crew, such that the original order of cast and crew is not changed, but
     * rather preserved (passing by value).
     * 
     * We then iterate through each cast member from the list of castcredits that we wish to add. In the case that the cast credit's member
     * has already been stored in castData, we get the cast member. Otherwise, we create a new person based on the id, name and profile 
     * path of the cast credit, and use WPCastMember to create a new cast member based on that person (storing it as castMember). As a cast
     * member who has an order greater than or equal to 3 is a in a starred film, if this is the case, then we add to the cast member's starred films 
     * the id for the given movie. We then add the film to the films for the cast member, and store the cast member into castData.
     * 
     * We subsequently iterate through each crew member from the list of crew credits that we wish to add. In the case that the crew credit's 
     * member has already been stored in crewData, we get the crew member. Otherwise, we create a new person based on the id, name and profile path
     * of the crew credit, and use WPCrewMember to create a new crew member based on that Person object (storing it as crewMember). We then add the film
     * to the films for the crew member, and store the crew member into crewData. 
     * 
     * O(n)
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The (unique) movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(CastCredit[] cast, CrewCredit[] crew, int id) {
        if (credits.get(id) != null) {
            return false;
        }

        WPCredit newCredit = new WPCredit(cast.clone(), crew.clone(), id);
        credits.put(id, newCredit);

        for (CastCredit singleCast : cast) {
            int currentCastID = singleCast.getID();
            WPCastMember castMember;
            
            if (castData.containsKey(currentCastID)) {
                castMember = castData.get(currentCastID);
            } else {
                Person castPerson = new Person(singleCast.getID(), singleCast.getName(), singleCast.getProfilePath());
                castMember = new WPCastMember(castPerson);
            }
            
            if (singleCast.getOrder() <= 3) {
                castMember.addStarredFilm(id);
            }
            
            castMember.addFilm(id);
            castData.put(currentCastID, castMember);
        }

        for (CrewCredit singleCrew : crew) {
            int currentCrewID = singleCrew.getID();
            WPCrewMember crewMember;
            
            if (crewData.containsKey(currentCrewID)) {
                crewMember = crewData.get(currentCrewID);
            } else {
                Person crewPerson = new Person(singleCrew.getID(), singleCrew.getName(), singleCrew.getProfilePath());
                crewMember = new WPCrewMember(crewPerson);
            }
            
            crewMember.addFilm(id);
            crewData.put(currentCrewID, crewMember);
        }


        return true;
    }

    /**
     * Remove a given films data from the data structure
     * NOT GOOD ENOUGH - LEAVES EXCESS DATA FROM CAST AND CREW. NEED TO COME BACK AND FIX.
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        WPCredit credit = credits.get(id);
        if (credit == null) {
            return false;
        }
    
        // Remove from credits map
        credits.remove(id);
    
        // Remove from castData
        for (CastCredit castCredit : credit.getCast()) {
            int castID = castCredit.getID();
            WPCastMember castMember = castData.get(castID);
            if (castMember != null) {
                castMember.removeFilm(id);
                if (castCredit.getOrder() <= 3) {
                    castMember.removeStarredFilm(id);
                }
                if (castMember.getFilms().isEmpty()) {
                    castData.remove(castID);
                }
            }
        }
    
        // Remove from crewData
        for (CrewCredit crewCredit : credit.getCrew()) {
            int crewID = crewCredit.getID();
            WPCrewMember crewMember = crewData.get(crewID);
            if (crewMember != null) {
                crewMember.removeFilm(id);
                if (crewMember.getFilms().isEmpty()) {
                    crewData.remove(crewID);
                }
            }
        }
    
        return true;
    }

    /**
     * Gets all the cast members for a given film
     * 
     * Get the movie credit for the given film id. In the case that it doesn't exist, return 
     * an empty array, otherwise return a list of CastCredit members sorted on each CastCredit's 
     * order, which is done by WPCredit's constructor that uses QuickSort to sort the cast accordingly.
     * 
     * O(1)
     * @param filmID The movie ID
     * @return An array of CastCredit objects, one for each member of cast that is 
     *         in the given film. The cast members should be in "order" order. If
     *         there is no cast members attached to a film, or the film cannot be 
     *         found in Credits, then return an empty array
     */
    @Override
    public CastCredit[] getFilmCast(int filmID) {
        WPCredit credit = credits.get(filmID);
        return credit == null ? new CastCredit[0] : credit.getFilmCast();
    }

    /**
     * Gets all the crew members for a given film
     * 
     * Get the movie credit for the given film id. In the case that it doesn't exist, return 
     * an empty array, otherwise return a list of CrewCredit members sorted on each CrewCredit's 
     * id, which is done by WPCredit's constructor that uses QuickSort to sort the crew accordingly.
     * 
     * O(1)
     * @param filmID The movie ID
     * @return An array of CrewCredit objects, one for each member of crew that is
     *         in the given film. The crew members should be in "id" order (not "elementID"). If there 
     *         is no crew members attached to a film, or the film cannot be found in Credits, 
     *         then return an empty array
     */
    @Override
    public CrewCredit[] getFilmCrew(int filmID) {
        WPCredit credit = credits.get(filmID);
        return credit == null ? new CrewCredit[0] : credit.getFilmCrew();
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * Get the movie credit for the given film id. In the case that it doesn't exist, return -1. otherwise 
     * return the size of the cast
     * 
     * O(1)
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        WPCredit credit = credits.get(filmID);
        return credit == null ? -1 : credit.getCastSize();
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * Get the movie credit for the given film id. In the case that it doesn't exist, return -1. otherwise 
     * return the size of the crew
     * 
     * O(1)
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCrew(int filmID) {
        WPCredit credit = credits.get(filmID);
        return credit == null ? -1 : credit.getCrewSize();
    }

    /**
     * Gets a list of all unique cast members present in the data structure
     * 
     * As in the add method, we ensure that each cast member added to castData is unique, we simply 
     * create a Person[] array, and then iterate through each cast in castdata. we then store the corresponding 
     * cast person into the Person[] array (uniqueCast), which we then return 
     * 
     * O(n)
     * @return An array of all unique cast members as Person objects. If there are 
     *         no cast members, then return an empty array
     */
    @Override
    public Person[] getUniqueCast() {
        Integer[] keys = castData.getKeys();
        Person[] uniqueCast = new Person[castData.size()];

        for (int i = 0; i < castData.size(); i++) {
            uniqueCast[i] = castData.get(keys[i]).getPerson();
        }

        return uniqueCast;
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * As in the add method, we ensure that each crew member added to crewData is unique, we simply create a
     * Person[] array called uniqueCrew, then iterate through each crew in crewdata. We then store the corresponding 
     * crew person into uniqueCrew, which we then return
     * 
     * O(n)
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    @Override
    public Person[] getUniqueCrew() {
        Integer[] keys = crewData.getKeys();
        Person[] uniqueCrew = new Person[crewData.size()];

        for (int i = 0; i < crewData.size(); i++) {
            uniqueCrew[i] = crewData.get(keys[i]).getPerson();
        }

        return uniqueCrew;
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * Create an array of Person objects called matchingCast that is the size of 
     * castData. Iterate through each cast member, get the the cast person's name. If 
     * their name contains the search term, then we add the person to matchingCast. Use System.arraycopy 
     * to remove potential null values from matchingCast, and return the resulting array.
     * 
     * O(n)
     * @param cast The string that needs to be found
     * @return An array of unique Person objects of all cast members that have the 
     *         requested string in their name. If there are no matches, return an 
     *         empty array
     */
    @Override
    public Person[] findCast(String cast) {
        Integer[] keys = castData.getKeys();
        Person[] matchingCast = new Person[castData.size()];
        int counter = 0;

        for (int i = 0; i < castData.size(); i++) {
            Person givenPerson = castData.get(keys[i]).getPerson();
            if (LowercaseContains.contains(givenPerson.getName(), cast)) {
                matchingCast[counter++] = givenPerson;
            }
        }

        Person[] trimmedMatchingCast = new Person[counter];
        System.arraycopy(matchingCast, 0, trimmedMatchingCast, 0, counter);
        return trimmedMatchingCast;
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * Create an array of Person objects called matchingCrew that is the size of 
     * crewData. Iterate through each crew member, get the the crew person's name. If 
     * their name contains the search term, then we add the person to matchingCrew. Use System.arraycopy 
     * to remove potential null values from matchingCrew, and return the resulting array.
     * 
     * O(n)
     * @param crew The string that needs to be found
     * @return An array of unique Person objects of all crew members that have the 
     *         requested string in their name. If there are no matches, return an 
     *         empty array
     */
    @Override
    public Person[] findCrew(String crew) {
        Integer[] keys = crewData.getKeys();
        Person[] matchingCrew = new Person[crewData.size()];
        int counter = 0;
        for (int i = 0; i < crewData.size(); i++) {
            Person givenPerson = crewData.get(keys[i]).getPerson();
            if (LowercaseContains.contains(givenPerson.getName(), crew)) {
                matchingCrew[counter++] = givenPerson;
            }
        }
        
        Person[] trimmedMatchingCrew = new Person[counter];
        System.arraycopy(matchingCrew, 0, trimmedMatchingCrew, 0, counter);
        return trimmedMatchingCrew;
    }

    /**
     * Gets the Person object corresponding to the cast ID
     * 
     * Get the cast member for the given cast id. In the case that it doesn't exist, return null. otherwise 
     * return the person associated with the cast member
     * 
     * O(1)
     * @param castID The cast ID of the person to be found
     * @return The Person object corresponding to the cast ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCast(int castID) {
        WPCastMember castMember = castData.get(castID);
        return castMember == null ? null : castMember.getPerson();
    }

    /**
     * Gets the Person object corresponding to the crew ID
     * 
     * Get the crew member for the given crew id. In the case that it doesn't exist, return null. otherwise 
     * return the person associated with the crew member
     * 
     * O(1)
     * @param crewID The crew ID of the person to be found
     * @return The Person object corresponding to the crew ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCrew(int crewID) {
        WPCrewMember crewMember = crewData.get(crewID);
        return crewMember == null ? null : crewMember.getPerson();
    }

    
    /**
     * Get an array of film IDs where the cast member has starred in
     * 
     * Get the cast member for the given cast id. In the case that it doesn't exist, return an empty array. otherwise 
     * return the films associated with the cast member
     * 
     * O(1)
     * @param castID The cast ID of the person
     * @return An array of all the films the member of cast has starred
     *         in. If there are no films attached to the cast member, 
     *         then return an empty array
     */
    @Override
    public int[] getCastFilms(int castID){
        WPCastMember castMember = castData.get(castID);
        return castMember == null ? new int[0] : castMember.getFilms();
    }

    /**
     * Get an array of film IDs where the crew member has starred in
     * 
     * Get the crew member for the given crew id. In the case that it doesn't exist, return an empty array. otherwise 
     * return the films associated with the crew member
     * 
     * O(1)
     * @param crewID The crew ID of the person
     * @return An array of all the films the member of crew has starred
     *         in. If there are no films attached to the crew member, 
     *         then return an empty array
     */
    @Override
    public int[] getCrewFilms(int crewID) {
        WPCrewMember crewMember = crewData.get(crewID);
        return crewMember == null ? new int[0] : crewMember.getFilms();
    }

    /**
     * Get the films that this cast member stars in (in the top 3 cast
     * members/top 3 billing). This is determined by the order field in
     * the CastCredit class
     * 
     * Get the cast member for the given cast id. In the case that it doesn't exist, return an empty array. otherwise 
     * return the starred films associated with the cast member
     * 
     * O(1)
     * @param castID The cast ID of the cast member to be searched for
     * @return An array of film IDs where the the cast member stars in.
     *         If there are no films where the cast member has starred in,
     *         or the cast member does not exist, return an empty array
     */
    @Override
    public int[] getCastStarsInFilms(int castID){
        WPCastMember castMember = castData.get(castID);
        return castMember == null ? new int[0] : castMember.getStarredFilms();
    }
    
    /**
     * Get Person objects for cast members who have appeared in the most
     * films. If the cast member has multiple roles within the film, then
     * they would get a credit per role played. For example, if a cast
     * member performed as 2 roles in the same film, then this would count
     * as 2 credits. The list should be ordered by the highest to lowest number of credits.
     * 
     * Get all of the cast mmbers from castData, and store in array of cast members.
     * Perform introsort on castMembers, then create a new array storing the top n 
     * people with the most appearances, which is based on the person attribute from 
     * the castmember object. Return the array
     * 
     * O(nlogn)
     * @param numResults The maximum number of elements that should be returned
     * @return An array of Person objects corresponding to the cast members
     *         with the most credits, ordered by the highest number of credits.
     *         If there are less cast members that the number required, then the
     *         list should be the same number of cast members found.
     */
    @Override
    public Person[] getMostCastCredits(int numResults) {
        Integer[] keys = castData.getKeys();
        WPCastMember[] castMembers = new WPCastMember[keys.length];
        for (int i=0; i < castData.size(); i++) {
            castMembers[i] = castData.get(keys[i]);
        }

        IntroSort.introsort(castMembers);

        Person[] peopleMostCastCredits = new Person[Math.min(numResults, keys.length)];
        for (int i=0; i < peopleMostCastCredits.length; i++) {
            peopleMostCastCredits[i] = castMembers[i].getPerson();
        }
        return peopleMostCastCredits;

    }

    /**
     * Get the number of credits for a given cast member. If the cast member has
     * multiple roles within the film, then they would get a credit per role
     * played. For example, if a cast member performed as 2 roles in the same film,
     * then this would count as 2 credits.
     * 
     * Get the number of cast credits for the given cast id. In the case that it doesn't exist, 
     * return -1. Otherwise, return the number of appearances for the given cast member.
     * 
     * O(1)
     * @param castID A cast ID representing the cast member to be found
     * @return The number of credits the given cast member has. If the cast member
     *         cannot be found, return -1
     */
    @Override
    public int getNumCastCredits(int castID) {
        WPCastMember castMember = castData.get(castID);
        return castMember == null ? -1 : castMember.getAppearances();
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return credits.size();
    }
}
