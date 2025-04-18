package edu.comillas.icai.gitt.pat.spring.p5.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO#7
 * Añade 2 tests unitarios adicionales que validen diferentes casos
 * (no variaciones del mismo caso) de registro con datos inválidos
 */

class RegisterRequestUnitTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidPasswordRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.USER, "A1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        System.out.println(violations);
        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("password", violation.getPropertyPath().toString());
    }

    @Test
    public void testInvalidEmailRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", null,
                Role.USER, "tttttttA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        System.out.println(violations);
        assertEquals(1, violations.size());
        var violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString());
    }
}