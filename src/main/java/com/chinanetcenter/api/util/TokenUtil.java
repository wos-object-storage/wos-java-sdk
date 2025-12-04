package com.chinanetcenter.api.util;

import com.chinanetcenter.api.entity.PutPolicy;

/**
 * Token utility class
 * Created by zouhao on 14-5-16.
 */
public class TokenUtil {


    public static void main(String[] args) {
        PutPolicy putPolicy = new PutPolicy();
        putPolicy.setScope("viptest:moteltest001.mp4");
        Long time = DateUtil.parseDate("2050-01-01 12:00:00", DateUtil.COMMON_PATTERN).getTime();
        putPolicy.setDeadline(String.valueOf(time));
        String uploadToken = TokenUtil.getUploadToken(putPolicy);
        System.out.println(uploadToken);
    }

    /**
     * Get upload token
     *
     * @param putPolicy Upload policy object
     * @return Upload token string
     */
    public static String getUploadToken(PutPolicy putPolicy) {
        JsonMapper jsonMapper = JsonMapper.nonEmptyMapper();
        String putPolicyStr = jsonMapper.toJson(putPolicy);
        String encodePutPolicy = EncodeUtils.urlsafeEncode(putPolicyStr);
        String singSk = EncryptUtil.encrypt(encodePutPolicy.getBytes(), Config.SK);// Sign
        String skValue = EncodeUtils.urlsafeEncode(singSk);// Base64 encode
        String uploadToken = Config.AK + ":" + skValue + ":" + encodePutPolicy;
        return uploadToken;
    }


    /**
     * Get delete token
     *
     * @param bucketName Bucket name
     * @param fileName File name
     * @return Delete token string
     */
    public static String getDeleteToken(String bucketName, String fileName) {
        String encodedEntryURI = EncodeUtils.urlsafeEncodeString((bucketName + ":" + fileName).getBytes());
        String encodeDeletePath = "/delete/" + encodedEntryURI + "\n";
        String signSk = EncryptUtil.encrypt(encodeDeletePath.getBytes(), Config.SK);// Sign
        String encodedSign = EncodeUtils.urlsafeEncode(signSk);// Base64 encode
        String deleteToken = Config.AK + ":" + encodedSign;
        return deleteToken;
    }

    /**
     * Get prefix fuzzy delete token
     *
     * @param bucketName Bucket name
     * @param fileName File name with prefix
     * @return Prefix delete token string
     */
    public static String getDeletePrefixToken(String bucketName, String fileName) {
        String encodedEntryURI = EncodeUtils.urlsafeEncodeString((bucketName + ":" + fileName).getBytes());
        String encodeDeletePath = "/deletePrefix/" + encodedEntryURI + "\n";
        String signSk = EncryptUtil.encrypt(encodeDeletePath.getBytes(), Config.SK);// Sign
        String encodedSign = EncodeUtils.urlsafeEncode(signSk);// Base64 encode
        String deleteToken = Config.AK + ":" + encodedSign;
        return deleteToken;
    }

    /**
     * Get file information token
     *
     * @param bucketName Bucket name
     * @param fileName File name
     * @return File information token string
     */
    public static String getStatToken(String bucketName, String fileName) {
        String encodedEntryURI = EncodeUtils.urlsafeEncodeString((bucketName + ":" + fileName).getBytes());
        String encodeDeletePath = "/stat/" + encodedEntryURI + "\n";
        String signSk = EncryptUtil.encrypt(encodeDeletePath.getBytes(), Config.SK);// Sign
        String encodedSign = EncodeUtils.urlsafeEncode(signSk);// Base64 encode
        String deleteToken = Config.AK + ":" + encodedSign;
        return deleteToken;
    }

    public static String getFileListToken(String listUrl) {
        listUrl += "\n";
        String encodeDownloadUrl = EncryptUtil.encrypt(listUrl.getBytes(), Config.SK);// Sign
        String skValues = EncodeUtils.urlsafeEncode(encodeDownloadUrl);// Base64 encode
        String listToken = Config.AK + ":" + skValues;
        return listToken;
    }

}
