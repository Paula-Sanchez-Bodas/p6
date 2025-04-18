package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO#4
 * Crea el repositorio para la entidad AppUser de modo que,
 * además de las operaciones CRUD, se pueda consultar el AppUser asociado
 * a un email dado
 */

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}