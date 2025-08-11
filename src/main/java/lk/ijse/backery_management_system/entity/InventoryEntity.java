package lk.ijse.backery_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
/*@Getter
@Setter
@ToString*/
@Data

public class InventoryEntity {
    private String inventoryId;
    private String productId;
    private String supplierId;
    private String itemName;
    private int price;
    private int quantity;

}
