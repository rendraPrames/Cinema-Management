package com.infosys.timd.bioskopapi.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ScheduleResponseDTO {

    private Integer scheduleId;
    private Films films;
    private Seats seats;

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateShow;

//    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime showStart;

//    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime showEnd;

    private Integer price;

    @Override
    public String toString() {
        return "\n ScheduleResponseDTO{" +
                "scheduleId=" + scheduleId +
                ", films=" + films +
                ", seats=" + seats +
                ", dateShow=" + dateShow +
                ", showStart=" + showStart +
                ", showEnd=" + showEnd +
                ", price=" + price +
                '}';
    }

    //    private LocalDateTime updatedAt;
//    private LocalDateTime createdAt;

}
