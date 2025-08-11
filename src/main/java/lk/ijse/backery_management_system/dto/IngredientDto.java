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

public class IngredientDto {
    private String itemId;
    private String productId;
    private String batchno;
    private String date;
    private int qty;
    private String ingredientName;

    public IngredientDto(String inventoryId, String productId, String supplierId, int rawMaterial, int qty) {
    }

    public IngredientDto(String itemId, String productId, String batchNo, String expireDate, String quantity, String ingredientName) {
    }
}
