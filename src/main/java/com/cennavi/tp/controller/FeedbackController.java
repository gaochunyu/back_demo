package com.cennavi.tp.controller;

import com.cennavi.tp.beans.FeedbackBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.FeedbackService;
import com.cennavi.tp.utils.MyDateUtils;
import com.cennavi.tp.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Value("${file_path}")
    private String fileSavePath;


    @ResponseBody
    @RequestMapping("/addFeedback")
    public ResultModel addFeedback(FeedbackBean feedbackBean,List<MultipartFile> imgList){

        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            String time = MyDateUtils.format(new Date(),format);
            feedbackBean.setCreatetime(time);

            String path = dealuUploadFile(imgList);

            feedbackBean.setImg_url(path);
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

    public String dealuUploadFile(List<MultipartFile> imgList){
        StringBuffer pathString=new StringBuffer();
        int count = 0;
        for (MultipartFile multipartFile: imgList) {
            String finalPath = UploadUtil.handleFileUpload(fileSavePath, "test/", multipartFile);
            if (count<imgList.size()-1){
                pathString.append(finalPath).append(",");
            }else {
                pathString.append(finalPath);
            }
            count++;
        }

        return pathString.toString();
    }

    @ResponseBody
    @RequestMapping("/getFeedbackList")
    public ResultModel getFeedbackList(Integer page,Integer pageSize,String keyword, HttpServletRequest request){

        try {
            if (keyword == null) {
                keyword = "";
            }
            Map<String,Object> map = new HashMap<>();
            List<FeedbackBean> list = feedbackService.getFeedbackList(page,pageSize,keyword);
            int count = feedbackService.getFeedbackCount(page,pageSize,keyword);
            map.put("list",list);
            map.put("total",count);
            ResultModel resultModel = Result.success("查询成功",map);
            resultModel.putExcludes("password");
            return resultModel;
        }catch (Exception e){
            e.printStackTrace();
            return Result.build500("查询出现异常");
        }

    }

    @ResponseBody
    @RequestMapping("/deleteFeedback")
    public ResultModel deleteFeedback(Integer id){

        boolean flag = feedbackService.deleteFeedback(id);
        if(flag){
            return Result.success("删除成功");
        }else{
            return Result.fail("删除失败");
        }
    }


    @ResponseBody
    @RequestMapping("/updateFeedbackState")
    public ResultModel updateFeedbackState(FeedbackBean feedbackBean){

        String format = "yyyy-MM-dd HH:mm:ss";
        String time = MyDateUtils.format(new Date(),format);
        feedbackBean.setUpdatetime(time);
        boolean flag = feedbackService.updateFeedbackState(feedbackBean);
        if(flag){
            return Result.success("更新成功");
        }else{
            return Result.fail("更新失败");
        }
    }


    @ResponseBody
    @RequestMapping("/updateFeedback")
    public ResultModel updateFeedback(FeedbackBean feedbackBean,List<MultipartFile> imgList){

//        String format = "yyyy-MM-dd HH:mm:ss";
//        String time = MyDateUtils.format(new Date(),format);
//        feedbackBean.setUpdatetime(time);
        //imgList.size()>0,说明图片有更新，重新上传
        if (imgList.size()>0){
            String path = dealuUploadFile(imgList);
            feedbackBean.setImg_url(path);
        }
        boolean flag = feedbackService.updateFeedback(feedbackBean);
        if(flag){
            return Result.success("更新成功");
        }else{
            return Result.fail("更新失败");
        }
    }

}
