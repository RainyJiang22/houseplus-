package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.*;
import service.AdminService;
import service.exception.UserNoLoginException;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: homeplus
 * @description:
 * @author: JSY
 * @create: 2019-08-29 15:11
 **/
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;


    /**
     * 获得传递过来的用户名
     * @param session
     * @return
     */
    @RequestMapping("/getCurrentUsername")
    @ResponseBody
    public ResponseResult<String> getCurrentUsername (
            HttpSession session
    ) {
        ResponseResult<String> result = new ResponseResult<>();
        String username = (String)session.getAttribute("username");
        if (StringUtils.isEmpty(username)) {
            throw new UserNoLoginException();
        } else {
            result.setData(username);
        }
        return result;
    }

    @RequestMapping("/selectAllLogin")
    @ResponseBody
    public ResponseResult<List<User>> selectAllLogin () {
        ResponseResult<List<User>> response = new ResponseResult<>();
        List<User> list = adminService.selectAllLogin();
        response.setData(list);
        return response;
    }


    /**
     * 查找所有雇主
     * @return
     */
    @RequestMapping("/selectAllCustomer")
    @ResponseBody
    public ResponseResult<List<Customer>> selectAllCustomer () {
        ResponseResult<List<Customer>> response = new ResponseResult<>();
        List<Customer> list = adminService.selectAllCustomer();
        response.setData(list);
        return response;
    }


    /**
     * 插入到消费者表
     * @param nickname
     * @param name
     * @param gender
     * @param phone
     * @param email
     * @param password
     * @param address
     * @return
     */
    @RequestMapping("/insertCustomer")
    @ResponseBody
    public ResponseResult<Void> insertCustomer (
            @RequestParam("nickname") String nickname,
            @RequestParam("name") String name,
            @RequestParam("gender") String gender,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("address") String address
    ) {
        ResponseResult<Void> response = new ResponseResult<>();
        Customer customer = new Customer();
        customer.setCmNickname(nickname);
        customer.setCmName(name);
        customer.setCmSex(gender);
        customer.setCmPhone(phone);
        customer.setCmEmail(email);
        customer.setCmPassword(password);
        customer.setCmAddress(address);
        adminService.insertCustomer(customer);
        return response;
    }


    /**
     * 更新管理表
     * @param name
     * @param gender
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/insertAdmin")
    @ResponseBody
    public ResponseResult<Void> insertAdmin (
            @RequestParam("name") String name,
            @RequestParam("gender") String gender,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password
    ) {
        ResponseResult<Void> response = new ResponseResult<>();
        Admin admin = new Admin();
        admin.setAdName(name);
        admin.setAdPassword(password);
        admin.setAdPhone(phone);
        admin.setAdSex(gender);
        adminService.insertAdmin(admin);
        return response;
    }

    @RequestMapping("/updateLoginStatus")
    @ResponseBody
    public ResponseResult<Void> updateLoginStatus (
            @RequestParam("username") String username
    ) {
        ResponseResult<Void> response = new ResponseResult<>();
        adminService.updateLoginStatus(username);
        return  response;
    }

    /**
     * 获取订单的图表信息
     * @return
     */
    @RequestMapping("/getOrderData")
    @ResponseBody
    public ResponseResult<List<EchartsData>> getOrderData(){
        ResponseResult<List<EchartsData>> response = new ResponseResult<>();
        List<EchartsData> echartsData = adminService.getOrderData();
        response.setData(echartsData);
        return response;
    }

    /**
     * 获取预约的图表信息
     * @return
     */
    @RequestMapping("/getAppoimentData")
    @ResponseBody
    public ResponseResult<List<EchartsData>> getAppoimentData(){
        ResponseResult<List<EchartsData>> response = new ResponseResult<>();
        List<EchartsData> echartsData = adminService.getAppoimentData();
        response.setData(echartsData);
        return response;
    }

    /**
     * 修改用户密码
     * @param id 用户id
     * @param password 用户密码
     * @param role 用户角色
     * @param status 用户状态
     * @return
     */
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public ResponseResult<Void> updateUserInfo (
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("status") String status
    ) {
        adminService.updateUserInfo(Integer.parseInt(id), password, Integer.parseInt(role), Integer.parseInt(status));
        return new ResponseResult<>();
    }

    /**
     * 获得未认证的雇主信息
     * @return
     */
    @RequestMapping("/getAllCustomerCertify")
    @ResponseBody
    public ResponseResult<List<Customer>> getAllCustomerCertify () {
        ResponseResult<List<Customer>> result = new ResponseResult<>();
        List<Customer> list = adminService.getAllCustomerCertify();
        result.setData(list);
        return result;
    }

    /**
     * 获得所有未认证的家政人员
     * @return
     */
    @RequestMapping("/getAllHousekeeperCertify")
    @ResponseBody
    public ResponseResult<List<HouseKeeper>> getAllHousekeeperCertify () {
        ResponseResult<List<HouseKeeper>> result = new ResponseResult<>();
        List<HouseKeeper> list = adminService.getAllHousekeeperCertify();
        result.setData(list);
        return result;
    }

    /**
     * 获得所有未认证的公司信息
     * @return
     */
    @RequestMapping("/getAllCompanyCertify")
    @ResponseBody
    public ResponseResult<List<Company>> getAllCompanyCertify () {
        ResponseResult<List<Company>> result = new ResponseResult<>();
        List<Company> list = adminService.getAllCompanyCertify();
        result.setData(list);
        return result;
    }

    /**
     * 获取消费者ID编号
     * @param id  消费者ID编号
     * @return
     */
    @RequestMapping("/getCustomerByID")
    @ResponseBody
    public ResponseResult<Customer> getCustomerByID (
            @RequestParam("id") String id
    ) {
        ResponseResult<Customer> result = new ResponseResult<>();
        Customer customer = adminService.getCustomerByID(Integer.parseInt(id));
        result.setData(customer);
        return result;
    }

    @RequestMapping("/getHousekeeperByID")
    @ResponseBody
    public ResponseResult<HouseKeeper> getHousekeeperByID (
            @RequestParam("id") String id
    ) {
        ResponseResult<HouseKeeper> result = new ResponseResult<>();
        HouseKeeper houseKeeper = adminService.getHousekeeperByID(Integer.parseInt(id));
        result.setData(houseKeeper);
        return result;
    }

    @RequestMapping("/getCompanyByID")
    @ResponseBody
    public ResponseResult<Company> getCompanyByID (
            @RequestParam("id") String id
    ) {
        ResponseResult<Company> result = new ResponseResult<>();
        Company company = adminService.getCompanyByID(Integer.parseInt(id));
        result.setData(company);
        return result;
    }

    @RequestMapping("/cancelCustomerByID")
    @ResponseBody
    public ResponseResult<Void> cancelCustomerByID (
            @RequestParam("id") String id
    ) {
        adminService.updateCustomerStatusByID(Integer.parseInt(id), 2);
        return new ResponseResult<>();
    }

    @RequestMapping("/certifyCustomerByID")
    @ResponseBody
    public ResponseResult<Void> certifyCustomerByID (
            @RequestParam("id") String id
    ) {
        adminService.updateCustomerStatusByID(Integer.parseInt(id), 1);
        return new ResponseResult<>();
    }

    /**
     * 取消家政人员的认证管理
     * @param id
     * @return
     */
    @RequestMapping("/cancelHousekeeperByID")
    @ResponseBody
    public ResponseResult<Void> cancelHousekeeperByID (
            @RequestParam("id") String id
    ) {
        //获取当前家政人员编号
        adminService.updateHousekeeperStatusByID(Integer.parseInt(id), 2);
        return new ResponseResult<>();
    }

    /**
     * 添加家政人员的认证管理
     * @param id
     * @return
     */
    @RequestMapping("/certifyHousekeeperByID")
    @ResponseBody
    public ResponseResult<Void> certifyHousekeeperByID (
            @RequestParam("id") String id
    ) {
        adminService.updateHousekeeperStatusByID(Integer.parseInt(id), 1);
        return new ResponseResult<>();
    }

    @RequestMapping("/cancelCompanyByID")
    @ResponseBody
    public ResponseResult<Void> cancelCompanyByID (
            @RequestParam("id") String id
    ) {
        adminService.updateCompanyStatusByID(Integer.parseInt(id), 2);
        return new ResponseResult<>();
    }

    /**
     * 通过ID进行认证公司
     * @param id
     * @return
     */
    @RequestMapping("/certifyCompanyByID")
    @ResponseBody
    public ResponseResult<Void> certifyCompanyByID (
            @RequestParam("id") String id
    ) {
        adminService.updateCompanyStatusByID(Integer.parseInt(id), 1);
        return new ResponseResult<>();
    }

    /**
     * 通过手机号找到用户
     * @param phone
     * @return
     */
    @RequestMapping("/getUserByPhone")
    @ResponseBody
    public ResponseResult<List<User>> getUserByPhone (
            @RequestParam("phone") String phone
    ) {
        ResponseResult<List<User>> result = new ResponseResult<>();
        List<User> list = adminService.getUserByPhone(phone);
        result.setData(list);
        return result;
    }
}
