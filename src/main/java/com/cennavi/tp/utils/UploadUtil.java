package com.cennavi.tp.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件上传util
 */
public class UploadUtil {


    /**
     * 文件上传
     * @param relativePath 文件要保存的相对路径
     * @param file 要处理的由前端上传来的文件
     * @return 存入数据库的相对路径
     */
    public static String  handleFileUpload(String fileSaveRootPath,String relativePath, MultipartFile file) {
        String format = "yyyyMMddHHmmss";
        String time = MyDateUtils.format(new Date(),format);
        String absolutePath = fileSaveRootPath + relativePath;
        String fileName = file.getOriginalFilename();
        System.out.println("fileName " + fileName);
        String[] split = fileName.split("\\.");
        String finalFileName = split[0]+ time+"."+split[1];

        String relativeFullPath = relativePath + finalFileName;
        String absoluteFullPath = absolutePath + finalFileName;
        // 判断文件夹是否存在
        File dirTest = new File(absolutePath);
        if (!dirTest.exists()) {
            dirTest.mkdirs();
        }
        // 获取文件
        File f = new File(absoluteFullPath);
        try {
            // 保存文件
            file.transferTo(f);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return relativeFullPath;
    }
}
