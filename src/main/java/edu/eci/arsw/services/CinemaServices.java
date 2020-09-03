/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.services;

import edu.eci.arsw.cinema.filter.*;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.*;

/**
 *
 * @author cristian
 */
@Service 
public class CinemaServices {
    @Autowired
    @Qualifier("InMemoryCinemaPersistence")
    CinemaPersitence cps;
    
    @Autowired
    @Qualifier("FilterAvailable")
    Filter filter;

    
    public void addNewCinema(Cinema c){
        cps.addCinema(c);
    }
    
    public List<Cinema> getAllCinemas(){
        return cps.getCinemas();
    }
    
    /**
     * 
     * @param name cinema's name
     * @return the cinema of the given name created by the given author
     * @throws CinemaException
     */
    public Cinema getCinemaByName(String name) throws CinemaException, CinemaPersistenceException{
        Cinema c=cps.getCinema(name);
        if(c == null){
            throw new CinemaException("Cinema no encontrado");
        }
        return c;
    }
    
  
    public void buyTicket(int row, int col, String cinema, String date, String movieName)throws CinemaPersistenceException, CinemaException{
        // throw new UnsupportedOperationException("Not supported yet."); 
        cps.buyTicket(row, col, cinema, date, movieName);
    }
    
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
       // throw new UnsupportedOperationException("Not supported yet."); 
        return cps.getFunctionsbyCinemaAndDate(cinema, date);
    }
    public List<Movie> filter(Cinema c, String filtro, String date){
        return filter.filter(c, filtro, date);
    }


}
