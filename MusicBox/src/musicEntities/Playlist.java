package musicEntities;

import java.util.ArrayList;

public class Playlist {

    private ArrayList<Integer> tracks = new ArrayList<Integer>();
    private String name;

    public Playlist(String name) {
        this.name = name;
    }

    public void addTrack(int number) {
        tracks.add(number);
    }

    public ArrayList<Integer> getTracks() {
        return this.tracks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
