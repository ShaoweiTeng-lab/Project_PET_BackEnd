package project_pet_backEnd.productMall.lineNotify.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.productMall.lineNotify.service.LineNotifyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "後台商城管理員通知功能")
@Validated
@RestController
@RequestMapping("/manager")
public class ManagerNotifyController {
    @Autowired
    LineNotifyService lineNotifyService;

    @GetMapping("/sendToOauth")
    public void sendToOauth(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String url = lineNotifyService.getOAuthCode();
        response.sendRedirect(url);
    }
}
