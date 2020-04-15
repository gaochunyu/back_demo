package com.cennavi.tp.controller;

import com.cennavi.tp.beans.BaseAreaRoadRtic;
import com.cennavi.tp.beans.BaseLink;
import com.cennavi.tp.beans.BaseRtic;
import com.cennavi.tp.beans.BaseRticLink;
import com.cennavi.tp.service.BaseDataService;
import com.cennavi.tp.utils.GeomtryUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *  用于静态数据导入，如位置参考
 * Created by sunpengyan on 2017/10/26.
 */
@Controller
@RequestMapping("/static")
public class ImportController {


    @Autowired
    private BaseDataService baseDataService;


    /**
     * 导入BaseAreaRoadRtic 配置表之间的关系  -》 t_area_road_roadsection_rtic
     * @return
     */
    @RequestMapping("/importAreaRoadRtic")
    @ResponseBody
    public String importAreaRoadRtic(@RequestParam(value = "file") MultipartFile file,
                                     @RequestParam(value = "city",required = false,defaultValue = "xian") String city) {
        try {
            InputStream in = file.getInputStream();

            InputStreamReader read = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(read);

            List<BaseAreaRoadRtic> maps = new ArrayList<>();
            String lineTxt = "";
            while((lineTxt = bufferedReader.readLine()) != null) {
                if(lineTxt.toLowerCase().contains("area_id")) { continue;  }
                String[] ss = lineTxt.replace("\"","").split("\t");
                BaseAreaRoadRtic barr = new BaseAreaRoadRtic();
                barr.setArea_id(ss[0]);
                barr.setRoad_id(ss[1]);
                barr.setSeq_no(Integer.parseInt(ss[2]));
                barr.setRtic_id(ss[5]);
                //map.put("rtic_no",ss[4]);
                //map.put("map_version",ss[6]);
                maps.add(barr);
            }
            baseDataService.batchSaveAreaRoadRtic(maps,city);
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return "fail"+e.getMessage();
        }
        return "success";
    }

