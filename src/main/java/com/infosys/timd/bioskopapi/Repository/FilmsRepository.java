package com.infosys.timd.bioskopapi.Repository;

import com.infosys.timd.bioskopapi.DTO.*;
import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;
import com.infosys.timd.bioskopapi.Response.*;
import com.infosys.timd.bioskopapi.Service.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmsRepository extends JpaRepository<Films, Long> {

    @Query(value = "select * from films f where is_playing =?1", nativeQuery = true)
    public List<Films> getFilmByIsPlaying(Integer isPlaying);

    @Query(value = "select * from films f where is_playing =1", nativeQuery = true)
    public List<Films> getFilmIsPlaying(Integer isPlaying);

    @Query("select f.name, s2.studioName, s.price from Films f inner join Schedule s on f.filmId = s.films.filmId inner join Seats s2 on s.films.filmId= s2.seatId where f.name = ?1")
    public List<Films> getNamePriceStudio(String name);
}
