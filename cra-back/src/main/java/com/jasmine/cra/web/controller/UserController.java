package com.jasmine.cra.web.controller;


import com.jasmine.cra.dao.interfaces.UserDao;
import com.jasmine.cra.model.User;
import com.jasmine.cra.web.exceptions.UserIntrouvableException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Api( description="API pour les opérations CRUD sur les utilisateurs.")
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @ApiOperation(value = "Récupère la liste des utilisateurs.")
    @GetMapping(value= "/Users")
    public List listeUsers() {

        return userDao.findAll();
    }

    @ApiOperation(value = "Récupère un utilisateur grâce à son ID à condition que celui-ci existe!")
    @GetMapping(value = "/Users/{id}")
    public User afficherUnUtilisateur(@PathVariable int id) {

         User user = userDao.findById(id);

        if(user==null) throw new UserIntrouvableException("L'utilisateur avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        return user;
    }

    @ApiOperation(value = "Ajoute un utilisateur!")
     @PostMapping(value = "/Users")
      public ResponseEntity ajouterUser(@RequestBody User user) {

         User userAdded = userDao.save(user);

         if (userAdded == null)
             return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Supprime un utilisateur grâce à son ID à condition que celui-ci existe!")
    @DeleteMapping (value = "/Users/{id}")
    public void supprimerUser(@PathVariable int id) {

        userDao.deleteById(id);
    }

    @ApiOperation(value = "Mettre a jour un utilisateur")
    @PutMapping (value = "/Users")
    public void updateUser(@RequestBody User user) {
        userDao.save(user);
    }
}
