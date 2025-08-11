package lk.ijse.backery_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class OrderdetailsDto {
    private String orderid;
    private String productId;
    private int qty;
}
