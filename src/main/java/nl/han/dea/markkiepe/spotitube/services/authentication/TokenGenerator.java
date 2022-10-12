package nl.han.dea.markkiepe.spotitube.services.authentication;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * This class is responsible for creating Tokens that will be used for authentication of users.
 *
 * @see SecureRandom
 * @see Base64.Encoder
 * @author Mark Kiepe
 * @since 1.0
 */
public class TokenGenerator {
    private static final int TOKEN_LENGTH = 32;
    private SecureRandom secureRandom = new SecureRandom();
    private Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    /**
     * Setter for the {@link SecureRandom} of the Token Generator
     * @param secureRandom {@link SecureRandom}
     * @since 1.0
     */
    public void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }


    /**
     * Setter for the {@link Base64.Encoder} of the Token Generator
     * @param base64Encoder {@link Base64.Encoder}
     * @since 1.0
     */
    public void setBase64Encoder(Base64.Encoder base64Encoder) {
        this.base64Encoder = base64Encoder;
    }


    /**
     * Generates a {@link String} of random characters that form a token.
     *
     * @implNote This method does not check if this token already exists.
     *
     * @return Random {@link String} of characters
     * @since 1.0
     */
    public String generateNewToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }


    /**
     * Returns the amount of {@link Byte}s that is used to generate a new token.
     * This does not return the length of the {@link String} the token will have.
     * The length of the token will have a bigger size.
     *
     * @implNote This method is primarily used in tests to re-create an expected outcome.
     *
     * @return Amount of {@link Byte}s used to generate token
     * @since 1.0
     */
    public int getTokenLength() {
        return TOKEN_LENGTH;
    }

}
