package com.monnify;

import com.monnify.models.MonnifyBaseResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void main() {
    }

    public static void assertSuccess(MonnifyBaseResponse<?> response) {
        assertNotNull(response);
        assertTrue(response.isRequestSuccessful());
        assertNotNull(response.getResponseBody());
    }
}