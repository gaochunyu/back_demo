package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentImgBean;
import com.cennavi.tp.beans.ComponentTypeBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.ComponentDao;
import com.cennavi.tp.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: chenfeng
 * @date: 2020/4/29 17:33
 */
@Service
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    private ComponentDao componentDao;

    @Value("${file_path}")
    private String fileSaveRootPath;

    // 新增
    @Override
    public boolean addComponent(Integer uid, String name, Integer type, String tags, String content, MultipartFile coverImg, List<MultipartFile> imgList, String visitUrl, MultipartFile file) {
        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = df.format(new Date());

        // 组件状态 0-录入中 1-待审核(默认) 2-审核成功 3-审核失败
        int status = 1;

        // coverImg/imgList/file 另行处理
        ComponentBean componentBean = new ComponentBean();
        componentBean.setUid(uid);
        componentBean.setName(name);
        componentBean.setType(type);
        componentBean.setTags(tags);
        componentBean.setContent(content);
        componentBean.setVisit_url(visitUrl);
        componentBean.setCreate_time(createTime);
        componentBean.setStatus(status);

        // 处理coverImg封面图
        if (coverImg != null && !coverImg.isEmpty()) {
            // 创建图片文件夹
            String coverImgSavePath = fileSaveRootPath + "component/img/cover";
            File pathTest = new File(coverImgSavePath + "/");
            // 如果文件夹不存在就创建
            if (!pathTest.exists()) {
                pathTest.mkdirs();
            }
            String coverImgName = coverImg.getOriginalFilename();
            // 拼接路径和文件名，并存入数据库
            String coverImgPath = coverImgSavePath + "/" + coverImgName;
            File f = new File(coverImgPath);

            try {
                // 保存封面图文件
                coverImg.transferTo(f);
                componentBean.setCover_img("component/img/cover/" + coverImgName);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }

        // 处理imgList展示图列表
        List<ComponentImgBean> list = new ArrayList<>();
        // 要保存到数据库的展示图的地址列表
        List<String> imgDataPathList = new ArrayList<>();
        if ( imgList != null && imgList.size() > 0) {
            for (MultipartFile imgFile : imgList) {
                // 如果当前图片文件存在
                if (imgFile != null && !imgFile.isEmpty()) {
                    String imgFilePath = fileSaveRootPath + "component/img/imgList";
                    File testPath = new File(imgFilePath + "/");
                    // 不存在路径，就创建
                    if (!testPath.exists()) {
                        testPath.mkdirs();
                    }
                    // 获取图片文件名称
                    String fileName = imgFile.getOriginalFilename();
                    // 拼接图片完整路径
                    String fullFilePath = imgFilePath + "/" + fileName;
                    // 创建文件并保存
                    File f = new File(fullFilePath);

                    try {
                        // 保存文件
                        imgFile.transferTo(f);
                        String imgDataPath = "component/img/imgList/" + fileName;
                        ComponentImgBean componentImgBean = new ComponentImgBean();
                        componentImgBean.setImg_url(imgDataPath);
                        list.add(componentImgBean);
                        imgDataPathList.add(imgDataPath);
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 处理file上传文件
        if (file != null && !file.isEmpty()) {
            // 创建文件文件夹
            String fileSavePath = fileSaveRootPath + "component/file";
            File pathTest = new File(fileSavePath + "/");
            // 如果文件夹不存在就创建
            if (!pathTest.exists()) {
                pathTest.mkdirs();
            }
            String fileName = coverImg.getOriginalFilename();
            // 拼接路径和文件名，并存入数据库
            String fileFullPath = fileSavePath + "/" + fileName;
            File f = new File(fileFullPath);

            try {
                // 保存封面图文件
                file.transferTo(f);
                componentBean.setFile_url("component/file/" + fileName);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }

        return componentDao.addComponent(componentBean, list, imgDataPathList);
    }

    // 删除
    @Override
    public ResultModel delComponent(Integer id, Integer uid) {
        Integer count = componentDao.delComponent(id,uid);
        if (count == 0) {
            return Result.fail("输入的id无对应数据", count);
        }
        else {
            return Result.success("删除成功", count);
        }
    }

    @Override
    public ResultModel updateComponent(Integer id, Integer uid, String name, String tags, String cover, String content, String testUrl, String fileUrl) {
        return null;
    }

    // 获取组件类型列表
    @Override
    public List<ComponentTypeBean> getComponentTypeList() {
        return componentDao.getComponentTypeList();
    }

}
