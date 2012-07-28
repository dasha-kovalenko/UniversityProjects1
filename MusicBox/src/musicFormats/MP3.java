package musicFormats;

public class MP3 extends AbstractTrack {

    public MP3() {
    }

    public MP3(String title, double size, double lenght, int number, String genre, String artist, String album) {
        super(title, size, lenght, number, genre, artist, album);
    }
}
