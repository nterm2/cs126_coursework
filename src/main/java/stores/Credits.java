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
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The (unique) movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    /**
     * CHECK FOR UNIQUENESS FIRST, THEN  CREATE CREDIT OBJECT. ALLOWS US TO DO IT IN PLACE THAN HAVING TO CREATE A CLONEEEEE! LESS MEMORY USED
     */
    public boolean add(CastCredit[] cast, CrewCredit[] crew, int id) {
        
        if (credits.get(id) == null) {
            // Store the credit object in the hashmap of credits
            // need to sort cast and need to sort crew here. 
            // Quicksort seems to be the best bet
            WPCredit newCredit = new WPCredit(cast.clone(), crew.clone(), id);
            credits.put(id, newCredit);

            // Store each individual person in array of CastCredit 
            //, with the value being the person and the key being the id.
            // For cast credits:
            for (CastCredit singleCast : cast) {
                int currentCastID = singleCast.getID();
                WPCastMember castMember;
                
                if (castData.containsKey(currentCastID)) {
                    // Retrieve and update the existing instance
                    castMember = castData.get(currentCastID);
                } else {
                    // Create a new cast member and add the film
                    Person castPerson = new Person(singleCast.getID(), singleCast.getName(), singleCast.getProfilePath());
                    castMember = new WPCastMember(castPerson);
                }
                
                if (singleCast.getOrder() <= 3) {
                    castMember.addStarredFilm(id);
                }
                // Add the film ID (avoid duplicate addition inside addStarredFilm)
                castMember.addFilm(id);
                castData.put(currentCastID, castMember);
            }


            // Store each individual in the array of CrewCredit, with the value 
            // being the person, and the key being the id.
            // For cast credits:
            for (CrewCredit singleCrew : crew) {
                int currentCrewID = singleCrew.getID();
                WPCrewMember crewMember;
                
                if (crewData.containsKey(currentCrewID)) {
                    // Retrieve and update the existing instance
                    crewMember = crewData.get(currentCrewID);
                } else {
                    // Create a new cast member and add the film
                    Person crewPerson = new Person(singleCrew.getID(), singleCrew.getName(), singleCrew.getProfilePath());
                    crewMember = new WPCrewMember(crewPerson);
                }
                
                // Add the film ID (avoid duplicate addition inside addStarredFilm)
                crewMember.addFilm(id);
                crewData.put(currentCrewID, crewMember);
            }


            return true;
        }
        return false;
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        return credits.remove(id);
    }

    /**
     * Gets all the cast members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CastCredit objects, one for each member of cast that is 
     *         in the given film. The cast members should be in "order" order. If
     *         there is no cast members attached to a film, or the film cannot be 
     *         found in Credits, then return an empty array
     */
    @Override
    public CastCredit[] getFilmCast(int filmID) {
        if (credits.get(filmID) == null) {
            return new CastCredit[0];
        }
        return credits.get(filmID).getFilmCast();
    }

    /**
     * Gets all the crew members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CrewCredit objects, one for each member of crew that is
     *         in the given film. The crew members should be in "id" order (not "elementID"). If there 
     *         is no crew members attached to a film, or the film cannot be found in Credits, 
     *         then return an empty array
     */
    @Override
    public CrewCredit[] getFilmCrew(int filmID) {
        if (credits.get(filmID) == null) {
            return new CrewCredit[0];
        }
        return credits.get(filmID).getFilmCrew();
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        if (credits.get(filmID) == null) {
            return -1;
        }
        return credits.get(filmID).getCastSize();
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found in Credits, then return -1
     */
    @Override
    public int sizeOfCrew(int filmID) {
        if (credits.get(filmID) == null) {
            return -1;
        }
        return credits.get(filmID).getCrewSize();
    }

    /**
     * Gets a list of all unique cast members present in the data structure
     * 
     * @return An array of all unique cast members as Person objects. If there are 
     *         no cast members, then return an empty array
     */
    @Override
    public Person[] getUniqueCast() {
        Integer[] keys = castData.getKeys();
        Person[] uniqueCast = new Person[castData.size()];
        int counter = 0;

        for (int i = 0; i < castData.size(); i++) {
            uniqueCast[counter++] = castData.get(keys[i]).getPerson();
        }

        Person[] trimmedUniqueCast = new Person[counter];
        System.arraycopy(uniqueCast, 0, trimmedUniqueCast, 0, counter);
        return trimmedUniqueCast;
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    @Override
    public Person[] getUniqueCrew() {
        Integer[] keys = crewData.getKeys();
        Person[] uniqueCrew = new Person[crewData.size()];
        int counter = 0;

        for (int i = 0; i < crewData.size(); i++) {
            uniqueCrew[counter++] = crewData.get(keys[i]).getPerson();
        }

        Person[] trimmedUniqueCrew = new Person[counter];
        System.arraycopy(uniqueCrew, 0, trimmedUniqueCrew, 0, counter);
        return trimmedUniqueCrew;
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
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
            if (givenPerson.getName().contains(cast)) {
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
            if (givenPerson.getName().contains(crew)) {
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
     * @param castID The cast ID of the person to be found
     * @return The Person object corresponding to the cast ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCast(int castID) {
        if (castData.containsKey(castID)) {
            return castData.get(castID).getPerson();
        }
        return null;
    }

    /**
     * Gets the Person object corresponding to the crew ID
     * 
     * @param crewID The crew ID of the person to be found
     * @return The Person object corresponding to the crew ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCrew(int crewID){
        if (crewData.containsKey(crewID)) {
            return crewData.get(crewID).getPerson();
        }
        return null;
    }

    
    /**
     * Get an array of film IDs where the cast member has starred in
     * 
     * @param castID The cast ID of the person
     * @return An array of all the films the member of cast has starred
     *         in. If there are no films attached to the cast member, 
     *         then return an empty array
     */
    @Override
    public int[] getCastFilms(int castID){
        if (castData.containsKey(castID)) {
            return castData.get(castID).getFilms();
        }
        return new int[0];
    }

    /**
     * Get an array of film IDs where the crew member has starred in
     * 
     * @param crewID The crew ID of the person
     * @return An array of all the films the member of crew has starred
     *         in. If there are no films attached to the crew member, 
     *         then return an empty array
     */
    @Override
    public int[] getCrewFilms(int crewID) {
        if (crewData.containsKey(crewID)) {
            return crewData.get(crewID).getFilms();
        }
        return new int[0];
    }

    /**
     * Get the films that this cast member stars in (in the top 3 cast
     * members/top 3 billing). This is determined by the order field in
     * the CastCredit class
     * 
     * @param castID The cast ID of the cast member to be searched for
     * @return An array of film IDs where the the cast member stars in.
     *         If there are no films where the cast member has starred in,
     *         or the cast member does not exist, return an empty array
     */
    @Override
    public int[] getCastStarsInFilms(int castID){
        if (castData.containsKey(castID)) {
            WPCastMember cast = castData.get(castID);
            return cast.getStarredFilms();
        }
        return new int[0];
    }
    
    /**
     * Get Person objects for cast members who have appeared in the most
     * films. If the cast member has multiple roles within the film, then
     * they would get a credit per role played. For example, if a cast
     * member performed as 2 roles in the same film, then this would count
     * as 2 credits. The list should be ordered by the highest to lowest number of credits.
     * 
     * @param numResults The maximum number of elements that should be returned
     * @return An array of Person objects corresponding to the cast members
     *         with the most credits, ordered by the highest number of credits.
     *         If there are less cast members that the number required, then the
     *         list should be the same number of cast members found.
     */
    @Override
    public Person[] getMostCastCredits(int numResults) {
        if (castData.size() == 0) {
            return new Person[0];
        }
        Integer[] keys = castData.getKeys();
        // What we need to do is quicksort on all th cast members based on 
        // appearances. Then create a list of person of the size of wanted 
        // results. Iterate through each result, store the person by getting 
        // index at sorted list.getPerson().
        WPCastMember[] castMembers = new WPCastMember[castData.size()];
        for (int i=0; i < castData.size(); i++) {
            castMembers[i] = castData.get(keys[i]);
        }
        // Perform quicksort on castMembers
        castMemberQuickSort(castMembers, 0, castMembers.length - 1);
        numResults = numResults < castMembers.length ? numResults : castMembers.length;
        Person[] peopleMostCastCredits = new Person[numResults];
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
     * @param castID A cast ID representing the cast member to be found
     * @return The number of credits the given cast member has. If the cast member
     *         cannot be found, return -1
     */
    @Override
    public int getNumCastCredits(int castID) {
        if (castData.containsKey(castID)) {
            return castData.get(castID).getAppearances();
        }
        return -1;
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

    private void castMemberQuickSort(WPCastMember[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = castMembertMedianOfThreePartition(arr, low, high);
            castMemberQuickSort(arr, low, pivotIndex - 1);
            castMemberQuickSort(arr, pivotIndex + 1, high);
        }
    }

    private int castMembertMedianOfThreePartition(WPCastMember[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        int lowOrder = arr[low].getAppearances();
        int midOrder = arr[mid].getAppearances();
        int highOrder = arr[high].getAppearances();

        // Find the median index among low, mid, high
        int medianIndex;
        if ((lowOrder <= midOrder && midOrder <= highOrder) || (highOrder <= midOrder && midOrder <= lowOrder)) {
            medianIndex = mid;
        } else if ((midOrder <= lowOrder && lowOrder <= highOrder) || (highOrder <= lowOrder && lowOrder <= midOrder)) {
            medianIndex = low;
        } else {
            medianIndex = high;
        }

        // Move median to the end to use as pivot
        castMemberSwap(arr, medianIndex, high);
        return castMemberPartition(arr, low, high);
    }

    private int castMemberPartition(WPCastMember[] arr, int low, int high) {
        int pivot = arr[high].getAppearances();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].getAppearances() >= pivot) {
                i++;
                castMemberSwap(arr, i, j);
            }
        }

        castMemberSwap(arr, i + 1, high);
        return i + 1;
    }

    private void castMemberSwap(WPCastMember[] arr, int i, int j) {
        WPCastMember temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
