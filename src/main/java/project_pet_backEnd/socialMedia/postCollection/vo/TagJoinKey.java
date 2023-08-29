package project_pet_backEnd.socialMedia.postCollection.vo;

import java.io.Serializable;

public class TagJoinKey implements Serializable {
    int pcId;
    int pctId;

    public TagJoinKey() {
    }

    public TagJoinKey(int pcId, int pctId) {
        this.pcId = pcId;
        this.pctId = pctId;
    }
}
