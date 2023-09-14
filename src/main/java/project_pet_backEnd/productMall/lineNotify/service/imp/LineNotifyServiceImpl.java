package project_pet_backEnd.productMall.lineNotify.service.imp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import project_pet_backEnd.productMall.lineNotify.dto.LineNotifyResponse;
import project_pet_backEnd.productMall.lineNotify.dto.LineOAuthRequest;
import project_pet_backEnd.productMall.lineNotify.service.LineNotifyService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
public class LineNotifyServiceImpl implements LineNotifyService {

    @Value("${line.oauth.authorize.url}")
    private String LINE_OAUTH_AUTHORIZE_URL;

    @Value("${line.client.id}")
    private String LINE_CLIENT_ID;

    @Value("${line.redirect.uri}")
    private String LINE_REDIRECT_URI;

    //設定表頭參數
    private final String LINE_NOTIFY_API_URL = "https://notify-api.line.me/api/notify";
    @Value("${line.notify.token}")
    private String LINE_NOTIFY_TOKEN;


    @Override
    public String getOAuthCode() {

        // 創建請求參數
        LineOAuthRequest oauthRequest = new LineOAuthRequest();
        oauthRequest.setClient_id(LINE_CLIENT_ID);
        oauthRequest.setRedirect_uri(LINE_REDIRECT_URI);
        System.out.println(LINE_REDIRECT_URI);
        // 建立請求實體
        HttpEntity<LineOAuthRequest> requestEntity = new HttpEntity<>(oauthRequest);
        RestTemplate restTemplate = new RestTemplate();
        // 發請求GET
        ResponseEntity<String> response = restTemplate.exchange(
                LINE_OAUTH_AUTHORIZE_URL,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        // 處理回應
        if (response.getStatusCode().is2xxSuccessful()) {
            // 成功獲取OAuth
            String responseBody = response.getBody();
            // 處理回傳的OAuth
            // 注意：Line Notify的OAuth2授權在瀏覽器中執行，登陸並授權後，重定向到你的重定向URI，并在URL參數中包含回傳的code。
            // 重新定向url
            return parseAuthorizationCodeFromResponseBody(responseBody);
        } else {
            throw new RuntimeException("Failed to obtain OAuth authorization code.");
        }
    }

    // response解析代碼
    private String parseAuthorizationCodeFromResponseBody(String responseBody) {
        // 根據 Line Notify 授權響應的具體格式來解析授權碼
        // 授權碼會包含在回傳的參數中，我需要提取該參數的值 看api文件
        // 返回授權碼
        try {
            // 解析 URI
            URI uri = new URI(responseBody);

            // 使用 Spring 的 UriComponentsBuilder 來獲取查詢參數
            UriComponents uriComponents = UriComponentsBuilder.fromUri(uri).build();
            MultiValueMap<String, String> queryParams = uriComponents.getQueryParams();

            // 獲取名為 "code" 的查詢參數的值
            List<String> codeValues = queryParams.get("code");
            if (codeValues != null && !codeValues.isEmpty()) {
                // 返回授權碼
                return codeValues.get(0);
            } else {
                // 如果在響應中找不到授權碼，可以拋出異常或返回 null，視情況而定
                throw new RuntimeException("在響應中找不到授權碼。");
            }
        } catch (URISyntaxException e) {
            // 處理 URI 解析錯誤
            throw new RuntimeException("解析授權響應 URI 失敗。", e);
        }
    }

    public String getOAuthCodeTest() {
        StringBuilder sb = new StringBuilder();
        LineOAuthRequest lineOAuthRequest = new LineOAuthRequest();
        lineOAuthRequest.setClient_id("client_id");
        lineOAuthRequest.setRedirect_uri("http://localhost:5500/frontend/pages/mall/mall/mall.html");

        Map<String,Object> variableParams = lineOAuthRequest.getUriParams();

        //組裝GetOAuth URL字串
        sb.append("https://notify-bot.line.me/oauth/authorize").append("?");
        for(String key:variableParams.keySet()) {
              sb.append(key)
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
