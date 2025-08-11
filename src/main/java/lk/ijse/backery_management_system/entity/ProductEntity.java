package lk.ijse.backery_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductEntity {
    private String productId;
    private String name;
    private int stocklevel;
    private int price;
    private String category;
    private int qty;
    private String inventory_id;
}
