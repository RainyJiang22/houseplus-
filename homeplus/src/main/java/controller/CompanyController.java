package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Company;
import pojo.ResponseResult;
import service.CompanyService;

import java.util.List;

/**
 * @program: homeplus
 * @description: Company的控制类
 * @author: JSY
 * @create: 2019-09-1 19:31
 **/
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;

    /**
     * 获取所有认证的公司
     * @return
     */
    @RequestMapping("/getAllCertifyCompany")
    @ResponseBody
    public ResponseResult<List<Company>> getAllCertifyCompany () {
        ResponseResult<List<Company>> result = new ResponseResult<>();
        List<Company> list = companyService.getCompanyByStatus(1);
        result.setData(list);
        return result;
    }
}
