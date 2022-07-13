package com.infosys.timd.bioskopapi.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponsePost {
    private Long book_id;
    private Integer sch_id;
    private Long usr_id;

    @Override
    public String toString() {
        return "BookingResponsePost{" +
                "book_id=" + book_id +
                ", sch_id=" + sch_id +
                ", usr_id=" + usr_id +
                '}';
    }
}
