package com.cennavi.tp.controller;

import com.cennavi.tp.beans.FeedbackBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.FeedbackService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @RequestMapping("/addFeedback")
    @ResponseBody
    public ResultModel addFeedback(FeedbackBean feedbackBean){

        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            String time = MyDateUtils.format(new Date(),format);
            feedbackBean.setCreatetime(time);
            boolean flag = feedbackService.addFeedback(feedbackBean);
            if(flag){
                return Result.success("添加成功");
            }else{
                return Result.fail("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常");
        }

    }

}
