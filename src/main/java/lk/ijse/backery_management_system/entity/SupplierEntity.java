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

public class SupplierEntity {
    private String supplierId;
    private String name;
    private String contact;
    private String address;
}
