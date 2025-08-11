package lk.ijse.backery_management_system.dto;

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

public class OrderDto {
    private String OrderId;
    private String CustomerId;
    private String Orderdate;
    private String Status;
    private int totalAmount;
    private String productId;

    public OrderDto(String string, String string1, LocalDate parse, String string2, int anInt) {
    }
}
