package lk.ijse.backery_management_system.entity;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
/*@Getter
@Setter
@ToString*/
@Data



public class EmployeeEntity {
    private String employeeId;
    private String name;
    private String role;
    private String salary;
    private String contact;
}