package com.chinanetcenter.api.demo;

import com.chinanetcenter.api.entity.HttpClientResult;
import com.chinanetcenter.api.exception.WsClientException;
import com.chinanetcenter.api.util.Config;
import com.chinanetcenter.api.util.EncodeUtils;
import com.chinanetcenter.api.wsbox.OperationManager;

/**
 * Created by fuyz on 2016/9/1.
 * Transcoding request
 */
public class FopsDemo {
    public static void main(String[] args) {
        Config.AK = "your-ak";
        Config.SK = "your-sk";
        /**
         * You can get uploadDomain and MgrDomain from User Management Interface - Security Management - Domain Name Query
         */
        Config.MGR_URL = "your MgrDomain";
        String bucketName = "your-bucket";
        String fileKey = "java-sdk/10m2.mp4";
        // Set transcoding operation parameters
        String fops = "avthumb/mp4/s/640x360/vb/1.25m";
        // You can customize the name of the transcoded file using the saveas parameter,
        // Or you can leave it unspecified, and it will be named by default and saved in the current space. Base64 encode the target Bucket_Name:custom file key.
        String saveAsKey = EncodeUtils.urlsafeEncode(bucketName + ":1.256m.jpg");
        fops += "|saveas/" + saveAsKey;
        String notifyURL = "http://demo1/notifyUrl";  // Notification URL, will be called back after transcoding is successful
        String force = "1";
        String separate = "1";
        FopsDemo demo = new FopsDemo();
        demo.fileTrans(bucketName,fileKey,fops,notifyURL,force,separate);

    }

    public void fileTrans(String bucketName, String fileKey, String fops, String notifyURL, String force,String separate) {
        OperationManager fileManageCommand = new OperationManager();
        try {
            HttpClientResult result = fileManageCommand.fops(bucketName, fileKey, fops, notifyURL,force,separate);
            System.out.println(result.getStatus() + ":" + result.getResponse());
        } catch (WsClientException e) {
            e.printStackTrace();
        }
    }
}
