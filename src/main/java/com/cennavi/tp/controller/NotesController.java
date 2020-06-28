package com.cennavi.tp.controller;

import com.cennavi.tp.beans.NotesBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notesData")
public class NotesController {

    @Resource
    private NotesService notesService;

    @ResponseBody
    @RequestMapping("/getNotesList")
    public ResultModel getNotesList(@RequestParam(value = "limitSize") Integer limitSize , @RequestParam(value = "curPage") Integer curPage , @RequestParam(value = "notesType") String notesType , @RequestParam(value = "status") String status , @RequestParam(value = "userId") Integer userId){
        try{
            Map list = notesService.getNotesList(limitSize,curPage ,notesType ,status ,userId);
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
