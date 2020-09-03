/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.eci.arsw.cinema.persistence.*;
import edu.eci.arsw.cinema.model.*;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.List;
import java.util.logging.Level;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.PathVariable;
/**
 *
 * @author cristian
 */
@RestController
@RequestMapping(value = "/cinemas")
@Service
public class CinemaAPIController {
    @Autowired
    CinemaServices cinemaServices;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCinemas() {
         try {
            return new ResponseEntity<>(cinemaServices.getAllCinemas(), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(edu.eci.arsw.cinema.controllers.CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value="/{name}", method= RequestMethod.GET)
    public ResponseEntity<?> getCinemasName(@PathVariable String name) {
         try {
            return new ResponseEntity<>(cinemaServices.getCinemaByName(name), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(edu.eci.arsw.cinema.controllers.CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404",HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value="/{name}/{date}", method= RequestMethod.GET)
    public ResponseEntity<?> getCinemasName(@PathVariable String name,@PathVariable String date) {
         try {
            return new ResponseEntity<>(cinemaServices.getFunctionsbyCinemaAndDate(name,date), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(edu.eci.arsw.cinema.controllers.CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404",HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value="/{name}/{date}/{moviename}", method = RequestMethod.GET)
    public ResponseEntity<?> getMoviebyCinemaAndDate(@PathVariable String name, @PathVariable String date, @PathVariable String moviename) throws CinemaPersistenceException, CinemaException {
        try {
            CinemaFunction b = null;
            List<CinemaFunction> a = cinemaServices.getFunctionsbyCinemaAndDate(name, date);
            for (CinemaFunction i : a) {
                if (i.getMovie().getName().equals(moviename)) {
                    b = i;
                }
            }
            if (b == null) {
                throw new CinemaPersistenceException("No se encontro la funcion " + date);
            }
            return new ResponseEntity<>(b.getMovie(), HttpStatus.ACCEPTED);
        } catch (CinemaPersistenceException ex) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("HTTP 404", HttpStatus.NOT_FOUND);
        }

    }
}
