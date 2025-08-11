package lk.ijse.backery_management_system.viewTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
/*@Getter
@Setter
@ToString*/
@Data

public class OrderTM {
    private String OrderId;
    private String CustomerId;
    private String OrderDate;
    private String Status;
    private int totalAmount;
}
