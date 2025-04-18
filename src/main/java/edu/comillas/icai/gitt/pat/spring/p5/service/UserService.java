package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * TODO#6
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

@Service
public class UserService implements UserServiceInterface {

    // IoC
    // Dependency injection

    @Autowired //para no tener que crear manualmente un constructor
    private AppUserRepository appUserRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private Hashing hashing;

    public Token login(String email, String password) {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null) return null;

        String passwordHash = appUser.getPassword();

        if(!hashing.compare(passwordHash,password)){
            return null;
        }

        Token token = tokenRepository.findByAppUser(appUser);
        if (token != null) return token;

        token = new Token();
        token.appUser=appUser;
        token=tokenRepository.save(token);
        return token;
    }

    public AppUser authentication(String tokenId) {
        Token token= tokenRepository.findById(tokenId).orElse(null);
        if(token==null){
            return null;
        }
        return token.appUser;
    }

    public ProfileResponse profile(AppUser appUser) {
        ProfileResponse profileResponse=new ProfileResponse(appUser.getName(),appUser.getEmail(),appUser.getRole());
        return profileResponse;
    }
    public ProfileResponse profile(AppUser appUser, ProfileRequest profile) {
        AppUser appUser2= appUserRepository.findByEmail(appUser.getEmail());
        appUser2.setName(profile.name());
        appUser2.setRole(profile.role());
        String passwordHash = hashing.hash(profile.password());
        appUser2.setPassword(passwordHash);
        AppUser appUser3=appUserRepository.save(appUser2);
        return profile(appUser3);
    }
    public ProfileResponse profile(RegisterRequest register) {
        AppUser appUser2= appUserRepository.findByEmail(register.email());
        if(appUser2 != null){
            throw new ResponseStatusException(409, "Usuario ya registrado", null);
        }
        AppUser usuario=new AppUser();
        usuario.setName(register.name());
        usuario.setRole(register.role());
        usuario.setEmail(register.email());
        String passwordHash = hashing.hash(register.password());
        usuario.setPassword(passwordHash);
        AppUser usuarioCreado=appUserRepository.save(usuario);
        return profile(usuarioCreado);
    }

    public void logout(String tokenId) {
        var token=tokenRepository.findById(tokenId);
        token.ifPresent(value -> tokenRepository.delete(value));
    }

    public void delete(AppUser appUser) {
        AppUser appUser2 =appUserRepository.findByEmail(appUser.getEmail());
        if(appUser2!=null){
            appUserRepository.delete(appUser2);
        }
    }

}
