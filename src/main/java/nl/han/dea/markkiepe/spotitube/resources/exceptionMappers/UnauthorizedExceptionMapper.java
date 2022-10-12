package nl.han.dea.markkiepe.spotitube.resources.exceptionMappers;

import nl.han.dea.markkiepe.spotitube.services.exceptions.IncorrectCredentialsException;
import nl.han.dea.markkiepe.spotitube.resources.AuthenticationResource;
import nl.han.dea.markkiepe.spotitube.services.exceptions.UnauthorizedException;

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
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

    /**
     * Creates a {@link Response} with the error code {@code Unauthorized} to inform the client they did not have the permissions
     * to perform this action<br>
     * @param e {@link UnauthorizedException}
     * @return {@link Response}
     * @since 1.0
     */
    @Override
    public Response toResponse(UnauthorizedException e) {
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .build();
    }

}
