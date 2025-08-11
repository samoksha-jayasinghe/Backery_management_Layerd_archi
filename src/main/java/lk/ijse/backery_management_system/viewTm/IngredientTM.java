package lk.ijse.backery_management_system.viewTm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class IngredientTM {
    private String itemId;
    private String productId;
    private String batchno;
    private String date;
    private int qty;
    private String ingredientName;

}
