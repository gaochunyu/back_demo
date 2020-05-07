package com.cennavi.tp.controller;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.TradeDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tradeType")
public class TradeTypeController {

    @Resource
    private TradeDataService tradeDataService;

    @ResponseBody
    @RequestMapping("/getTradeTypeList")
    public ResultModel getTradeTypeList(){
        try{
            List list = tradeDataService.getTradeTypeList();
            if(list == null && list.size() == 0){
                return Result.fail("暂无数据",new ArrayList<>()); //1000
            }else{
                return Result.success("成功获取数据",list);  //200
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

}
