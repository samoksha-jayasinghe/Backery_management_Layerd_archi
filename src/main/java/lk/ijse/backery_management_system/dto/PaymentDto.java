package lk.ijse.backery_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
/*@Getter
@Setter
@ToString*/
@Data

public class PaymentDto {
    private String paymentId;
    private String orderId;
    private String method;
    private String paymentDate;
    private int amount;
}
