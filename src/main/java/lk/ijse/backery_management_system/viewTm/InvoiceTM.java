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

public class InvoiceTM {

    private String invoiceid;
    private String orderid;
    private String dataIssue;
    private int totalAmount;
}


