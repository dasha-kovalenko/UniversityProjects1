package musicbox;

import actions.Actions;
import java.util.Scanner;
import musicEntities.Album;
import musicFormats.*;

public class Main {

    /**
     * @version 1.0 19 Sept 2011
     * @author Kiku Daria
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Actions actions = new Actions();
        AbstractTrack[] tracks = new MP3[5];
        tracks[0] = new MP3("Nuclear", 5.6, 3.44, 1, "Rock", "Anekdoten", "1");
        tracks[1] = new MP3("New Pin", 10.61, 8.2, 10, "Rock", "Oceansize", "3");
        tracks[2] = new MP3("Angelica", 6.1, 5.02, 3, "Rock", "Anathema", "4");
        tracks[3] = new MP3("Knots", 9.4, 6.02, 3, "Rock", "Gentle Giant", "5");
        tracks[4] = new MP3("La Fille Damnee", 6.8, 6.5, 2, "Folk", "Cecile Corbel", "6");
        AbstractTrack[] albumTracks = new MP3[4];
        albumTracks[0] = new MP3("Paperhouse", 5.6, 3.44, 1, "Rock", "Can", "2");
        albumTracks[1] = new MP3("Mushroom", 10.61, 8.2, 10, "Rock", "Can", "2");
        albumTracks[2] = new MP3("Oh Yeah", 6.1, 5.02, 3, "Rock", "Can", "2");
        albumTracks[3] = new MP3("Bring Me Coffee Or Tea", 9.4, 6.02, 3, "Rock", "Can", "2");
        Album album1 = new Album(albumTracks, "1995");
        AbstractTrack[] albumTracks2 = new FLAC[2];
        albumTracks2[0] = new FLAC("See No Evil", 5.6, 3.44, 1, "Rock", "Television", "2");
        albumTracks2[1] = new FLAC("Venus", 10.61, 8.2, 10, "Rock", "Television", "2");
        Album album2 = new Album(albumTracks2, "1992");
        Album[] albums = new Album[2];
        albums[0] = album1;
        albums[1] = album2;
        AbstractTrack bigTrack = new APE("Providence", 720, 3.44, 1, "Rock", "Godspeed You Black Emperor!", "2");

        actions.createPlaylist("playlist1");
        actions.createPlaylist("playlist2", tracks[0]);
        actions.createPlaylist("playlist3", tracks);
        actions.createPlaylist("playlist4", album1);
        actions.createPlaylist("playlist5", albums);
        actions.createPlaylist("playlist6", bigTrack);
        System.out.println(actions.getAllPlaylistContent());
        actions.sortLibrary();
        System.out.println("Library content:\n"+actions.getLibrary());

        System.out.println("Enter playlist name to record: ");
        String name = sc.nextLine();
        actions.createRecording(name);
    }
}
