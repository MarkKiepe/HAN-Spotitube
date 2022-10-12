package nl.han.dea.markkiepe.spotitube.resources.dto.playlists;

import nl.han.dea.markkiepe.spotitube.services.tracks.Track;

import java.util.ArrayList;

public class PlaylistDTO {

    private int id;
    private String name;
    private boolean owner;
    private ArrayList<Track> tracks;

    @Deprecated
    public PlaylistDTO() {
    }

    public PlaylistDTO(int id, String name, boolean owner, ArrayList<Track> tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
