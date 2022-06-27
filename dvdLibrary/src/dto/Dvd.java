package dto;

public class Dvd {
    private String title;
    private String releaseDate;
    private String mpaaRating;
    private String directorName;

    private String studio;

    private String userNote;

    public Dvd(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getStudio() {
        return studio;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getUserNote() {
        return userNote;
    }
}
