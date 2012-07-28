package musicFormats;

public class FLAC extends Lossless {

    public FLAC() {
    }

    public FLAC(String title, double size, double lenght, int number, String genre, String artist, String album) {
        super(title, size, lenght, number, genre, artist, album);
    }
}
