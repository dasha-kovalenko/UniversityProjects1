package musicFormats;

import musicEntities.MusicGenre;

public abstract class AbstractTrack {

    protected String title;
    protected double size; //Mb
    protected double lenght; //min
    protected int number;
    protected int libraryNumber;
    protected MusicGenre genre;
    protected String artist;
    protected String album;

    protected AbstractTrack(String title, double size, double lenght, int number, String genre, String artist, String album) {
        this.title = title;
        this.size = size;
        this.lenght = lenght;
        this.number = number;
        this.libraryNumber = 0;
        this.genre = MusicGenre.valueOf(genre);
        this.artist = artist;
        this.album = album;
    }

    public AbstractTrack() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public double getSize() {
        return this.size;
    }

    public double getLenght() {
        return this.lenght;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void setLibraryNumber(int number) {
        this.libraryNumber = number;
    }

    public int getLibraryNumber() {
        return this.libraryNumber;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public MusicGenre getGenre() {
        return this.genre;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getalbum() {
        return this.album;
    }

    @Override
    public boolean equals(Object o) {
        AbstractTrack track = (AbstractTrack) o;
        if (!this.artist.equals(track.artist)) {
            return false;
        }
        if (!this.genre.equals(track.genre)) {
            return false;
        }
        if (!(this.lenght == track.lenght)) {
            return false;
        }
        if (!(this.size == track.size)) {
            return false;
        }
        if (!this.title.equals(track.title)) {
            return false;
        }
        return true;
    }
}
