package nl.han.dea.markkiepe.spotitube.resources.exceptionMappers;

import nl.han.dea.markkiepe.spotitube.resources.PlaylistResource;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UserNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for Incorrect Credentials
 * @see UserNotFoundException
 * @see PlaylistResource
 * @author Mark Kiepe
 * @since 1.0
 */
@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {

    /**
     * Creates a {@link Response} with the error code {@code Not Found} to inform the client the user could not be found.<br>
     * There is also a message that says the exact reason of why this exception was thrown.
     * @param e {@link UserNotFoundException}
     * @return {@link Response}
     * @since 1.0
     */
    @Override
    public Response toResponse(UserNotFoundException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(e.getMessage())
                .build();
    }

}
