package service.impl;

import dao.CommentMapper;
import dao.HKPersonMapper;
import dao.OrderMapper;
import dao.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pojo.Comment;
import pojo.Customer;
import pojo.Order;
import service.CommentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: homeplus
 * @description: comment 接口类的实现类
 * @author: JSY
 * @create: 2019-08-26 18:02
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PersonMapper personMapper;

    /**
     * 获取评论信息列表 （这里就是进行显示列表，
     * 我已经写好了service业务层的逻辑，需要在controller层中调用，
     * 最后需要用ajax请求进行前端界面获取数据）
     * @param id 家政人员的编号
     * @return
     */
    @Override
    public List<Comment> getCommentListByID(int id) {
        List<Order> orderList = orderMapper.getOrderListByHKID(id);
        List<Comment> commentList = new ArrayList<>();
        for (Order order : orderList) {
            int orderID = order.getId();
            int cmID = order.getCmID();
            Customer customer = personMapper.selectCustomerByID(cmID);
            //如果没有订单号评价了，直接返回
            if (commentMapper.getCommentByOrderID(orderID) == null) {
                continue;
            } else {
                Comment comment = commentMapper.getCommentByOrderID(orderID);
                comment.setCommentPublicer(customer.getCmNickname());
                //将这条评论添加到数据库
                commentList.add(comment);
            }
        }
        return commentList;
    }

    //直接插入评价信息到数据库表
    @Override
    public Integer insertComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }
}
