package com.infosys.timd.bioskopapi.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long book_id;
    private Long usr_id;
    private String usr_name;
    private String email_usr;
    private String film_name;
    private Integer price;
    private String studio;
    private Integer status_show;
    private Long seat_num;
    private Integer status_seat;
    private LocalDate date_film;
    private LocalTime start_film;
    private LocalTime end_film;

    @Override
    public String toString() {
        return "BookingResponseDTO{" +
                "book_id=" + book_id +
                ", usr_id=" + usr_id +
                ", usr_name='" + usr_name + '\'' +
                ", email_usr='" + email_usr + '\'' +
                ", film_name='" + film_name + '\'' +
                ", price=" + price +
                ", studio='" + studio + '\'' +
                ", status_show=" + status_show +
                ", seat_num=" + seat_num +
                ", status_seat=" + status_seat +
                ", date_film=" + date_film +
                ", start_film=" + start_film +
                ", end_film=" + end_film +
                '}';
    }
}

