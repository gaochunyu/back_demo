package com.cennavi.tp.controller;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ContentService;
import com.cennavi.tp.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/17 14:23.
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    static int flag = 1;//用来判断文件是否删除成功

    @Autowired
    private ContentService contentService;


    // 1 新增一条数据, 带有文件的上传
    @ResponseBody
    @RequestMapping(value = "/addAContentItem", method = RequestMethod.POST)
    public ResultModel addAContentItem(int id, String title, String subTitle, String content, int type, String tags, MultipartFile file) {

        JSONObject json = new JSONObject();
        content = content.replace("'","''");
        if(type == 1) {
            // 新增
            try {
                // 获取时间戳并转化格式
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String createTime = formatter.format(date);

                int contentId = id;

                ContentBean contentBean = new ContentBean();
                contentBean.setTitle(title);
                contentBean.setSub_title(subTitle);
                contentBean.setContent(content);
                contentBean.setTags(tags);
                contentBean.setId(contentId);
                contentBean.setCreate_time(createTime);

                // 当文件不为空的时候进行文件的存储
                if(file != null) {
                    if(!file.isEmpty()){
                        String fileName = file.getOriginalFilename();


                        // 拼接文件从存放的路径，创建id文件夹，保存对应的文件进去
                        String path = "E:/test/"+ String.valueOf(id);
                        File dest = new File(path + "/" + fileName);

                        if (!dest.getParentFile().exists()) {
                            //判断文件父目录是否存在
                            dest.getParentFile().mkdir();
                        }

                        try {
                            file.transferTo(dest); //保存文件
                            // 文件保存成功之后，将文件路径保存到 file 字段下面
                            contentBean.setFile(path + "/" + fileName);
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return Result.fail("保存文件失败", JsonUtils.objectToJson(json, new String[]{}));

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return Result.fail("保存文件失败", JsonUtils.objectToJson(json, new String[]{}));

                        }
                    }

                }


                // 获得刚刚插入的key值
                int changeNumber = contentService.addANewItem(contentBean);

                if(changeNumber == 1) {
                    return Result.success("成功插入数据", contentService.getItemById(contentId));
                } else {
                    return Result.fail("插入数据失败", JsonUtils.objectToJson(json, new String[]{}));
                }

            } catch (Exception e) {
                e.printStackTrace();
                json = JsonUtils.packJsonErr(e.getMessage());
                return Result.fail("插入数据失败", JsonUtils.objectToJson(json, new String[]{}));
            }

        }
        else {
            try {
                ContentBean contentBean = new ContentBean();
                contentBean.setTitle(title);
                contentBean.setTitle(subTitle);
                contentBean.setContent(content);
                contentBean.setTags(tags);

                if(file != null){
                    // 当文件不为空的时候进行文件的存储
                    if(!file.isEmpty()){
                        String fileName = file.getOriginalFilename();

                        // 拼接文件从存放的路径，拼接文件创建时间和文件夹
                        String path = "E:/test/"+ id;
                        File dest = new File(path + "/" + fileName);
                        // 如果当前id的文件夹下已经存在了文件，先删除所有的文件
                        File unique = new File(path);
                        deleteFile(unique);
                        if (!dest.getParentFile().exists()) {
                            //判断文件父目录是否存在
                            dest.getParentFile().mkdir();
                        }

                        try {
                            file.transferTo(dest); //保存文件
                            // 文件保存成功之后，将文件路径保存到 file 字段下面
                            contentBean.setFile(path + "/" + fileName);
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return Result.fail("保存文件失败", JsonUtils.objectToJson(json, new String[]{}));

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return Result.fail("保存文件失败", JsonUtils.objectToJson(json, new String[]{}));

                        }
                    }
                }
                contentService.updateItemById(id, contentBean);
                return Result.success("成功更新一条数据", contentBean);
            } catch (Exception e) {
                e.printStackTrace();
                json = JsonUtils.packJsonErr(e.getMessage());
                return Result.fail("更新数据失败", JsonUtils.objectToJson(json,new String[]{}));
            }

        }
    }



//    // 2 更新某条数据内容, 如果包含文件的更新
//    @ResponseBody
//    @RequestMapping(value = "/updateItemById", method = RequestMethod.POST)
//    public ResultModel updateItemById(@RequestParam Map<String,String> map, MultipartFile file){
//        if (map.size() > 0) {
//            JSONObject json = new JSONObject();
//            try {
//                ContentBean contentBean = new ContentBean();
//                contentBean.setTitle(map.get("title"));
//                contentBean.setTitle(map.get("subTitle"));
//                contentBean.setContent(map.get("content"));
//                contentBean.setTags(map.get("tags"));
//
//                int id = Integer.parseInt(map.get("id"));
//
//                if(file != null){
//                    // 当文件不为空的时候进行文件的存储
//                    if(!file.isEmpty()){
//                        String fileName = file.getOriginalFilename();
//
//                        // 拼接文件从存放的路径，拼接文件创建时间和文件夹
//                        String path = "E:/test/"+ map.get("id").toString();
//                        File dest = new File(path + "/" + fileName);
//                        // 如果当前id的文件夹下已经存在了文件，先删除所有的文件
//                        File unique = new File(path);
//                        deleteFile(unique);
//                        if (!dest.getParentFile().exists()) {
//                            //判断文件父目录是否存在
//                            dest.getParentFile().mkdir();
//                        }
//
//                        try {
//                            file.transferTo(dest); //保存文件
//                            // 文件保存成功之后，将文件路径保存到 file 字段下面
//                            contentBean.setFile(path + "/" + fileName);
//                        } catch (IllegalStateException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                            return Result.fail("保存文件失败", JsonUtils.objectToJson(json, new String[]{}));
//
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                            return Result.fail("保存文件失败", JsonUtils.objectToJson(json, new String[]{}));
//
//                        }
//                    }
//                }
//                contentService.updateItemById(id, contentBean);
//                return Result.success("成功更新一条数据", contentBean);
//            } catch (Exception e) {
//                e.printStackTrace();
//                json = JsonUtils.packJsonErr(e.getMessage());
//                return Result.fail("更新数据失败", JsonUtils.objectToJson(json,new String[]{}));
//            }
//        }
//        return null;
//
//    }


    // 3. 删除一条数据(根据id删除一条数据)
    @ResponseBody
    @RequestMapping(value = "/deleteItemById", method = RequestMethod.POST)
    public ResultModel deleteItemById(@RequestParam(value = "id") int id) {
        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean =  contentService.getItemById(id);
            contentService.deleteItemById(id);
            return Result.success("成功删除一条数据,删除的数据是", contentBean);
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("删除数据失败", JsonUtils.objectToJson(json,new String[]{}));
        }
    }


    // 4. 查找数据：根据id获取一条表数据  RequestParam 默认参数是必填
    @ResponseBody
    @PostMapping("/getItemById")
    public ResultModel getItemById(@RequestParam(value = "id") int id) {

        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean =  contentService.getItemById(id);
            String content = contentBean.getContent().replace("''","'");
            contentBean.setContent(content);
            if(contentBean != null) {
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


    // 删除一个路径下所有的文件
    public static void deleteFile(File file){
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()){
            flag = 0;
            System.out.println("文件删除失败,请检查文件路径是否正确");
            return;
        }

        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files){
            //打印文件名
            String name = file.getName();
            System.out.println(name);
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()){
                deleteFile(f);
            }else {
                f.delete();
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        file.delete();
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

}

