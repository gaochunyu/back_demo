package com.cennavi.tp.controller;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ContentService;
import com.cennavi.tp.service.MenuDataService;
import com.cennavi.tp.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/17 14:23.
 */
@Controller
@RequestMapping("/content")
public class ContentController {


    @Autowired
    private ContentService contentService;
    @Autowired
    private MenuDataService menuDataService;

    @Value("${file_path}")
    private String fileSavePath;


    // 新增 or 更新数据
    @ResponseBody
    @RequestMapping(value = "/addAContentItem", method = RequestMethod.POST)
    public ResultModel addAContentItem(int id, String title, String subTitle, String content, String tags,String autoSave, MultipartFile file,HttpServletRequest request) {

        // 首先去判断是新增还是更新
        int type = 0;
        JSONObject json = new JSONObject();
        try {
            content = content.replace("'","''");
            ContentBean contentBean1 =  contentService.getItemById(id);
            Object obj = request.getSession().getAttribute("user");
            if(obj==null){
                return Result.buildUnLogin();
            }
            UserinfoBean user = (UserinfoBean) obj;
            if(contentBean1 != null) {
                // 更新
                return updateItem(id, title,subTitle,content,tags,autoSave,file,user.getId());
            } else {
                // 新增
                return addItem(id, title,subTitle,content,tags,autoSave,file,user.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("查找数据失败", JsonUtils.objectToJson(json,new String[]{}));
        }
    }

    // 新增
    private ResultModel addItem(int id, String title, String subTitle, String content, String tags, String autoSave,MultipartFile file,int uid) {
        return contentService.addANewItem(id, title,subTitle,content,tags,autoSave,file,uid);
    }
    // 更新
    private ResultModel updateItem(int id, String title, String subTitle, String content, String tags,String autoSave, MultipartFile file,int uid){
        return contentService.updateItemById(id, title,subTitle,content,tags,autoSave,file,uid);
    }



    // 3. 查找数据：根据id获取一条表数据  RequestParam 默认参数是必填
    @ResponseBody
    @PostMapping("/getItemById")
    public ResultModel getItemById(@RequestParam(value = "id") int id) {

        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean =  contentService.getItemById(id);
            if(contentBean != null) {
                String content = contentBean.getContent().replace("''","'");
                contentBean.setContent(content);
                contentBean.setFile(fileSavePath + contentBean.getFile());
                return Result.success("成功找到一条数据", contentBean);
            } else {
                return Result.success("没有找到对应id的数据", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("查找数据失败", JsonUtils.objectToJson(json,new String[]{}));
        }
    }



    // 文件的下载
    @ResponseBody
    @RequestMapping(value = "/fileDownLoad", method = RequestMethod.POST)
    public ResultModel downLoad( HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        try {

            ContentBean contentBean =  contentService.getItemById(3);
    //        String filename="2.xlsx";
            String filePath = contentBean.getFile();

            File file = new File(filePath);
            if(file.exists()){ //判断文件父目录是否存在
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filePath,"UTF-8"));

                byte[] buffer = new byte[1024];
                FileInputStream fis = null; //文件输入流
                BufferedInputStream bis = null;

                OutputStream os = null; //输出流
                try {
                    os = response.getOutputStream();
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    int i = bis.read(buffer);
                    while(i != -1){
                        os.write(buffer);
                        i = bis.read(buffer);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("----------file download---" + filePath);

                try {
                    bis.close();
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return Result.success("文件下载成功", null);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("文件下载失败", JsonUtils.objectToJson(json,new String[]{}));
        }
    }

    /**
     * 根据tags检索内容
     * @param tags
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchContent")
    public ResultModel searchContent(String tags){
        try {
            List<ContentBean> contentBeanList = contentService.getContentsByTags(tags);
            return Result.success("检索成功",contentBeanList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("检索异常");

        }
    }
}

