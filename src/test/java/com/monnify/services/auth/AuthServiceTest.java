package com.monnify.services.auth;

import com.monnify.Monnify;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceTest {

    @BeforeAll
    static void setUp() {
        assertNotNull(System.getenv("apiKey"));
        assertNotNull(System.getenv("secretKey"));
        Monnify.initialize(System.getenv("apiKey"), System.getenv("secretKey"));
    }
    private static volatile String token = "";

    @Test
    @Order(1)
    void getToken() {
        token = AuthService.getToken();
        assertNotNull(token);
        Assertions.assertThat(token).isNotBlank();
    }

    @Test
    @Order(2) // checking that the token gotten from the first request was cached
    void getToken2() {
        assertEquals(token, AuthService.getToken());
    }

    @Test
    @Order(3) // checking that the token gotten from the first request was cached
    void getToken3() {
        assertEquals(token, AuthService.getToken());
    }

}