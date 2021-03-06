package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Message;
import pojo.ResponseResult;
import service.MessageService;
import service.UserService;
import service.exception.UserNoLoginException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: homeplus
 * @description: Message 的控制类
 * @author: JSY
 * @create: 2019-08-29 19:31
 **/
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;


    /***
     * 获取消息通过session
     * @param session  用户的ID
     * @return
     */
    @RequestMapping("/getMessageByID")
    @ResponseBody
    public ResponseResult<List<Message>> getMessageByID (
            HttpSession session
    ) {
        ResponseResult<List<Message>> result = new ResponseResult<>();
        List<Message> list = messageService.getMessageByToID(session);
        result.setData(list);
        return result;
    }


    /**
     * 插入该条信息(雇主对保姆)
     * @param session
     * @param toID
     * @param msgContent
     * @return
     */
    @RequestMapping("/insertMessage")
    @ResponseBody
    public ResponseResult<Void> insertMessage (
            HttpSession session,
            @RequestParam("id") int toID,
            @RequestParam("msgContent") String msgContent
    ) {
        messageService.insertMessage(msgContent, session, toID);
        return new ResponseResult<>();
    }


    /**
     * 插入一条信息（保姆对雇主）
     */
    @RequestMapping("/insertMessageCM")
    @ResponseBody
    public ResponseResult<Void> insertMessageCustomer(HttpSession session,
                                                      @RequestParam("id")int toID,
                                                      @RequestParam("msgContent")String msgContent){
        messageService.insertMessageBYCustomer(msgContent, session, toID);

       return new ResponseResult<>();
     }

}
