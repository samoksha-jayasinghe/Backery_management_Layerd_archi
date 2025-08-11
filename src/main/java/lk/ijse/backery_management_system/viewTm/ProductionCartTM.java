package lk.ijse.backery_management_system.viewTm;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class ProductionCartTM {
    private String productId;
    private String name;
    private int stockLevel;
    private int price;
    private String category;
    private int qty;
    private String inventory_id;
}
