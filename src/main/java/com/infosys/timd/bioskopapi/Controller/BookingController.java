package com.infosys.timd.bioskopapi.Controller;

import com.infosys.timd.bioskopapi.DTO.*;
import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;
import com.infosys.timd.bioskopapi.Response.*;
import com.infosys.timd.bioskopapi.Service.*;

import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teamD/v1")
@AllArgsConstructor
public class BookingController {

    private static final Logger logger = LogManager.getLogger(BookingController.class);
    private static final String Line = "====================";
    private BookingService bookingService;
    private ScheduleService scheduleService;
    private SeatsService seatsService;


    /**
     *Get All booking from booking table
     * throws ResourceNotFoundException if not found happened
     */
    @GetMapping("/booking")
    public ResponseEntity<Object> findAll(){
        try {
            List<Booking> bookings = bookingService.getAll();
            List<BookingResponseDTO> bookingmaps = new ArrayList<>();
            logger.info(Line + " Logger Start Find All Booking " + Line);
            for (Booking dataresults:bookings){
                logger.info("Booking id : "+dataresults.getBookingId()+
                        ", Title Film : "+dataresults.getSchedule().getFilms().getName()+
                        ", Status Playing : "+dataresults.getSchedule().getFilms().getIsPlaying()+
                        ", Studio : "+dataresults.getSchedule().getSeats().getStudioName()+
                        ", Seat Number : "+dataresults.getSchedule().getSeats().getSeatNumber()+
                        ", Status Seat : "+dataresults.getSchedule().getSeats().getIsAvailable()+
                        ", Price : "+dataresults.getSchedule().getPrice()+
                        ", Date Show : "+dataresults.getSchedule().getDateShow()+
                        ", Show Start : "+dataresults.getSchedule().getShowStart()+
                        ", Show End : "+dataresults.getSchedule().getShowEnd());
                BookingResponseDTO bookDTO = dataresults.convertToResponse();
                bookingmaps.add(bookDTO);
            }
            logger.info(Line + " Logger End Find All Booking " + Line);
            return ResponseHandler.generateResponse("Success Get All",HttpStatus.OK,bookingmaps);
        }catch(ResourceNotFoundException e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        }
    }

    /**
     *Get Booking by Booking id
     * throws ResourceNotFoundException if data is not found
     */
    @GetMapping("/booking/{id}")
    public ResponseEntity<Object> getBookingById(@PathVariable Long id){
        try {
            Optional<Booking> booking = bookingService.getBookingById(id);
            Booking bookingget = booking.get();
            BookingResponseDTO result = bookingget.convertToResponse();
            logger.info(Line + " Logger Start Get By id Booking " + Line);
            logger.info("Update Booking : " + result);
            logger.info(Line + " Logger End Get By id Booking " + Line);
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    /**
     * update booking
     * throws ResourceNotFoundException if data not found
     */
    @PutMapping("/booking/{id}")
    public ResponseEntity<Object> bookingupdate(@PathVariable Long id, @RequestBody BookingRequestDTO bookingRequestDTO){
        try {
            if(bookingRequestDTO.getSch() == null || bookingRequestDTO.getUsr() == null){
                throw new ResourceNotFoundException("Booking must have schedule id and user id");
            }
            Booking booking = bookingRequestDTO.covertToEntitiy();
            booking.setBookingId(id);
            Booking bookingUpdate = bookingService.updateBooking(booking);
            BookingResponseDTO results = bookingUpdate.convertToResponse();
            logger.info(Line + " Logger Start Update Booking " + Line);
            logger.info("Update Booking : " + bookingUpdate);
            logger.info(Line + " Logger End Update Booking " + Line);
            return ResponseHandler.generateResponse("Success Update Booking",HttpStatus.CREATED,results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }
    /**
     *create new booking into booking table
     * throws ResourceNotFoundException if bad request happened
     */
    @PostMapping("/booking")
    public ResponseEntity<Object> bookingCreate(@RequestBody BookingRequestDTO bookingRequestDTO){
        try{
            if(bookingRequestDTO.getSch() == null || bookingRequestDTO.getUsr() == null){
                throw new ResourceNotFoundException("Booking must have schedule id and user id");
            }
            Booking booking = bookingRequestDTO.covertToEntitiy();
            bookingService.createBooking(booking);
            BookingResponsePost result = booking.convertToResponsePost();
            logger.info(Line + " Logger Start Create Booking " + Line);
            logger.info("Create Booking : " + result);
            logger.info(Line + " Logger End Create Booking " + Line);
            return ResponseHandler.generateResponse("Success Create Booking",HttpStatus.CREATED,result);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Database");
        }
    }

    /**
     * delete schedule by id
     * throws ResourceNotFoundException if data is not found
     */
    @DeleteMapping("/booking/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Long id){
        try {
            bookingService.deleteSBookingById(id);
            Boolean result = Boolean.TRUE;
            logger.info(Line + " Logger Start Delete Booking " + Line);
            logger.info("Success Delete Booking by ID :"+result);
            logger.info(Line + " Logger End Delete Booking " + Line);
            return ResponseHandler.generateResponse("Success Delete Booking by ID",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    /**
     * custom Query Booking by filmname
     * Query Find by Filmnme
     *throws ResourceNotFoundException if film name is not found
     */
    @PostMapping("/booking/Filmname")
    public ResponseEntity<Object> findBookingBySchdeuleFilmName(@RequestBody Films films){
        try {
            List<Booking> bookingByScheduleFilmsname = bookingService.getBookingByFilmName(films.getName());
            List<BookingResponseDTO> bookingResponseDTOS = bookingByScheduleFilmsname.stream()
                    .map(Booking::convertToResponse)
                    .collect(Collectors.toList());
            logger.info(Line+" Logger Start Query By Film Name Booking "+Line);
            logger.info("Success Query By Filmname : " +bookingResponseDTOS);
            logger.info(Line+" Logger End Query By Film Name Booking "+Line);
            return ResponseHandler.generateResponse("Succes Query By Filmname",HttpStatus.OK,bookingResponseDTOS);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Query By Filename");
        }

    }


}






