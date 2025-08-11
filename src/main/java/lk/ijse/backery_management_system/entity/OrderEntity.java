package lk.ijse.backery_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
/*@Getter
@Setter
@ToString*/
@Data

public class OrderEntity {
    private String OrderId;
    private String CustomerId;
    private String OrderDate;
    private String Status;
    private int totalAmount;
    private String productId;


}
