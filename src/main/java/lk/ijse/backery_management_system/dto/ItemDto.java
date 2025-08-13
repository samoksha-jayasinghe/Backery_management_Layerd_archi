package lk.ijse.backery_management_system.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString


public class ItemDto {
    private String itemId;
    private String name;
    private String category;
    private int price;
    private int quantity;

    public ItemDto(String itemId, String name, String category, int price, int quantity, String expirDate) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.expirDate = expirDate;
    }

    private String expirDate;



}
