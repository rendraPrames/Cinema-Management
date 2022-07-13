package com.infosys.timd.bioskopapi.DTO;

import com.infosys.timd.bioskopapi.DTO.*;
import com.infosys.timd.bioskopapi.Model.*;
import com.infosys.timd.bioskopapi.Exception.*;
import com.infosys.timd.bioskopapi.Response.*;
import com.infosys.timd.bioskopapi.Service.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequestDTO {
    private Long id;
    private User usr;
    private Schedule sch;

    public Booking covertToEntitiy(){
        return Booking.builder()
                .bookingId(this.id)
                .user(this.usr)
                .schedule(this.sch)
                .build();
    }
}

