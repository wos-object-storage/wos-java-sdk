package com.chinanetcenter.api.wsbox;


import com.chinanetcenter.api.entity.HttpClientResult;
import com.chinanetcenter.api.entity.PutPolicy;
import com.chinanetcenter.api.exception.WsClientException;
import com.chinanetcenter.api.http.HttpClientUtil;
import com.chinanetcenter.api.util.Config;
import com.chinanetcenter.api.util.DateUtil;
import com.chinanetcenter.api.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Upload files
 *
 * @author zouhao
 * @version 1.0
 * @since 2014/02/14
 */
public class FileUploadManage {

    public HttpClientResult upload(String bucketName, String fileKey, String srcFile) throws WsClientException {
        PutPolicy putPolicy = new PutPolicy();
        putPolicy.setScope(bucketName + ":" + fileKey);
        putPolicy.setOverwrite(1);
        putPolicy.setDeadline(String.valueOf(DateUtil.nextHours(1, new Date()).getTime()));
        return upload(bucketName, fileKey, srcFile, putPolicy);
    }

    public HttpClientResult upload(String bucketName, String fileKey, InputStream in) throws WsClientException {
        PutPolicy putPolicy = new PutPolicy();
        putPolicy.setScope(bucketName + ":" + fileKey);
        putPolicy.setOverwrite(1);
        putPolicy.setDeadline(String.valueOf(DateUtil.nextHours(1, new Date()).getTime()));
        String fileName = fileKey;
        if (fileName.contains("/")) {
            fileName = StringUtils.substringAfterLast(fileName, "/");
        }
        return upload(bucketName, fileKey, fileName, in, putPolicy);
    }

    /**
     * Upload file to the specified bucket, and can set upload policy
     *
     * @param bucketName The bucket name where the file will be saved
     * @param fileKey    The file name to save in the bucket
     * @param srcFile    Local path of the file to upload
     * @param putPolicy  Upload policy  Upload policy data is a set of configuration settings attached when uploading resources.
     *                   Through this set of configuration information, users can customize specific upload requirements. It will determine what resource to upload, which bucket to upload to,
     *                   whether the upload result is a callback notification or a redirect jump, whether to set the content of feedback information, and the deadline for authorized upload, etc.
     *                   Please refer to the PutPolicy entity for specific attribute descriptions
     */
    public HttpClientResult upload(String bucketName, String fileKey, String srcFile, PutPolicy putPolicy) throws WsClientException {
        if (putPolicy.getDeadline() == null) {
            putPolicy.setDeadline(String.valueOf(DateUtil.nextHours(1, new Date()).getTime()));
        }
        if (StringUtils.isEmpty(fileKey)) {
            putPolicy.setScope(bucketName);
        } else {
            putPolicy.setScope(bucketName + ":" + fileKey);
        }
        String uploadToken = TokenUtil.getUploadToken(putPolicy);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("token", uploadToken);
        return upload(paramMap,srcFile);
    }

    /**
     * Upload file to the specified bucket, and can set upload policy
     *
     * @param bucketName  The bucket name where the file will be saved
     * @param fileKey     The file name to save in the bucket
     * @param inputStream File input stream
     * @param putPolicy   Upload policy  Upload policy data is a set of configuration settings attached when uploading resources.
     *                    Through this set of configuration information, users can customize specific upload requirements. It will determine what resource to upload, which bucket to upload to,
     *                    whether the upload result is a callback notification or a redirect jump, whether to set the content of feedback information, and the deadline for authorized upload, etc.
     *                    Please refer to the PutPolicy entity for specific attribute descriptions
     */
    public HttpClientResult upload(String bucketName, String fileKey, String fileName, InputStream inputStream, PutPolicy putPolicy) throws WsClientException {
        if (putPolicy.getDeadline() == null) {
            putPolicy.setDeadline(String.valueOf(DateUtil.nextHours(1, new Date()).getTime()));
        }
        if (fileKey == null || fileKey.equals("")) {
            putPolicy.setScope(bucketName);
        } else {
            putPolicy.setScope(bucketName + ":" + fileKey);
        }
        String uploadToken = TokenUtil.getUploadToken(putPolicy);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("token", uploadToken);
        return upload(paramMap,fileName,inputStream);
    }

    public HttpClientResult upload(Map<String, String> paramMap, String fileName, InputStream inputStream) throws WsClientException {
        String url = Config.PUT_URL + "/file/upload";
        return HttpClientUtil.httpPost(url, null, paramMap, fileName, inputStream);
    }

    public HttpClientResult upload(Map<String, String> paramMap, String srcFile) throws WsClientException {
        String url = Config.PUT_URL + "/file/upload";
        File file = new File(srcFile);
        return HttpClientUtil.httpPost(url, paramMap,null, file);
    }
}
