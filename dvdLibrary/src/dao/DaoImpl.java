package dao;
import dto.Dvd;

import java.io.*;
import java.util.*;

public class DaoImpl implements Dao{
    public static final String LIBRARY_FILE = "Resources/library.txt";
    public static final String DELIMITER = "::";

    private Map<String, Dvd> dvds = new HashMap<>();

    @Override
    public Dvd addDvd(String title, Dvd dvd) throws DaoException{
        loadLibrary();
        Dvd prevDvd = dvds.put(title, dvd);
        writeLibrary();
        return prevDvd;
    }

    @Override
    public List<Dvd> getAllDvds() throws DaoException {
        loadLibrary();
        return new ArrayList<Dvd>(dvds.values());
    }

    @Override
    public Dvd getDvd(String title) throws DaoException {
        loadLibrary();
        return dvds.get(title);
    }

    @Override
    public Dvd removeDvd(String title) throws DaoException {
        loadLibrary();
        Dvd removedDvd = dvds.remove(title);
        writeLibrary();
        return removedDvd;
    }

    private Dvd unmarshallDvd(String dvdAsText){
        // dvdAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // Startrek::25/06/1973::A.Stephens::3.6::AStudios:: No notes
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in vddTokens.
        // Which should look like this:
        // ______________________________________________________
        // |        |          |           |   |        |        |
        // |Startrek|25/06/1973||A.Stephens|3.6|AStudios|No notes|
        // |        |          |           |   |        |        |
        // ------------------------------------------------------
        //  [0]         [1]         [2]     [3]    [4]     [5]
        String[] dvdTokens = dvdAsText.split(DELIMITER);

        // Given the pattern above, the title is in index 0 of the array.
        String title = dvdTokens[0];

        // Which we can then use to create a new Dvd object to satisfy
        // the requirements of the Dvd constructor.
        Dvd dvdFromFile = new Dvd(title);

        // However, there are remaining tokens that need to be set into the
        // new Dvd object. Do this manually by using the appropriate setters.

        // Index 1 - ReleaseDate
        dvdFromFile.setReleaseDate(dvdTokens[1]);

        // Index 2 - DirectorName
        dvdFromFile.setDirectorName(dvdTokens[2]);

        // Index 3 - MPAA rating
        dvdFromFile.setMpaaRating(dvdTokens[3]);

        // Index 4 - Studio
        dvdFromFile.setStudio(dvdTokens[4]);

        // Index 5 - UserNote
        dvdFromFile.setUserNote(dvdTokens[5]);

        // We have now created a dvd! Return it!
        return dvdFromFile;
    }

    private void loadLibrary() throws DaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new DaoException(
                    "-_- Could not load DVD library data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentStudent holds the most recent dvd unmarshalled
        Dvd currentDvd;
        // Go through LIBRARY_FILE line by line, decoding each line into a
        // dvd object by calling the unmarshallDvd method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Student
            currentDvd = unmarshallDvd(currentLine);

            // We are going to use the title as the map key for our dvd object.
            // Put currentDvd into the map using title as the key
            dvds.put(currentDvd.getTitle(), currentDvd);
        }
        // close scanner
        scanner.close();
    }

    private String marshallDvd(Dvd aDvd){
        // We need to turn a Dvd object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // Startrek::25/06/1973::A.Stephens::3.6::AStudios:: No notes

        // It's not a complicated process. Just get out each property,
        // and concatenate with our DELIMITER as a kind of spacer.

        // Start with the title, since that's supposed to be first.
        String dvdAsText = aDvd.getTitle() + DELIMITER;

        // add the rest of the properties in the correct order:

        // Release Date
        dvdAsText += aDvd.getReleaseDate() + DELIMITER;

        // Director Name
        dvdAsText += aDvd.getDirectorName() + DELIMITER;

        // MPAA Rating
        dvdAsText += aDvd.getMpaaRating();

        // Studio
        dvdAsText += aDvd.getStudio();

        // User Note - don't forget to skip the DELIMITER here.
        dvdAsText += aDvd.getUserNote();

        // We have now turned a student to text! Return it!
        return dvdAsText;
    }

    /**
     * Writes all DVDs in the library out to a LIBRARY_FILE.  See loadLibrary
     * for file format.
     *
     * @throws DaoException if an error occurs writing to the file
     */
    private void writeLibrary() throws DaoException {
        // NOTE FOR APPRENTICES: We are not handling the IOException - but
        // we are translating it to an application specific exception and
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us.  It is the responsibility of the calling code to
        // handle any errors that occur.
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new DaoException(
                    "Could not save DVD data.", e);
        }

        // Write out the Dvd objects to the library file.
        // NOTE TO THE APPRENTICES: We could just grab the DVD map,
        // get the Collection of DVDs and iterate over them but we've
        // already created a method that gets a List of DVDs so
        // we'll reuse it.
        String dvdAsText;
        List<Dvd> dvdList = this.getAllDvds();
        for (Dvd currentDvd : dvdList) {
            // turn a Dvd into a String
            dvdAsText = marshallDvd(currentDvd);
            // write the Dvd object to the file
            out.println(dvdAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }
}
