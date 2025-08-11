package lk.ijse.backery_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
/*@Getter
@Setter
@ToString*/
@Data

public class UsersDto {
    private String userId;
    private String name;
    private String address;
    private String email;
    private String contact;
    private int password;

    public UsersDto(String userId, String inputUsername, String inputAddress, String inputEmail, String inputContact, String inputPassword) {
    }
}
