package musicEntities;

import musicFormats.AbstractTrack;

public class Album {

    private AbstractTrack[] tracks;
    private String year;
    private String artist;
    private MusicGenre genre;
    private int numberOfTracks;

    public AbstractTrack[] getTracks() {
        return this.tracks;
    }

    public Album(AbstractTrack[] tracks, String year) {
        this.tracks = tracks;
        this.artist = tracks[0].getArtist();
        this.genre = tracks[0].getGenre();
        this.numberOfTracks = tracks.length;
        this.year = year;
    }
}
