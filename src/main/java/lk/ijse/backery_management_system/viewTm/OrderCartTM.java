package lk.ijse.backery_management_system.viewTm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderCartTM {
    private String itemId;
    private String customerId;
    private String name;
    private int cartQty;
    private double unitPrice;
    private double total;
    private Button remove;
}
