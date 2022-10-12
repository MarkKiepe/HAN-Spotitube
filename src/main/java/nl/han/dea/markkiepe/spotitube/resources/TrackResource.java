package nl.han.dea.markkiepe.spotitube.resources;

import nl.han.dea.markkiepe.spotitube.services.tracks.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Track Resource<br>
 * Resource that is listening to the path {@code "/tracks"}.
 * This resource is responsible for receiving all requests that involve anything with tracks that are not related to any playlists.
 *
 * @see TrackService
 *
 * @since 1.0
 * @author Mark Kiepe
 */
@Path("/tracks")
public class TrackResource {
    private TrackService trackService;

    /**
     * Setter for the Track Service that is used by this resource to get all requested information.
     * @param trackService {@link TrackService}
     * @since 1.0
     */
    @Inject
    void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * Gets all Tracks that are not in the specified playlist.
     * @param token {@link String} Authentication Token
     * @param playlistId {@link Integer} PlaylistId.
     * @return {@link Response}
     * @since 1.0
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksNotInPlaylist(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId) {
        return Response
                .status(Response.Status.OK)
                .entity(trackService.getTracksNotInPlaylist(token, playlistId))
                .build();
    }

}
