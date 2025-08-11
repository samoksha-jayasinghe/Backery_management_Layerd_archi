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

public class FeedbackEntity {
    private String feedbackId;
    private String customerId;
    private String orderId;
    private int rating;
    private String comment;
}