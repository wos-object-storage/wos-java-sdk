package com.chinanetcenter.api.demo;

import com.chinanetcenter.api.entity.HttpClientResult;
import com.chinanetcenter.api.exception.WsClientException;
import com.chinanetcenter.api.util.Config;
import com.chinanetcenter.api.wsbox.WsliveFileManage;

/**
 * Purpose description: Resource management - Set file retention period
 * Created by chenql on 2018/4/3.
 */
public class SetdeadlineDemo {

    public static void main(String[] args) {
        Config.AK = "your-ak";
        Config.SK = "your-sk";
        /**
         * You can get uploadDomain and MgrDomain from User Management Interface - Security Management - Domain Name Query
         */
        Config.MGR_URL = "your MgrDomain";
        String bucketName = "your-bucket";
        String fileKey = "java-sdk/testfile.jpg";
        int deleteAfterDays = 30;// Represents expiration after 30 days
        try {
            HttpClientResult result = WsliveFileManage.setDeadline(bucketName, fileKey, deleteAfterDays);
            System.out.println(result.getStatus() + ":" + result.getResponse());
        } catch (WsClientException e) {
            e.printStackTrace();
        }
    }
}
