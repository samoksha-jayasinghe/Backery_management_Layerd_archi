package lk.ijse.backery_management_system.viewTm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class ItemTM {

        private String itemId;
        private String name;
        private String category;
        private int price;
        private int quantity;
        private String expirDate;




       /* public ItemTM(String itemId, String name, String category, double price, int quantity, String expirDate) {
        }*/
}

