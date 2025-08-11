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

public class UserTm {
    private String userId;
    private String name;
    private String address;
    private String email;
    private String contact;
    private int password;
}
