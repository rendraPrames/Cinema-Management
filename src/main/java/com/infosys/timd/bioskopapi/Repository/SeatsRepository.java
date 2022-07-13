package com.infosys.timd.bioskopapi.Repository;

import com.infosys.timd.bioskopapi.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Long> {
    @Query(value = "select * from seats s where is_available =?1", nativeQuery = true)
    public List<Seats> getSeatAvailable(Integer isAvailable);

    @Query(value = "select * from seats s where is_available =1", nativeQuery = true)
    public List<Seats> getSeatAvailableNew(Integer isAvailable);
}