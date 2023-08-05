package project_pet_backEnd.productMall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class ProductMallController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //查詢資料庫有沒有訂單 萬能的Map
    @GetMapping("/ordersList")
    public List<Map<String, Object>> ordersList(){
        String sql = "SELECT * FROM manager";
        List<Map<String, Object>> listMaps = jdbcTemplate.queryForList(sql);
        return listMaps;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
