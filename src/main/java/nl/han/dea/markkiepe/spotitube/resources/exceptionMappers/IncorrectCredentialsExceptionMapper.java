package nl.han.dea.markkiepe.spotitube.resources.exceptionMappers;

import nl.han.dea.markkiepe.spotitube.services.exceptions.IncorrectCredentialsException;
import nl.han.dea.markkiepe.spotitube.resources.AuthenticationResource;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception Mapper for Incorrect Credentials
 * @see IncorrectCredentialsException
 * @see AuthenticationResource
 * @author Mark Kiepe
 * @since 1.0
 */
@Provider
public class IncorrectCredentialsExceptionMapper implements ExceptionMapper<IncorrectCredentialsException> {

    /**
     * Creates a {@link Response} with the error code {@code Conflict} to inform the client the credentials are incorrect.<br>
     * There is also a message that says the exact reason of why this exception was thrown.
     * @param e {@link IncorrectCredentialsException}
     * @return {@link Response}
     * @since 1.0
     */
    @Override
    public Response toResponse(IncorrectCredentialsException e) {
        return Response
                .status(Response.Status.CONFLICT)
                .entity(e.getMessage())
                .build();
    }

}
