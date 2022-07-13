package com.infosys.timd.bioskopapi.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponseNameLikeDTO {
    private String filmName;
    private String studioName;
    private Integer price;

//    @Override
//    public String toString() {
//        return "ScheduleResponseNameLikeDTO{" +
//                "filmName='" + filmName + '\'' +
//                ", studioName='" + studioName + '\'' +
//                ", price=" + price +
//                '}';
//    }
}
