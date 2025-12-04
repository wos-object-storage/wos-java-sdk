package com.chinanetcenter.api.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuyz on 2016/9/2.
 * Fmgr parameter object
 */
public class FmgrParam {
    private String fetchURL;
    private String bucket;
    private String fileKey;
    private String prefix;
    private String md5;
    private String resource;
    private String output;
    private String deletets;//Specify whether to delete associated ts files. 0: no associated deletion, 1: delete associated ts files
    //Deprecated, please use deleteAfterDays. File retention period. Files are automatically deleted after the retention period expires, unit: days. For example: 1, 2, 3... Note: 0 means delete as soon as possible, -1 means cancel expiration time, permanent storage
    @Deprecated
    private int deadline = -1;
    /**
     * File retention period. Files are automatically deleted after the retention period expires, unit: days. For example: 1, 2, 3... Note: 0 means delete as soon as possible, -1 means cancel expiration time, permanent storage
     */
    private int deleteAfterDays = -1;;
    /**
     * Used to store later extended parameters, key and value must be filled according to the format in the document center
     */
    private Map<String, String> paramMap = new HashMap<String, String>();

    public String getFetchURL() {
        return fetchURL;
    }

    public void setFetchURL(String fetchURL) {
        this.fetchURL = fetchURL;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getDeletets() {
        return deletets;
    }

    public void setDeletets(String deletets) {
        this.deletets = deletets;
    }

    public int getDeadline() {
        return deleteAfterDays;
    }

    public void setDeadline(int deadline) {
        this.deleteAfterDays = deadline;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public void putExtParams(String key, String value) {
        this.paramMap.put(key, value);
    }


    public int getDeleteAfterDays() {
        return deleteAfterDays;
    }

    public void setDeleteAfterDays(int deleteAfterDays) {
        this.deleteAfterDays = deleteAfterDays;
    }
}
