package lk.ijse.backery_management_system.viewTm;


import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTM {
    private String customerId;
    private String firstName;
    private String address;
    private String email;
    private String contact;
    private String userID;

   /* public CustomerTM(String customerId, String firstName, String lastName, String address, String email, String customerDtoEmail) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }*/
}
