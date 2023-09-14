package com.yupi.yupao.once;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 导入星球数据库
 */
public class ImportXingQiuUser {
    /**
     * 读取数据
     */
    public static void main(String[] args) {
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        String fileName = "D:\\projects\\xingqiu\\yupideyupao\\user-center-backend-master\\src\\main\\resources\\prodExcel.xlsx";
        List<XingQiuUserInfo> userInfoList =
                EasyExcel.read(fileName).head(XingQiuUserInfo.class).sheet().doReadSync();
        System.out.println("总数"+userInfoList.size());
        Map<String, List<XingQiuUserInfo>> listMap =
                userInfoList.stream().filter(userInfo -> StringUtils.isNotEmpty(userInfo.getUsername()))
                        .collect(Collectors.groupingBy(XingQiuUserInfo::getUsername));
        System.out.println("不重复是昵称数总数"+listMap.keySet().size());
        for(Map.Entry<String, List<XingQiuUserInfo>> list:listMap.entrySet()){
            if(list.getValue().size() >1){
                System.out.println(list.getKey()+list.getValue());
            }
        }
    }

    /**
     * 监听器读取
     *
     * @param fileName
     */
    public static void readByListener(String fileName) {
        EasyExcel.read(fileName, XingQiuUserInfo.class, new TableListener()).sheet().doRead();
    }
    /**
     * 同步读
     *
     * @param fileName
     */
    public static void synchronousRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<XingQiuUserInfo> totalDataList =
                EasyExcel.read(fileName).head(XingQiuUserInfo.class).sheet().doReadSync();
        for (XingQiuUserInfo xingQiuTableUserInfo : totalDataList) {
            System.out.println(xingQiuTableUserInfo);
        }
    }
}
