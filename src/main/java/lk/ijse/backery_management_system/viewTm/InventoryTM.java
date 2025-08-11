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

public class InventoryTM {

    private String inventoryId;
    private String productId;
    private String supplierId;
    private String itemName;
    private int price;
    private int quantity;

}


