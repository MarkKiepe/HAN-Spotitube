package nl.han.dea.markkiepe.spotitube.resources;

import nl.han.dea.markkiepe.spotitube.resources.dto.playlists.PlaylistDTO;
import nl.han.dea.markkiepe.spotitube.services.playlists.PlaylistService;
import nl.han.dea.markkiepe.spotitube.services.tracks.Track;
import nl.han.dea.markkiepe.spotitube.services.tracks.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Playlist Resource<br>
 * Resource that is listening to the path {@code "/playlists"}.
 * This resource is responsible for receiving all requests that involve anything with playlists.
 *
 * @see PlaylistService
 * @see TrackService
 *
 * @since 1.0
 * @author Mark Kiepe
 */
@Path("/playlists")
public class PlaylistResource {
    private PlaylistService playlistService;
    private TrackService trackService;

    /**
     * Setter for the {@link PlaylistService} that is used by this resource to get all the requested information.
     * @param playlistService {@link PlaylistService}
     * @since 1.0
     */
    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    /**
     * Setter for the {@link TrackService} that is used by this resource to get all the requested information.
     * @param trackService {@link TrackService}
     * @since 1.0
     */
    @Inject
    void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * Getter for all the playlists that are linked to the received Authentication Token.
     *
     * @see PlaylistService#getPlaylists(String)
     *
     * @param token {@link String} Authentication Token
     * @return {@link Response} in JSON
     * @since 1.0
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        return Response
                .status(Response.Status.OK)
                .entity(playlistService.getPlaylists(token))
                .build();
    }

    /**
     * Creates a new playlist for the given username.
     *
     * @see PlaylistService#createPlaylist(String, PlaylistDTO)
     *
     * @param token {@link String} Authentication Token that will create the playlist.
     * @param playlistDTO {@link PlaylistDTO} containing all data that is used to create the playlist.
     * @return {@link Response}
     * @since 1.0
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlaylist(@QueryParam("token") String token, PlaylistDTO playlistDTO) {
        return Response
                .status(Response.Status.OK)
                .entity(playlistService.createPlaylist(token, playlistDTO))
                .build();
    }

    /**
     * Deletes a specified playlist.
     *
     * @see PlaylistService#deletePlaylist(String, int)
     *
     * @param token {@link String} Authentication Token
     * @param playlistId {@link Integer} PlaylistId that you want to remove.
     * @return {@link Response}
     * @since 1.0
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}")
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId) {
        return Response
                .status(Response.Status.OK)
                .entity(playlistService.deletePlaylist(token, playlistId))
                .build();
    }

    /**
     * Updates a specified playlist
     *
     * @see PlaylistService#editPlaylist(String, int, PlaylistDTO)
     *
     * @param token {@link String} Authentication Token
     * @param playlistId {@link Integer} playlist that you want to update.
     * @param playlistDTO {@link PlaylistDTO} that contains the new information for the playlist.
     * @return {@link Response}
     * @since 1.0
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}")
    public Response updatePlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, PlaylistDTO playlistDTO) {
        return Response
                .status(Response.Status.OK)
                .entity(playlistService.editPlaylist(token, playlistId, playlistDTO))
                .build();
    }

    /**
     * Gets all the tracks in a specified playlist.
     *
     * @see TrackService#getTracksInPlaylist(String, int)
     *
     * @param token {@link String} Authentication Token
     * @param playlistId {@link Integer} playlistId you want all tracks from
     * @return {@link Response}
     * @since 1.0
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks")
    public Response getTracksInPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId) {
        return Response
                .status(Response.Status.OK)
                .entity(trackService.getTracksInPlaylist(token, playlistId))
                .build();
    }

    /**
     * Adds a new track to a specified playlist.
     *
     * @see TrackService#addTrackInPlaylist(String, int, Track)
     *
     * @param token {@link String} Authentication Token
     * @param playlistId {@link Integer} PlaylistId you want to add the track to
     * @param track {@link Track} containing track data.
     * @return {@link Response}
     * @since 1.0
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks")
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, Track track) {
        return Response
                .status(Response.Status.OK)
                .entity(trackService.addTrackInPlaylist(token, playlistId, track))
                .build();
    }

    /**
     * Removes a track from a specified playlist.
     *
     * @see TrackService#deleteTrackInPlaylist(String, int, int)
     *
     * @param token {@link String} Authentication Token.
     * @param playlistId {@link Integer} PlaylistId that you want to remove the track from.
     * @param trackId {@link Integer} TrackId that you want to remove from playlist.
     * @return {@link Response}
     * @since 1.0
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(
            @QueryParam("token") String token,
            @PathParam("playlistId") int playlistId,
            @PathParam("trackId") int trackId)
    {
        return Response
                .status(Response.Status.OK)
                .entity(trackService.deleteTrackInPlaylist(token, playlistId, trackId))
                .build();
    }
}
