package com.infosys.timd.bioskopapi.DTO;

import com.infosys.timd.bioskopapi.Model.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequestDTO {

    private Integer scheduleId;
    private Films films;
    private Seats seats;
    private LocalDate dateShow;
    private LocalTime showStart;
    private LocalTime showEnd;
    private Integer price;

    public Schedule convertToEntity(){
        return Schedule.builder().scheduleId(this.scheduleId).films(this.films).seats(this.seats)
                .dateShow(this.dateShow).showStart(this.showStart)
                .showEnd(this.showEnd).price(this.price)
                .build();
    }
}
