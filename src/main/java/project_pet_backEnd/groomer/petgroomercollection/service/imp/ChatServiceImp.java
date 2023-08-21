package project_pet_backEnd.groomer.petgroomercollection.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project_pet_backEnd.groomer.petgroomer.dto.PGQueryParameter;
import project_pet_backEnd.groomer.petgroomer.dto.response.ChatRes;
import project_pet_backEnd.groomer.petgroomercollection.dao.ChatDao;
import project_pet_backEnd.groomer.petgroomercollection.service.ChatService;
import project_pet_backEnd.groomer.petgroomercollection.vo.Chat;
import project_pet_backEnd.utils.AllDogCatUtils;
import project_pet_backEnd.utils.commonDto.Page;
import project_pet_backEnd.utils.commonDto.ResultResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImp implements ChatService {

    @Autowired
    ChatDao dao;

    /**
     * 新增
     * @param rest
     * @return
     */
    @Override
    public ResultResponse insert(Chat rest) {
        try {
            dao.insert(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }

    /**
     * 更新
     * @param rest
     * @return
     */
    @Override
    public ResultResponse update(Chat rest) {
        try {
            dao.update(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }

    /**
     * 删除
     * @param rest
     * @return
     */
    @Override
    public ResultResponse delete(Chat rest) {
        try {
            dao.delete(rest);
            ResultResponse rs = new ResultResponse();
            rs.setMessage("操作成功");
            return rs;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失敗，請稍後重試", e);
        }
    }


    /**
     * 列表
     * @param PGQueryParameter
     * @return
     */
    @Override
    public Page<List<ChatRes>> list(PGQueryParameter PGQueryParameter) {
        List<Chat> list = dao.list(PGQueryParameter);
        List<ChatRes> rsList = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "找不到寵物美容師");
        }
        for (Chat rest : list) {
            ChatRes res = new ChatRes();
            res.setChatNo(rest.getChatNo());
            res.setPgId(rest.getPgId());
            res.setUserId(rest.getUserId());
            res.setChatText(rest.getChatText());
            res.setChatStatus(rest.getChatStatus());
            res.setChatCreated(AllDogCatUtils.timestampToSqlDateFormat(rest.getChatCreated()));
            rsList.add(res);
        }
        Page page = new Page<>();
        page.setLimit(PGQueryParameter.getLimit());
        page.setOffset(PGQueryParameter.getOffset());
        //得到總筆數，方便實作頁數
        Integer total = dao.count(PGQueryParameter);
        page.setTotal(total);
        page.setRs(rsList);
        return page;
    }
}
