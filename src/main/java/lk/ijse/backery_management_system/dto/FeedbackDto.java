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

public class FeedbackDto {
    private String feedbackId;
    private String customerId;
    private String orderId;
    private int rating;
    private String comment;
}
