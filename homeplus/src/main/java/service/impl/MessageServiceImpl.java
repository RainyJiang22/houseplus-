package service.impl;

import dao.HKPersonMapper;
import dao.MessageMapper;
import dao.PersonMapper;
import dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pojo.Message;
import service.MessageService;
import service.exception.NoMessagePermission;
import service.exception.UserNoLoginException;
import utils.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @program: homeplus
 * @description: MessageService的实现类
 * @author: JSY
 * @create: 2019-08-27 20:03
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HKPersonMapper hkPersonMapper;
    @Autowired
    private PersonMapper personMapper;

    /**
     * unreadStatus 表示未读状态
     */
    private final int unreadStatus = 0;
    /**
     * readStatus 表示已读
     */
    private final int readStatus = 1;

    /**
     * 获取当前用户下获得的信息
     * @param session 通过用户存放在session中的手机号获得编号
     * @return
     */
    @Override
    public List<Message> getMessageByToID(HttpSession session) {
        String phone = (String)session.getAttribute("username");
        if (StringUtils.isEmpty(phone)) {
            throw new UserNoLoginException("用户未登录!");
        } else {
            int id = userMapper.selectHKIDByPhone(phone);
            return messageMapper.getMessageByToID(id);
        }
    }

    @Override
    public Integer insertMessage(String msgContent, HttpSession session, int toID) throws UserNoLoginException, NoMessagePermission {
        //传递参数问题
      //当前登录的手机号
        String phone = (String)session.getAttribute("username");
        //需要发送的手机号(保姆)
        String toPhone = hkPersonMapper.getHousekeeperByID(toID).getHkPhone();
        if (StringUtils.isEmpty(phone)) {
            throw new UserNoLoginException("用户未登录！");
            //如果用户名手机号相等，则判断不能给自己留言
        } else if (phone.equals(toPhone) ) {
            throw new NoMessagePermission("不能给自己留言！");
        } else {
            Message message = new Message();
            int fromID = userMapper.selectCmIDByPhone(phone);
            Date now = new Date();
            message.setContent(msgContent);
            message.setCreatedTime(now);
            message.setFromID(fromID);
            message.setToID(toID);
            message.setMessageStatus(unreadStatus);
            return  messageMapper.insertMessage(message);
        }
    }

    /*
     发送给雇主的ID
     */
    @Override
    public Integer insertMessageBYCustomer(String msgContent, HttpSession session, int toID) throws UserNoLoginException, NoMessagePermission {
        //获取手机号
        String phone = (String) session.getAttribute("username");
        //获取需要发送的手机号（保姆发送给雇主）
       String toPhone = personMapper.getCustomerByID(toID).getCmPhone();
        if (StringUtils.isEmpty(phone)){
            throw new UserNoLoginException("用户未登录！");
            //如果用户名手机号相等，则判断不能给自己留言
        }else if(phone.equals(toPhone)){
            throw new NoMessagePermission("不能给自己留言");
        }else{
            Message message = new Message();
            Date date = new Date();
            int fromID = userMapper.selectHKIDByPhone(phone);
            message.setContent(msgContent);
            message.setCreatedTime(date);
            message.setFromID(fromID);
            message.setToID(toID);
            message.setMessageStatus(unreadStatus);
            return messageMapper.insertMessage(message);
        }
    }
}
