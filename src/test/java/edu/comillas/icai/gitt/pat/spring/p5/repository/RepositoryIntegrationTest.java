package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class RepositoryIntegrationTest {
    @Autowired TokenRepository tokenRepository;
    @Autowired AppUserRepository appUserRepository;

    /**
     * TODO#9
     * Completa este test de integración para que verifique
     * que los repositorios TokenRepository y AppUserRepository guardan
     * los datos correctamente, y las consultas por AppToken y por email
     * definidas respectivamente en ellos retornan el token y usuario guardados.
     */
    @Test void saveTest() {
        // Given ...
        AppUser user = new AppUser();
        Token token = new Token();

        final String email= "maria@gmail.com";
        final String name= "Maria";
        final String password= "Hola12345";
        final Role role= Role.USER;

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        token.appUser=user;

        appUserRepository.save(user);
        tokenRepository.save(token);

        // When ...


        AppUser usuario2=appUserRepository.findByEmail(email);
        Token token2=tokenRepository.findByAppUser(usuario2);

        // Then ...
        assertNotNull(usuario2);
        assertEquals(email,usuario2.getEmail());
        assertEquals(password,usuario2.getPassword());
        assertEquals(name,usuario2.getName());
        assertEquals(role,usuario2.getRole());

        assertNotNull(token2);
        assertEquals(usuario2,token2.appUser);
    }

    /**
     * TODO#10
     * Completa este test de integración para que verifique que
     * cuando se borra un usuario, automáticamente se borran sus tokens asociados.
     */

    @Test void deleteCascadeTest() {
        // Given ...
        AppUser user = new AppUser();
        Token token = new Token();

        final String email= "maria@gmail.com";
        final String name= "Maria";
        final String password= "Hola12345";
        final Role role= Role.USER;

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        user.setToken(token);
        token.appUser = user;

        user = appUserRepository.save(user);

        // When ...
        AppUser usuario2=appUserRepository.findByEmail(email);
        appUserRepository.delete(usuario2);


        // Then ...


        assertEquals(0, tokenRepository.count());

    }
}