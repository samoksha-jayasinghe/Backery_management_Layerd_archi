package lk.ijse.backery_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class CustomerDto {
    private String customerId;
    private String firstName;
    private String address;
    private String email;
    private String contact;
    private String userID;


    public CustomerDto(String customerId, String name, String number, String address, String orderPlatform) {
    }
}

