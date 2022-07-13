package com.infosys.timd.bioskopapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infosys.timd.bioskopapi.DTO.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "schedules")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Films films;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_show")
    private LocalDate dateShow;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seats seats;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "show_start")
    private LocalTime showStart;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "show_end")
    private LocalTime showEnd;

    @Column(name = "price")
    private Integer price;

    public ScheduleResponseDTO convertToResponse(){
        return ScheduleResponseDTO.builder()
                .scheduleId(this.scheduleId).films(this.films)
                .seats(this.seats).dateShow(this.dateShow)
                .showStart(this.showStart).showEnd(this.showEnd).price(this.price).build();
    }

    public ScheduleResponseFilmSeatDTO convertToResponseFilmsSeat(){
        return ScheduleResponseFilmSeatDTO.builder()
                .scheduleId(this.scheduleId)
                .filmId(this.films.getFilmId())
                .seatId(this.seats.getSeatId())
                .dateShow(this.dateShow)
                .showStart(this.showStart)
                .showEnd(this.showEnd)
                .price(this.price).build();

    }

    public ScheduleResponseNameLikeDTO convertToResponseNameLike(){
        return ScheduleResponseNameLikeDTO.builder()
                .filmName(this.getFilms().getName())
                .studioName(this.getSeats().getStudioName())
                .price(this.price)
                .build();
    }

    @Override
    public String toString() {
        return "\n Schedule{" +
                "scheduleId=" + scheduleId +
                ", films=" + films +
                ", dateShow=" + dateShow +
                ", seats=" + seats +
                ", showStart=" + showStart +
                ", showEnd=" + showEnd +
                ", price=" + price +
                '}';
    }
}


//bikin constructor kosong (bisa diganti dengan penggunaan anotasi @NoArgsConstructor)
//bikin constructor method untuk semua property (bisa diganti dengan penggunaan anotasi @AllArgsConstructor)
//bikin setter getter (bisa diganti dengan penggunaan anotasi @SetterGetter)


