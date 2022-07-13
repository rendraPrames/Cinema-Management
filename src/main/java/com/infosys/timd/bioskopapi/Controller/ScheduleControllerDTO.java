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

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teamD/v1")
@AllArgsConstructor
public class ScheduleControllerDTO {

    private static final Logger logger = LogManager.getLogger(ScheduleControllerDTO.class);
    private static final String Line = "============================================================";

    private ScheduleService scheduleService;

    /**
     *Get all of data from schedules table
     */
    @GetMapping("/schedule")
    public ResponseEntity<Object> ScheduleList(){
        try {
            List<Schedule> result = scheduleService.getAll();
            List<ScheduleResponseDTO> scheduleMaps = new ArrayList<>();
            logger.info(Line + "Logger Start Find All Schedule" + Line);
            for (Schedule dataResult:result){
                Map<String,Object> schedule = new HashMap<>();
                logger.info("================================");
                schedule.put("Schedule id : ", dataResult.getScheduleId());
                schedule.put("Film : ", dataResult.getFilms());
                schedule.put("Seat : ", dataResult.getSeats());
                logger.info("Schedule id :"+dataResult.getScheduleId());
                logger.info("Film :"+dataResult.getFilms());
                logger.info("Seats :"+dataResult.getSeats());
                logger.info("================================");
                ScheduleResponseDTO scheduleDTO = dataResult.convertToResponse();
                scheduleMaps.add(scheduleDTO);
            }
            logger.info(Line + "Logger End Find All Schedule" + Line);
            return ResponseHandler.generateResponse("Successfully  getAll data!", HttpStatus.OK, scheduleMaps);
        }
        catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "Tabel has no Value");
        }
    }

    /**
     *create new schedule into schedules table
     * throws ResourceNotFoundException if bad request happened
     */
    @PostMapping("/schedule")
    public ResponseEntity<Object> createScheduleDTO(@RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        try {
            Schedule scheduleCreate = scheduleRequestDTO.convertToEntity();
            scheduleService.createSchedule(scheduleCreate);
            ScheduleResponseFilmSeatDTO result = scheduleCreate.convertToResponseFilmsSeat();
            logger.info(Line + " Logger Start Created New Schedule" + Line);
            logger.info(result);
            logger.info(Line + " Logger Stop Create New Schedule" + Line);
            return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.CREATED, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Bad Request!!");
        }
    }

    /**
     * update schedule
     * throws ResourceNotFoundException if data not found
     */
    @PutMapping("/schedule/{id}")
    public ResponseEntity<Object> updateScheduleDTO(@PathVariable Integer id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        try {
            Schedule schedule = scheduleRequestDTO.convertToEntity();
            schedule.setScheduleId(id);
            Schedule scheduleUpdate = scheduleService.updateSchedule(schedule);
            ScheduleResponseFilmSeatDTO result = scheduleUpdate.convertToResponseFilmsSeat();
            logger.info(Line + " Logger Start Updated Data" + Line);
            logger.info("Update : " + scheduleUpdate);
            logger.info(Line + " Logger Finish Updated Data" + Line);
            return ResponseHandler.generateResponse("Data Updated!", HttpStatus.CREATED, result);
        } catch (Exception e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request");
        }
    }
    /**
     * delete schedule by id
     * throws ResourceNotFoundException if data is not found
     */
    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Integer id) {
        try {
            scheduleService.deleteScheduleById(id);
            Boolean result = Boolean.TRUE;
            logger.info(Line + " Logger Start Delete Schedule " + Line);
            logger.info("Success Delete Schedule by ID :" + result);
            logger.info(Line + " Logger End Delete Schedule " + Line);
            return ResponseHandler.generateResponse("Success Delete Booking by ID", HttpStatus.OK, result);
        } catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!");
        }
    }
    /**
     *Get Schedule by Schedule id
     * throws ResourceNotFoundException if data is not found
     */
    @GetMapping("/schedule/{id}")
    public ResponseEntity<Object> getscheduleById(@PathVariable Integer id) {
        try {
            Optional<Schedule> schedule = scheduleService.getScheduleById(id);
            Schedule scheduleget = schedule.get();
            ScheduleResponseDTO result = scheduleget.convertToResponse();
            logger.info(Line + " Logger Start Find Schedule ById " + Line);
            logger.info("GetById");
            logger.info(result);
            logger.info(Line + " Logger End Find Schedule By Id");
            return ResponseHandler.generateResponse("Successfully retrivied data!", HttpStatus.OK, result);
        } catch (ResourceNotFoundException e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!"); }
    }

    /**
     * custom Challange 4 slide 8 nomor 2.2
     * Query Find colum Film,Studio Name, Price
     *throws ResourceNotFoundException if film name is not found
     */
    @PostMapping("/schedule/byfilmnameLike")
    public ResponseEntity<Object> findScheduleByFilmName(@RequestBody Films film) {
        try {
            List<Schedule> scheduleByFilmName = scheduleService.getScheduleByFilmNameLike(film.getName());
            List<ScheduleResponseNameLikeDTO> results = scheduleByFilmName.stream()
                    .map(Schedule::convertToResponseNameLike)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Create New Schedule" + Line);
            logger.info("Get Response :" + results);
            logger.info(Line + "Logger Finish Create New Schedule" + Line);
            return ResponseHandler.generateResponse("Success" ,HttpStatus.CREATED,results);
        } catch (Exception e) {
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST, "Bad Request");

        }

    }
}

