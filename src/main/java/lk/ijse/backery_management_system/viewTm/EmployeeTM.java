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

public class EmployeeTM {
    private String employeeId;
    private String name;
    private String role;
    private String salary;
    private String contact;
}
