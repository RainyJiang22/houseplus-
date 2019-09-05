package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Appointment;
import pojo.ResponseResult;
import service.AppointmentService;
import service.exception.UserNoLoginException;
import utils.FormatDate;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @program: homeplus
 * @description: 预约操作的控制层
 * @author: JSY
 * @create: 2019-08-29 14:48
 **/

@Controller()
@RequestMapping("/app")
public class AppointmentController extends BaseController {

    @Autowired
    private AppointmentService appService;

    /**
     * 进行预约，提交预约资料
     * @param session
     * @param appType
     * @param appAddressCity
     * @param appAddressArea
     * @param appAddressDetail
     * @param appPhone
     * @param appTime
     * @return
     */
    @RequestMapping("/mkApp")
    @ResponseBody
    public ResponseResult<Void> makeAppoint(
            HttpSession session,
            @RequestParam("app_type") String appType,
            @RequestParam("app_address_city") String appAddressCity,
            @RequestParam("app_address_area") String appAddressArea,
            @RequestParam("app_address_detail") String appAddressDetail,
            @RequestParam("app_phone") String appPhone,
            @RequestParam("app_time") String appTime
    ) {
        ResponseResult<Void> response = new ResponseResult<>();
        String username = new String();
        String key = "username";
        if (session.getAttribute(key) == null) {
            response.setState(406);
            response.setMessage("用户未登录");
        } else {
            username = session.getAttribute(key).toString();
        }
        String appAddress = appAddressCity + "-" + appAddressArea + "-" + appAddressDetail;
        Appointment appointment = new Appointment();
        appointment.setAppAddress(appAddress);
        appointment.setAppType(appType);
        appointment.setAppPhone(appPhone);
        appointment.setAppBeginTime(FormatDate.StrToDate(appTime));

        System.out.println("预约地点:" + appAddress + "预约类型:" + appType + "预约电话:" + appPhone);

        appService.insertAppointment(appointment, username);

        return new ResponseResult<Void>();
    }


    /**
     * 通过session获得还未完成的预约信息
     * @param session
     * @return
     */
    @RequestMapping("/getVaildApp")
    @ResponseBody
    public ResponseResult<List<Appointment>> getVaildApp (
            HttpSession session
    ) {
        ResponseResult<List<Appointment>> result = new ResponseResult<>();
        List<Appointment> list = appService.getStatusApp(session, 1);
        result.setData(list);
        return result;
    }

    /**
     * 获得所有的预约信息
     * @param session
     * @return
     */
    @RequestMapping("/getAllApp")
    @ResponseBody
    public ResponseResult<List<Appointment>> getAllApp (
            HttpSession session
    ) {
        ResponseResult<List<Appointment>> result = new ResponseResult<>();
        List<Appointment> list = appService.getAllApp(session);
        result.setData(list);
        return result;
    }


    /**
     * 获取所有预约的消费者
     * @param session
     * @return
     */
    @RequestMapping("/getAppCustomer")
    @ResponseBody
    public ResponseResult<List<Appointment>> getAppCustomer (
            HttpSession session
    ) {
        ResponseResult<List<Appointment>> result = new ResponseResult<>();
        List<Appointment> list = appService.getAllAppCustomer(session);
        result.setData(list);
        return result;
    }


    /**
     * 保姆进行申请
     * @param session  返回的seesion
     * @param id  保姆申请的ID号
     * @return
     */
    @RequestMapping("/apply")
    @ResponseBody
    public ResponseResult<Void> apply (
            HttpSession session,
            Integer id
    ) {
        appService.insertApplyList(session, id);
        return new ResponseResult<Void>();
    }

    @RequestMapping("/getSingleApp")
    @ResponseBody
    public ResponseResult<Appointment> getSingleApp (
            @RequestParam("id") Integer id
    ) {
        ResponseResult<Appointment> result = new ResponseResult<>();
        Appointment app = appService.selectAppointByID(id);
        result.setData(app);
        return result;
    }

    @RequestMapping("/updateAppStatus")
    @ResponseBody
    public ResponseResult<Void> updateAppStatus (
            @RequestParam("id") Integer id
    ) {
        appService.updateAppointStatus(0, id);
        return new ResponseResult<Void>();
    }

    @RequestMapping("/deleteApp")
    @ResponseBody
    public ResponseResult<Void> deleteAppointByID (
            @RequestParam("id") Integer id
    ) {
        appService.deleteAppointByID(id);
        return new ResponseResult<Void>();
    }

    @RequestMapping("/confirmHK")
    @ResponseBody
    public ResponseResult<Void> confirmHK (
            HttpSession session,
            @RequestParam("id") Integer id,
            @RequestParam("hkID") Integer hkID
    ) {
        String username = session.getAttribute("username").toString();
        appService.insertConfirmHKID(username, id, hkID);
        return new ResponseResult<>();
    }
}
