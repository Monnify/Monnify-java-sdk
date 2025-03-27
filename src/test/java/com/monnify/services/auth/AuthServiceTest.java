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

    @Test
    @Order(4) // checking that the token gotten from the first request was cached
    void getToken4() {
        assertEquals(token, AuthService.getToken());
    }

    @Test
    @Order(5) // checking that the token gotten from the first request was cached
    void getToken5() {
        assertEquals(token, AuthService.getToken());
    }

    @Test
    @Order(6) // checking that the token gotten from the first request was cached
    void getToken6() {
        assertEquals(token, AuthService.getToken());
    }

    @Test
    @Order(7) // checking that the token gotten from the first request was cached
    void getToken7() {
        assertEquals(token, AuthService.getToken());
    }

    @Test
    @Order(8) // checking that the token gotten from the first request was cached
    void getToken8() {
        assertEquals(token, AuthService.getToken());
    }

    @Test
    @Order(9) // checking that the token gotten from the first request was cached
    void getToken9() {
        assertEquals(token, AuthService.getToken());
    }

}