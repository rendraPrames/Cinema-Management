package com.infosys.timd.bioskopapi.Service;

import com.infosys.timd.bioskopapi.DTO.*;
import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;
import com.infosys.timd.bioskopapi.Response.*;
import com.infosys.timd.bioskopapi.Service.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface ScheduleService {

    List<Schedule> getAll();
    Optional<Schedule> getScheduleById(Integer Id);
    Schedule createSchedule(Schedule schedule);
    void deleteScheduleById(Integer Id);
    Schedule updateSchedule(Schedule schedule);
    Schedule getReferenceById (Integer id);
    List<Schedule> getScheduleByFilmName(String name);
    List<Schedule> getScheduleByFilmNameLike(String name);
    Page<Schedule> findPaginatedSchedule(int pageNo, int pageSize);

}

