package com.infosys.timd.bioskopapi.Service;

import com.infosys.timd.bioskopapi.DTO.*;
import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;
import com.infosys.timd.bioskopapi.Response.*;
import com.infosys.timd.bioskopapi.Service.*;
import com.infosys.timd.bioskopapi.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FilmsServiceImpl implements FilmsService {

    private final FilmsRepository filmsRepository;

    /**
     * Method to find ALL FILMS
     */
    @Override
    public List<Films> findAllFilms() {
        List<Films> optionalFilms = filmsRepository.findAll();
        if (optionalFilms.isEmpty()) {
            throw new ResourceNotFoundException("Table films has not value");
        }
        return filmsRepository.findAll();
    }

    /**
     * Method to find FILMS by ID
     */
    @Override
    public Optional<Films> findbyId(Long filmId) {
        Optional<Films> optionalFilms = filmsRepository.findById(filmId);
        if (optionalFilms.isEmpty()) {
            throw new ResourceNotFoundException("Films with id " + filmId + " is not exist");
        }
        return filmsRepository.findById(filmId);
    }

    /**
     * Method to Create FILMS
     */
    @Override
    public Films createFilm(Films films) {

        return filmsRepository.save(films);
    }

    public Films getReferenceById(Long id) {
        return this.filmsRepository.getReferenceById(id);
    }


    @Override
    public Films updateFilm(Films films, Long filmId) {
        Optional<Films> optionalFilms = filmsRepository.findById(filmId);
        if (optionalFilms.isEmpty()) {
            throw new ResourceNotFoundException("Films with id " + filmId + " is can't to update");
        }
        return filmsRepository.save(films);
    }

    @Override
    public void deleteFilmById(Long filmId) {
        Optional<Films> optionalFilms = filmsRepository.findById(filmId);
        if (optionalFilms.isEmpty()) {
            throw new ResourceNotFoundException("Films with id " + filmId + " is failed to delete");
        }
        filmsRepository.deleteAllById(Collections.singleton(filmId));
    }

    public List<Films> getByIsPlaying(Integer isPlaying) {

        return this.filmsRepository.getFilmByIsPlaying(isPlaying);
    }

    public List<Films> getIsPlaying(Integer isPlaying) {

        return this.filmsRepository.getFilmIsPlaying(isPlaying);
    }
}

