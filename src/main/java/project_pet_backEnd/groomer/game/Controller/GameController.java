package project_pet_backEnd.groomer.game.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project_pet_backEnd.groomer.game.service.GameService;
import project_pet_backEnd.utils.commonDto.ResultResponse;

@Api(tags = "小遊戲功能")
@RestController
@Validated
public class GameController {
    @Autowired
    GameService gameService;

    @ApiOperation("小遊戲增加點數")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization_U", value = "User Access Token", required = true, dataType = "string", paramType = "header")
    })
    @GetMapping("/user/game")
    public ResultResponse<String> updateUserPoint(@RequestAttribute(name = "userId") Integer userId,@RequestParam Integer point){
        gameService.updateUserPoint(userId,point);

        ResultResponse<String> rs = new ResultResponse<>();
        rs.setMessage("點數增加"+point+"點!");
        return rs;
    }
}
