package lk.ijse.backery_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ProductDto {
    private String productId;
    private String name;
    private int stocklevel;
    private int price;
    private String category;
    private int qty;
    private String inventory_id;

    public ProductDto(String productionId, String inventoryId, String employeeId) {
    }
}
