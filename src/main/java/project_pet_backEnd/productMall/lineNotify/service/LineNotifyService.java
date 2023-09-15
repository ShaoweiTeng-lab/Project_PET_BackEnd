package project_pet_backEnd.productMall.lineNotify.service;

import project_pet_backEnd.productMall.lineNotify.dto.LineNotifyResponse;

public interface LineNotifyService {
    public abstract String getOAuthCode();

    public abstract void notify(LineNotifyResponse lineNotifyResponse);
}
