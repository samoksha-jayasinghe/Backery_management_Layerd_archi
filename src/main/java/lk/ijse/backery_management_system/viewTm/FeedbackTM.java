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

public class FeedbackTM {
    private String FeedbackId;
    private String CustomerId;
    private String OrderId;
    private int Rating;
    private String Comment;

}
