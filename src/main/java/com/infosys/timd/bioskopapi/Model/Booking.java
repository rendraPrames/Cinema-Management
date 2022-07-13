package com.infosys.timd.bioskopapi.Model;

import com.infosys.timd.bioskopapi.DTO.*;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public BookingResponseDTO convertToResponse(){
        return BookingResponseDTO.builder()
                .book_id(this.bookingId)
                .usr_id(this.getUser().getUserId())
                .usr_name(this.getUser().getUsername())
                .email_usr(this.getUser().getEmailId())
                .film_name(this.getSchedule().getFilms().getName())
                .price(this.getSchedule().getPrice())
                .studio(this.getSchedule().getSeats().getStudioName())
                .status_show(this.getSchedule().getFilms().getIsPlaying())
                .seat_num(this.getSchedule().getSeats().getSeatNumber())
                .status_seat(this.getSchedule().getSeats().getIsAvailable())
                .date_film(this.getSchedule().getDateShow())
                .start_film(this.getSchedule().getShowStart())
                .end_film(this.getSchedule().getShowEnd())
                .build();
    }

    public BookingResponsePost convertToResponsePost(){
        return BookingResponsePost.builder()
                .book_id(this.bookingId)
                .usr_id(this.getUser().getUserId())
                .sch_id(this.getSchedule().getScheduleId())
                .build();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", user=" + user +
                ", schedule=" + schedule +
                '}';
    }
}

