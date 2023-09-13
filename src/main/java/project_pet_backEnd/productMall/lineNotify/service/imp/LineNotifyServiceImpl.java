package project_pet_backEnd.productMall.lineNotify.service.imp;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import project_pet_backEnd.productMall.lineNotify.dto.LineNotifyResponse;
import project_pet_backEnd.productMall.lineNotify.dto.LineOAuthRequest;
import project_pet_backEnd.productMall.lineNotify.service.LineNotifyService;

import java.util.Map;

@Service
public class LineNotifyServiceImpl implements LineNotifyService {

    //設定表頭參數
    private final String LINE_NOTIFY_API_URL = "https://notify-api.line.me/api/notify";
    private final String LINE_NOTIFY_TOKEN = "BPN1oT9rCEKJLBLW7HdYCHiZUgyYTWUiiK6QcK6rwXk"; // Line Notify 令牌
    @Override
    public String getOAuthCode() {
        StringBuilder sb = new StringBuilder();
        LineOAuthRequest lineOAuthRequest = new LineOAuthRequest();
        lineOAuthRequest.setClient_id("csOQbpYbnaYhfrESUxRdmx");
        lineOAuthRequest.setRedirect_uri("http://localhost:5500/frontend/pages/mall/mall/mall.html");

        Map<String,Object> variableParams = lineOAuthRequest.getUriParams();

        //組裝GetOAuth URL字串
        sb.append("https://notify-bot.line.me/oauth/authorize").append("?");
        for(String key:variableParams.keySet()) {                                                          sb.append(key)
                .append("=")
                .append(String.valueOf(variableParams.get(key)))
                .append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();

    }

    @Override
    public void notify(LineNotifyResponse lineNotifyResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + LINE_NOTIFY_TOKEN);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 設置body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("message", lineNotifyResponse.getMessage());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                LINE_NOTIFY_API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 檢查回應狀態
        if (response.getStatusCode() == HttpStatus.OK) {
            // 通知發送成功
            System.out.println("LineNotify 發送成功");
        } else {
            // 通知發送失败
            System.err.println("LineNotify 發送失敗：" + response.getStatusCode());
        }
    }
}
