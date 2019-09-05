package service;

import pojo.Appointment;
import service.exception.AppointFailedException;
import service.exception.UserNoLoginException;
import service.exception.UserNotExistException;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AppointmentService {


    /**
     * 考虑未登录异常和预约失败的异常
     * @param appointment   预约实体类表
     * @param username  用户名
     * @return
     * @throws AppointFailedException
     * @throws UserNoLoginException
     */
    public Integer insertAppointment(Appointment appointment, String username) throws AppointFailedException, UserNoLoginException;


    /**
     * 进入保姆详细页可以直接进行预约
     */
    Integer insertHKAppointment(Appointment appointment,String username) throws AppointFailedException, UserNotExistException;

    /**
     * 获得所有有效的订单
     * @return 订单列表
     */
    List<Appointment> getStatusApp (HttpSession session, int status) throws UserNoLoginException;

    /**
     * 消费者获得自己的预约
     * @param session
     * @return
     */
    List<Appointment> getAllAppCustomer (HttpSession session);

    /**
     * 家政人员获得所有的预约
     * @return
     */
    List<Appointment> getAllApp (HttpSession session);

    /**
     * 申请的时候将家政人员的编号插入到申请列表中
     * @param id 订单编号
     * @return 受影响行数
     */
    Integer insertApplyList (HttpSession session, int id) throws UserNoLoginException;

    /**
     * 通过预约的编号获得单条预约的信息
     * @param id 预约的编号
     * @return 单条预约的信息
     */
    Appointment selectAppointByID (int id) throws UserNoLoginException ;

    /**
     * 通过预约的编号来更新预约的状态
     * @param status 预约的状态
     * @param id 预约的编号
     * @return 受影响的行数
     */
    Integer updateAppointStatus (int status, int id);

    /**
     * 通过预约的编号上删除预约
     * @param id 预约的编号
     * @return 受影响的行数
     */
    Integer deleteAppointByID (int id);

    /**
     * 确认最后的家政人员的编号
     * @param id 订单的编号
     * @param hkID 家政人员的编号
     * @return 受影响的行数
     */
    Integer insertConfirmHKID (String username, int id, int hkID);
}
