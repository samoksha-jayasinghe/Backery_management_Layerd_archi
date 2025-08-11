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

public class UsersEntity {
    private String userId;
    private String name;
    private String address;
    private String email;
    private String contact;
    private int password;
}
