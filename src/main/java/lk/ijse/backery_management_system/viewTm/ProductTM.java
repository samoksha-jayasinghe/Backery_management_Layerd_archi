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

public class ProductTM {
    private String productId;
    private String name;
    private int stocklevel;
    private int price;
    private String category;
    private int qty;
    private String inventory_id;
}
