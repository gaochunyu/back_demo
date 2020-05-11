package com.cennavi.tp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.ContentDao;
import com.cennavi.tp.service.ContentService;
import com.cennavi.tp.service.MenuDataService;
import com.cennavi.tp.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/17 14:50.
 */
@Service
public class ContentServiceImpl implements ContentService {

    static int flag = 1;//用来判断文件是否删除成功

    @Resource
    ContentDao contentDao;

    @Value("${file_path}")
    private String fileSavePath;

    @Autowired
    private ContentService contentService;
    @Autowired
    private MenuDataService menuDataService;

    //新增一条数据
    @Override
    public ResultModel addANewItem(int id, String title, String subTitle, String content, String tags, String autoSave,MultipartFile file, int uid) {
//        contentDao.save(contentBean);
        JSONObject json = new JSONObject();
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
            contentBean.setUid(uid);
            // 当文件不为空的时候进行文件的存储
            if(file != null) {
                if(!file.isEmpty()){
                    String fileName = file.getOriginalFilename();

                    // 创建文件夹    E://test

                    String rootPath = fileSavePath;
                    File test = new File(rootPath + "/" + String.valueOf(id));

                    if (!test.getParentFile().exists()) {
                        // 判断 test 文件夹是否存在，如果不存在就新建
                        test.getParentFile().mkdir();
                    }


                    // 拼接文件从存放的路径，创建id文件夹，保存对应的文件进去
                    String path = fileSavePath + String.valueOf(id);
                    File dest = new File(path + "/" + fileName);

                    if (!dest.getParentFile().exists()) {
                        //判断文件父目录是否存在
                        dest.getParentFile().mkdir();
                    }

                    try {
                        file.transferTo(dest); //保存文件
                        // 文件保存成功之后，将文件路径保存到 file 字段下面
                        contentBean.setFile(String.valueOf(id) + "/" + fileName);
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
            int changeNumber = contentDao.insertItem(contentBean);;
            //
            if(autoSave.equals("auto")) {
                int changeNumber1 = menuDataService.updateMenuStatus(contentId, 0);
            }else{
                int changeNumber1 = menuDataService.updateMenuStatus(contentId, 1);
            }

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


    // 删除一条数据
    public void deleteItemById(int id){
        contentDao.delete(id);
    }

    // 更新数据
    public ResultModel updateItemById(int id, String title, String subTitle, String content, String tags, String autoSave,Boolean isHaveFile, MultipartFile file, int uid) {
//
        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean = contentService.getItemById(id);
            contentBean.setTitle(title);
            contentBean.setSub_title(subTitle);
            contentBean.setContent(content);
            contentBean.setTags(tags);
            contentBean.setUid(uid);

            // 当文件不为空的时候进行文件的存储
            if(file != null) {
                String fileName = file.getOriginalFilename();
                if(!file.isEmpty()){

                    // 创建文件夹    E://test

                    String rootPath = fileSavePath;
                    File test = new File(rootPath + "/" + String.valueOf(id));

                    if (!test.getParentFile().exists()) {
                        // 判断 test 文件夹是否存在，如果不存在就新建
                        test.getParentFile().mkdir();
                    }


                    // 拼接文件从存放的路径，拼接文件创建时间和文件夹
                    String path = fileSavePath + id;
                    File dest = new File(path + "/" + fileName);
                    // 如果当前id的文件夹下已经存在了文件，先删除所有的文件
                    File unique = new File(path);
                    try{
                        deleteFile(unique);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (!dest.getParentFile().exists()) {
                        //判断文件父目录是否存在
                        dest.getParentFile().mkdir();
                    }

                    try {
                        file.transferTo(dest); //保存文件
                        // 文件保存成功之后，将文件路径保存到 file 字段下面
                        contentBean.setFile(String.valueOf(id) + "/" + fileName);
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
            else {
                // 当文件为空的时候
                if(isHaveFile) {
                    // 1.用户删除了文件执行删除逻辑

                    String path = fileSavePath + id;
                    File dest = new File(path + "/" + 1111);
                    // 如果当前id的文件夹下已经存在了文件，先删除所有的文件
                    File unique = new File(path);
                    deleteFile(unique);

                    contentBean.setFile("");

                } else {
                    // 2. 用户只是没有更新文件，依旧是之前的文件不需要再次上上传
                }

            }
            if(autoSave.equals("auto")) {
                menuDataService.updateMenuStatus(id, 0);
            }else{
                menuDataService.updateMenuStatus(id, 1);
            }
            contentDao.updateItemById(id, contentBean);
            return Result.success("成功更新一条数据", contentBean);
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("更新数据失败", JsonUtils.objectToJson(json,new String[]{}));
        }
    }

    // 查找数据
    public ContentBean getItemById(int id){
        ContentBean contentBean = contentDao.getItemById(id);
        return contentBean;
    }

    @Override
    public List<ContentBean> getContentsByTags(String tags) {
        List<ContentBean> contentBeanList = contentDao.getContentsByTags(tags);
        return contentBeanList;
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

}
