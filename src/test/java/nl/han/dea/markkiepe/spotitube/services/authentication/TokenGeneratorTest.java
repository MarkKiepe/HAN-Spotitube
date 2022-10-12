package nl.han.dea.markkiepe.spotitube.services.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TokenGeneratorTest {

    private SecureRandom secureRandom = new SecureRandom();
    private Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private TokenGenerator sut;

    @BeforeEach
    void setup() {
        // Arrange
        this.secureRandom = mock(SecureRandom.class);

        this.sut = new TokenGenerator();
        sut.setBase64Encoder(base64Encoder);
        sut.setSecureRandom(secureRandom);
    }

    @Test
    void doesReturnTokenLengthReturnANumber() {
        // Act
        var result = sut.getTokenLength();

        // Assert
        assertEquals(((Object) 1).getClass().getName(), ((Object) result).getClass().getName());
    }

    @Test
    void doesGenerateNewTokeReturnAString() {
        // Act
        var result = sut.generateNewToken();

        // Assert
        assertEquals(
                ((Object) "testString").getClass().getName(),
                ((Object) result).getClass().getName()
        );
    }

    @Test
    void doesGenerateNewTokenUseSecureRandom() {
        // Arrange
        int tokenLength = sut.getTokenLength();
        byte[] randomBytes = new byte[tokenLength];

        // Act
        sut.generateNewToken();

        // Assert
        verify(secureRandom, atLeastOnce()).nextBytes(randomBytes);
    }
}