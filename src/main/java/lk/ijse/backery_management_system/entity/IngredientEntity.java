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

public class IngredientEntity {
    private String itemId;
    private String productId;
    private String batchno;
    private String date;
    private int qty;
    private String ingredientName;
}
