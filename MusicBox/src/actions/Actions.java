package actions;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import media.*;
import musicEntities.Album;
import musicFormats.AbstractTrack;
import musicEntities.Playlist;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;

public class Actions {

    ArrayList<AbstractTrack> library = new ArrayList<AbstractTrack>();
    ArrayList<Playlist> playlists = new ArrayList<Playlist>();

    public Playlist createPlaylist(String name) {
        if (name == null) {
            return null;
        }
        Playlist temp = new Playlist(name);
        playlists.add(temp);
        return (new Playlist(name));
    }

    //return library number if library has the same track
    //return -1 if track hasn't been yet
    private int hasEqual(AbstractTrack track) {
        for (AbstractTrack temp : library) {
            if (temp.equals(track)) {
                return temp.getLibraryNumber();
            }
        }
        return -1;
    }

    //add trak to library
    private int addToLibrary(AbstractTrack track) {
        track.setLibraryNumber(library.size());
        library.add(track);
        return track.getLibraryNumber();
    }

    public Playlist createPlaylist(String name, AbstractTrack track) {
        if (name == null) {
            return null;
        }
        Playlist temp = new Playlist(name);
        if (this.hasEqual(track) != 1) {
            this.addToLibrary(track);
        }
        temp.addTrack(track.getLibraryNumber());
        playlists.add(temp);
        return temp;
    }

    public Playlist createPlaylist(String name, AbstractTrack[] tracks) {
        if (name == null) {
            return null;
        }
        Playlist temp = new Playlist(name);
        for (AbstractTrack tmp : tracks) {
            if (this.hasEqual(tmp) == -1) {
                this.addToLibrary(tmp);
            }
            temp.addTrack(tmp.getLibraryNumber());
        }
        playlists.add(temp);
        return temp;
    }

    public Playlist createPlaylist(String name, Album album) {
        if (name == null) {
            return null;
        }
        Playlist temp = new Playlist(name);
        for (AbstractTrack tmp : album.getTracks()) {
            if (this.hasEqual(tmp) == -1) {
                this.addToLibrary(tmp);
            }
            temp.addTrack(tmp.getLibraryNumber());
        }
        playlists.add(temp);
        return temp;
    }

    public Playlist createPlaylist(String name, Album[] albums) {
        if (name == null) {
            return null;
        }
        Playlist temp = new Playlist(name);
        for (Album tmpAlbum : albums) {
            for (AbstractTrack tmpTrack : tmpAlbum.getTracks()) {
                if (this.hasEqual(tmpTrack) == -1) {
                    this.addToLibrary(tmpTrack);
                }
                temp.addTrack(tmpTrack.getLibraryNumber());
            }
        }
        playlists.add(temp);
        return temp;
    }

    //calculate total duration of playlist
    public double calculateDuration(String name) {
        if (name == null) {
            return -1;
        }
        for (Playlist tmp : playlists) {
            if (tmp.getName().equals(name)) {
                double sum = 0;
                for (int number : tmp.getTracks()) {
                    sum += this.library.get(number).getLenght();
                }
                return sum;
            }
        }
        System.out.println("No such playlist!");
        return -1;
    }

    //calculate total size of playlist
    public double calculateSize(String name) {
        if (name == null) {
            return -1;
        }
        for (Playlist tmp : playlists) {
            if (tmp.getName().equals(name)) {
                double sum = 0;
                for (int number : tmp.getTracks()) {
                    for (AbstractTrack temp : library) {
                        if (temp.getLibraryNumber() == number) {
                            sum += temp.getSize();
                            break;
                        }
                    }
                }
                return sum;
            }
        }
        System.out.println("No such playlist!");
        return -1;
    }

    public boolean createRecording(String name) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter '1' for CD recording;");
            System.out.println("Enter '2' for DVD recording;");
            int num = sc.nextInt();
            switch (num) {
                case 1: {
                    AbstractDisc cd = new CD();
                    for (Playlist temp : this.playlists) {
                        if (temp.getName().equals(name)) {
                            if ((this.calculateSize(name)) > CD.capacity) {
                                System.out.println("This playlist is too big; try another one.");
                                return false;
                            }
                            //recording
                            System.out.println("Recording was successful!");
                            return true;
                        }
                    }
                    System.out.println("No such playlist;");
                    return false;
                }
                case 2: {
                    AbstractDisc dvd = new DVD();
                    for (Playlist temp : this.playlists) {
                        if (temp.getName().equals(name)) {
                            if ((this.calculateSize(name)) > DVD.capacity) {
                                System.out.println("This playlist is too big; try another one.");
                                return false;
                            }
                            //recording
                            System.out.println("Recording was successful!");
                            return true;
                        }
                    }
                    System.out.println("No such playlist;");
                    return false;
                }
                default: {
                    System.out.println("Incorrect input;");
                }
            }
            return false;
        } catch (InputMismatchException e) {
            System.out.println("incorrect input!");
            return false;

        }
    }

    //return playlist content
    public String getPlaylistContent(String name) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        for (Playlist temp : this.playlists) {
            if (temp.getName().equals(name)) {
                String content = "Playlist " + name + ":";
                int numberOfPlaylist = 1;
                for (int i : temp.getTracks()) {
                    content += "\n" + numberOfPlaylist + " " + this.library.get(i).getArtist() + " - " + this.library.get(i).getTitle() + " " + this.library.get(i).getLenght();
                    numberOfPlaylist++;
                }
                content += "\n\nTotal duration: " + nf.format(this.calculateDuration(name));
                content += "\nTotal size: " + this.calculateSize(name) + "\n____________";
                return content;
            }
        }
        System.out.println("\nNo such playlist!");
        return null;
    }

    //return content of all playlists
    public String getAllPlaylistContent() {
        String contents = "";
        for (Playlist temp : this.playlists) {
            contents += this.getPlaylistContent(temp.getName()) + "\n\n";
        }
        return contents;
    }

    //return library content
    public String getLibrary() {
        String contents = "";
        for (AbstractTrack track : this.library) {
            contents += track.getArtist() + " - " + track.getTitle() + " " + track.getLenght() + "\n";
        }
        return contents;
    }

    public void sortLibrary() {

        Collections.sort(this.library, new Comparator<AbstractTrack>() {

            public int compare(AbstractTrack o1, AbstractTrack o2) {
                return o1.getArtist().compareTo(o2.getArtist());
            }
        });
    }
}
