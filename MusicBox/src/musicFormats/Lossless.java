package musicFormats;

public abstract class Lossless extends AbstractTrack {

    protected Lossless() {
    }

    protected Lossless(String title, double size, double lenght, int number, String genre, String artist, String album) {
        super(title, size, lenght, number, genre, artist, album);
    }
}
