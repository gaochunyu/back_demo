package com.cennavi.tp.controller;

import com.cennavi.tp.beans.NotesBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.NotesService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @ResponseBody
    @RequestMapping("/getNotesInfoById")
    public ResultModel getNotesInfoById(@RequestParam(value = "notesId") Integer notesId, HttpServletRequest request){
        try{
            Object obj = request.getSession().getAttribute("user");
            if(obj==null){
                return Result.buildUnLogin();
            }
            UserinfoBean user = (UserinfoBean) obj;
            int uid = user.getId();
            Map list = notesService.getNotesInfoById(notesId,uid);
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

    @ResponseBody
    @RequestMapping("/saveNotesInfo")
    public ResultModel saveNotesInfo(int operation , int id , String title , String content , int sort , MultipartFile file , boolean isUpdate , HttpServletRequest request){
        try{
            Object obj = request.getSession().getAttribute("user");
            if(obj==null){
                return Result.buildUnLogin();
            }
            UserinfoBean user = (UserinfoBean) obj;
            int uid = user.getId();
            boolean flag = notesService.saveNotesInfo(operation,id ,title , content , sort , file ,isUpdate ,uid);
            if(flag){
                return Result.success("笔记信息保存成功",flag);
            }else{
                return Result.fail("笔记信息保存失败",new Object());
            }
        }catch (Exception e){
            e.getStackTrace();
            System.out.println(e.getMessage() + e);
            return Result.build500("出现异常");
        }
    }

    @ResponseBody
    @RequestMapping("/deleteNotesInfoById")
    public ResultModel deleteNotesInfoById(@RequestParam(value = "notesId") Integer notesId){
        try{
            boolean flag = notesService.deleteNotesInfo(notesId);
            if(!flag){
                return Result.fail("删除失败",new ArrayList<>()); //1000
            }else{
                return Result.success("删除成功",new ArrayList<>());  //200
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    @ResponseBody
    @RequestMapping("/updateNotesStatus")
    public ResultModel updateNotesStatus(@RequestParam(value = "notesId") Integer notesId,@RequestParam(value = "status") Integer status){
        try{
            boolean flag = notesService.updateStatus(notesId , status);
            if(!flag){
                return Result.fail("审核失败",new ArrayList<>()); //1000
            }else{
                return Result.success("审核成功",new ArrayList<>());  //200
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    @ResponseBody
    @RequestMapping("/updateNotesViewNum")
    public ResultModel updateNotesViewNum(@RequestParam(value = "notesId") Integer notesId){
        try{
            boolean flag = notesService.updateViewNum(notesId);
            if(!flag){
                return Result.fail("增加失败",new ArrayList<>()); //1000
            }else{
                return Result.success("增加成功",new ArrayList<>());  //200
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    @ResponseBody
    @RequestMapping("/updateNotesFavourNum")
    public ResultModel updateNotesFavourNum(@RequestParam(value = "notesId") Integer notesId,@RequestParam(value = "type") boolean type,HttpServletRequest request){
        try{
            Object obj = request.getSession().getAttribute("user");
            if(obj==null){
                return Result.buildUnLogin();
            }
            UserinfoBean user = (UserinfoBean) obj;
            int uid = user.getId();
            boolean flag = notesService.updateFavourNum(notesId ,type ,uid);
            if(!flag){
                return Result.fail("修改失败",new ArrayList<>()); //1000
            }else{
                return Result.success("修改成功",new ArrayList<>());  //200
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

}