    /**
     * 导入张家口静态数据 rtic表  -》 rtic
     * @return
     */
    @RequestMapping("/importRtic")
    @ResponseBody
    public String importRtic(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "city",required = false,defaultValue = "xian") String city) {
        try {
            // String filePath = "E:\\交警平台\\b基础配置表\\张家口\\18Q1_M3W1\\130700\\t_rtic_130700.txt";
            List<BaseRtic> list = new ArrayList<>();
            InputStream in = file.getInputStream();
            InputStreamReader read = new InputStreamReader(in,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);

            String lineTxt = "";
            while((lineTxt = bufferedReader.readLine()) != null) {
                if(lineTxt.toLowerCase().contains("rtic_id")) {
                    continue;
                }
                String[] ss = lineTxt.replace("\"","").split("\t");
                BaseRtic baseRtic = new BaseRtic();
                baseRtic.setId(ss[0]);
                baseRtic.setName(ss[1]);
                baseRtic.setStartName(ss[2]);
                baseRtic.setEndName(ss[3]);
                baseRtic.setLength(Float.parseFloat(ss[4]));
                //baseRtic.setLaneNum(ss[5]);
                baseRtic.setWidth(ss[6]);
                baseRtic.setSpeedLimit(ss[7]);
                baseRtic.setDirection(ss[8]);
                baseRtic.setKind(ss[9]);
                baseRtic.setStartpoint(ss[10]+","+ss[11]);
                baseRtic.setEndpoint(ss[12]+","+ss[13]);
                baseRtic.setCenter("{\"type\":\"Point\",\"coordinates\":["+ss[14].replace(" ",",")+"]}");
                baseRtic.setGeometry(GeomtryUtils.wkt2GeoJson(ss[15]));
                //baseRtic.setVersion(ss[16]);
                list.add(baseRtic);
                if(list.size()>=3000) {
                    baseDataService.batchSaveRtic(list,city);
                    list = new ArrayList<>();
                }
            }
            baseDataService.batchSaveRtic(list,city);
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return "fail"+e.getMessage();        }
        return "success";
    }

    /**
     * 导入correspondingOfxian表  -》 base_rtic_link
     * @return
     */
    @RequestMapping("/importRticLink")
    @ResponseBody
    public String importRticLink(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "city",required = false,defaultValue = "xian") String city) {
        try {
            List<BaseRticLink> list = new ArrayList<>();
            InputStream in = file.getInputStream();
            InputStreamReader read = new InputStreamReader(in,"utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);

            String lineTxt = "";
            while((lineTxt = bufferedReader.readLine()) != null) {
                if(lineTxt.toLowerCase().contains("meshno")) {
                    continue;
                }
                String[] ss = lineTxt.replace("\"","").split(",");
                long rticid = Long.parseLong(ss[1])*10000+Long.parseLong(ss[2]);
                int n = 1;
                for (int i = 6; i < ss.length; i+=2) {
                    BaseRticLink rl = new BaseRticLink();
                    rl.setRticid(rticid+"");
                    rl.setLinkid(ss[i]);
                    rl.setSort(n);
                    n++;
                    list.add(rl);
                }
                if(list.size()>=3000) {
                    baseDataService.batchSaveRticLink(list,city);
                    list = new ArrayList<>();
                }
            }
            baseDataService.batchSaveRticLink(list,city);
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return "fail"+e.getMessage();
        }
        return "success";
    }

    /**
     * 导入静态数据 r表  -》 base_link
     * @return
     */
    @RequestMapping("/importLink")
    @ResponseBody
    public String importLink(@RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "city",required = false,defaultValue = "xian") String city) {
        long t1 = System.currentTimeMillis();
        List<BaseLink> list = new ArrayList<>();
        String packageName = file.getOriginalFilename();
        if(packageName.matches(".*\\.zip")) {
            try {
                ZipInputStream zs = new ZipInputStream(file.getInputStream());
                //BufferedInputStream bs = new BufferedInputStream(zs);
                InputStreamReader read = new InputStreamReader(zs, "GBK");
                BufferedReader br = new BufferedReader(read);
                List<List<String>> geomList = new ArrayList<>();
                List<String> linkInfoList = new ArrayList<>();
                ZipEntry ze;
                while ((ze = zs.getNextEntry()) != null) {                    //获取zip包中的每一个zip file entry
                    String fileName = ze.getName();                            //pictureName
                    if (fileName.toLowerCase().matches(".*\\.mif")) {
                        String temp;
                        while ((temp = br.readLine()) != null) {
                            String[] splits = temp.split(" ");
                            if (splits[0].equals("Line")) {//Line 108.46330 34.16144 108.46344 34.16333
                                List<String> templist = new ArrayList<>();
                                templist.add(splits[1] + "," + splits[2]);
                                templist.add(splits[3] + "," + splits[4]);
                                geomList.add(templist);
                            } else if (splits[0].equals("Pline")) {
                                List<String> templist = new ArrayList<>();
                                int pointNum = Integer.valueOf(splits[1]);
                                for (int i = 0; i < pointNum; i++) {
                                    String tempPoint = br.readLine();
                                    String[] pointSplits = tempPoint.split(" ");
                                    templist.add(pointSplits[0] + "," + pointSplits[1]);
                                }
                                geomList.add(templist);
                            }
                        }
                    } else if (fileName.toLowerCase().matches(".*\\.mid")) {
                        String temp = null;
                        while ((temp = br.readLine()) != null) {
                            linkInfoList.add(temp);
                        }
                    }
                }
                for (int i = 0; i < linkInfoList.size(); i++) {
                    String[] splits = linkInfoList.get(i).split("\",\"");
                    String linkid = splits[1];
                    String kind = splits[3].substring(1,2);
                    // 删掉8级以下道路，包括09：非机动车通行道路；0a：航线、轮渡；0b：行人道路
                    if(kind.equals("9") || kind.equals("a") || kind.equals("b") || kind.equals("c")) {
                        continue;
                    }
                    int direction = Integer.valueOf(splits[5]);
                    // Link长度  单位：千米
                    float length = Float.valueOf(splits[12]);
                    String name = "未知道路";
                    if (splits.length > 44 && StringUtils.isNotBlank(splits[44])) {
                        name = splits[44];
                    }
                    List<String> geom = geomList.get(i);
                    // 基于Link方向构造Link加入Map
                    if(direction == 0 || direction == 1) {// 双向通行需构造两条Link
                        BaseLink link = new BaseLink(linkid, name, kind, length, direction, lonlat2GeoJson(geom,false));
                        list.add(link);
                        // 双向通行的反向Link其ID前加负号
                        String reverseLinkID = "-" + linkid;
                        // 反向Link方向与画线方向相反，因此其形状点序列要反转
                        BaseLink link1 = new BaseLink(reverseLinkID, name, kind, length, direction, lonlat2GeoJson(geom,true));
                        list.add(link1);
                    } else if(direction == 2) {
                        BaseLink link = new BaseLink(linkid, name, kind, length, direction, lonlat2GeoJson(geom,false));
                        list.add(link);
                    } else if(direction == 3) {
                        // 方向值为3，其Link方向与画线方向相反，因此其形状点序列要反转
                        BaseLink link = new BaseLink(linkid, name, kind, length, direction, lonlat2GeoJson(geom,true));
                        list.add(link);
                    }
                    if (list.size() >= 3000) {
                        baseDataService.batchSaveLink(list,city);
                        list = new ArrayList<>();
                    }
                }
                baseDataService.batchSaveLink(list,city);
                br.close();
                zs.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "fail"+e.getMessage();
            }
        }
        System.out.println(System.currentTimeMillis()-t1);
        return "success，耗时"+(System.currentTimeMillis()-t1)/1000+"秒";
    }

    //将点转成geojson,是否需要间点反转
    private String lonlat2GeoJson(List<String> list,boolean isReverse){
        StringBuffer sb = new StringBuffer();
        sb.append("{\"type\":\"LineString\",\"coordinates\":[");
        for(int i=0;i<list.size();i++) {
            String s = list.get(i);
            if(isReverse) {
               s = list.get(list.size() - i - 1);
            }
            sb.append("["+s+"],");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]}");
        return sb.toString();
    }
}
