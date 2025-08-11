package lk.ijse.backery_management_system.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
/*@Getter
@Setter
@ToString*/
@Data



public class EmployeeDto {
    private String employeeId;
    private String name;
    private String role;
    private String salary;
    private String contact;
}
