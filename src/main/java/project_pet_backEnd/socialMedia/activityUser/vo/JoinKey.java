package project_pet_backEnd.socialMedia.activityUser.vo;

import java.io.Serializable;

public class JoinKey implements Serializable {
    private int activityId;
    private int userId;

    public JoinKey() {
    }

    public JoinKey(int activityId, int userId) {
        this.activityId = activityId;
        this.userId = userId;
    }

}
