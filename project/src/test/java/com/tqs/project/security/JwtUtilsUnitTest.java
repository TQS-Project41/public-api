package com.tqs.project.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class JwtUtilsUnitTest {

  private JwtUtils jwtUtils = new JwtUtils();

  @Test
  void whenGeneratingToken_thenReturnsString() {
    assertNotEquals(null, jwtUtils.generateJwtToken(1));
  }

  @Test
  void givenGeneratedToken_whenReadingId_thenReturnsEqual() {
    String token = jwtUtils.generateJwtToken(1);
    assertEquals("1", jwtUtils.getUserNameFromJwtToken(token));
  }

  @Test
  void givenValidToken_whenVerifying_thenReturnsTrue() {
    String token = jwtUtils.generateJwtToken(1);
    assertEquals(true, jwtUtils.validateJwtToken(token));
  }
  
}
