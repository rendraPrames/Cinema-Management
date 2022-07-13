package com.infosys.timd.bioskopapi.Service;

import com.infosys.timd.bioskopapi.DTO.*;
import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;
import com.infosys.timd.bioskopapi.Response.*;
import com.infosys.timd.bioskopapi.Service.*;
import com.infosys.timd.bioskopapi.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatsServiceImpl implements SeatsService {


    private final SeatsRepository seatRepository;

    @Autowired
    public SeatsServiceImpl(SeatsRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seats> findAllseats() {

        List<Seats> optionalSeats = seatRepository.findAll();
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("table seats have not value");
        }
        return seatRepository.findAll();
    }

    public Optional<Seats> findbyid(Long id){

        Optional<Seats> optionalSeats = seatRepository.findById(id);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException(" Seats not Exist with id :" + id);
        }
        return seatRepository.findById(id);
    }

    public Seats createseat(Seats seat) {
        return seatRepository.save(seat);}

    public Seats updateseat(Seats seat, Long seatId) {
        Optional<Seats> optionalSeats = seatRepository.findById(seatId);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Films not exist with id" + seatId);
        }
        return seatRepository.save(seat);
    }

    @Override
    public void deleteseat(Long seatId){
        Optional<Seats> optionalSeats = seatRepository.findById(seatId);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Seats not exist with id :" + seatId);
        }
        Seats seats = seatRepository.getReferenceById(seatId);
        this.seatRepository.delete(seats);

    }

    @Override
    public Seats getReferenceById(Long id) {
        return this.seatRepository.getReferenceById(id);
    }

    @Override
    public List<Seats> getSeatAvailable(Integer isAvailable) {
        List<Seats> optionalSeats = seatRepository.getSeatAvailable(isAvailable);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Seats not exist with id : " + isAvailable);
        }
        return this.seatRepository.getSeatAvailable(isAvailable);
    }

    @Override
    public List<Seats> getSeatAvailableNew(Integer isAvailable) {
        List<Seats> optionalSeats = seatRepository.getSeatAvailableNew(isAvailable);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Seats not exist with id : " + isAvailable);
        }
        return this.seatRepository.getSeatAvailableNew(isAvailable);
    }

//    tambahan

//    @Override
//    public Page<Seats> findPaginatedSeats(int pageNoSeat, int pageSizeSeat) {
//        Pageable pageable = PageRequest.of(pageNoSeat -1, pageSizeSeat);
//        return this.seatRepository.findAll(pageable);
//    }

}

