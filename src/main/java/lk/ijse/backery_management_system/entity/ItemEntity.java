package lk.ijse.backery_management_system.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class ItemEntity {
    private String itemId;
    private String name;
    private String category;
    private int price;
    private int quantity;
    private String expirDate;


}
