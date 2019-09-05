package controller;

import dao.IndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.HouseKeeper;
import pojo.ResponseResult;
import pojo.Type;
import service.HKPersonService;
import service.IndexService;
import utils.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: homeplus
 * @description: index的控制类
 * @author: JSY
 * @create: 2019-08-28 13:55
 **/
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/getAllType")
    @ResponseBody
    public ResponseResult<List<Type>> getAllType () {
        ResponseResult<List<Type>> result = new ResponseResult<>();
        List<Type> list = indexService.getAllType();
        result.setData(list);
        return result;
    }

    @RequestMapping("/getAllSmallType")
    @ResponseBody
    public ResponseResult<List<Type>> getAllSmallType (
            @RequestParam("type_id") Integer type_id
    ) {
        ResponseResult<List<Type>> result = new ResponseResult<>();
        List<Type> list = indexService.getAllSmallType(type_id);
        result.setData(list);
        return result;
    }

    /**
     * 获得保姆推荐
     */
    @RequestMapping("/getTopHousekeeper")
    @ResponseBody
    public ResponseResult<List<HouseKeeper>> getTopHouseKeeper () {
        ResponseResult<List<HouseKeeper>> result = new ResponseResult<>();
        //
        List<HouseKeeper> list = indexService.getTopHousekeeper(5);
        result.setData(list);
        return result;
    }


    @RequestMapping("/getRecommend")
    @ResponseBody
    public ResponseResult<List<HouseKeeper>> getRecommend (
        HttpSession session
    ) {
        ResponseResult<List<HouseKeeper>> result = new ResponseResult<>();
        List<HouseKeeper> list = indexService.getRecommend(session);
        result.setData(list);
        return result;
    }

    /**
     * 获取类别编号
     * @param param
     * @return
     */
    @RequestMapping("/getTypeID")
    @ResponseBody
    public ResponseResult<Integer> getTypeID (
            @RequestParam("param") String param
    ) {
        ResponseResult<Integer> result = new ResponseResult<>();
        int id = indexService.getTypeID(StringUtil.encodeStr(param));
        result.setData(id);
        return result;
    }

}
