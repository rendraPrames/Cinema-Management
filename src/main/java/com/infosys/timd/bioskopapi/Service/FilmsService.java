package com.infosys.timd.bioskopapi.Service;

import com.infosys.timd.bioskopapi.DTO.*;
import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;
import com.infosys.timd.bioskopapi.Response.*;
import com.infosys.timd.bioskopapi.Service.*;
import com.infosys.timd.bioskopapi.Repository.*;

import java.util.List;
import java.util.Optional;

public interface FilmsService {
   List<Films> findAllFilms();
   Optional<Films> findbyId(Long filmId);
   Films createFilm(Films films);
   Films updateFilm(Films films, Long filmId);
   void deleteFilmById(Long id);
   List<Films> getByIsPlaying(Integer isPlaying);
   List<Films> getIsPlaying(Integer isPlaying);
//   List<Films> getNamePriceStudio(String name);
   Films getReferenceById (Long id);


}