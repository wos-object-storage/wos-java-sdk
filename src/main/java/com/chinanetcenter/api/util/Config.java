package com.chinanetcenter.api.util;

import com.chinanetcenter.api.emum.EncryptionType;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Configuration class for user's AK and SK information<br>
 *
 * @author zouhao
 * @version 1.0
 * @since 2014/03/02
 */
public class Config {
    public final static String VERSION_NO = "wcs-java-sdk-2.0.11";
    /**
     * Please obtain the specific AK/SK information from the WCS Web application (Account Management - Key Management)
     */
    public static String AK = "your-ak";
    public static String SK = "your-sk";
    /**
     * You can obtain uploadDomain and MgrDomain from the User Management Interface - Security Management - Domain Name Query
     */
    public static String PUT_URL = "your uploadDomain";
    public static String MGR_URL = "your MgrDomain";
    /**
     * Use the bound domain name for download GET_URL
     */
    public static String GET_URL = "your downloadDomain";
    public static String LOCAL_IP = "127.0.0.1";
    public static String LOG_FILE_PATH = "";

    /**
     * Request connection timeout in milliseconds
     */
    public static int CONNECTION_TIME_OUT = 30000;
    /**
     * Data transmission timeout in milliseconds
     */
    public static int SOCKET_TIME_OUT = 60000;
    /**
     * Number of request retries
     */
    public static int REQUEST_RETRY_TIMES = 3;
    /**
     * Traffic limit in kb/s, 0 means no limit
     */
    public static int TRAFFIC_LIMIT = 0;

    /**
     * Encryption algorithm type, default is SHA1
     * Supported values: SHA1, SM3
     */
    public static EncryptionType ENCRYPTION_TYPE = EncryptionType.SHA1;

    /**
     * Prevent external direct instantiation<br>
     */
    private Config() {
        try {
            LOCAL_IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOCAL_IP = "127.0.0.1";
        }
    }

    /**
     * Initialize the secret key<br>
     *
     * @param ak AK information for the space
     * @param sk SK information for the space
     */
    public static void init(String ak, String sk) {
        AK = ak;
        SK = sk;
    }

    /**
     * Initialize the secret key<br>
     *
     * @param ak AK information for the space
     * @param sk SK information for the space
     * @param logFilePath Path for HTTP request logs
     */
    public static void init(String ak, String sk, String logFilePath) {
        AK = ak;
        SK = sk;
        LOG_FILE_PATH = logFilePath;
    }

    /**
     * Initialize secret key<br>
     *
     * @param ak AK information for the space
     * @param sk SK information for the space
     */
    public static void init(String ak, String sk, String putUrl, String getUrl) {
        AK = ak;
        SK = sk;
        PUT_URL = putUrl;
        GET_URL = getUrl;
    }

    /**
     * Initialize secret key<br>
     *
     * @param ak AK information for the space
     * @param sk SK information for the space
     */
    public static void init(String ak, String sk, String putUrl, String getUrl, String mgrUrl) {
        AK = ak;
        SK = sk;
        PUT_URL = putUrl;
        GET_URL = getUrl;
        MGR_URL = mgrUrl;
    }

    /**
     * Initialize secret key<br>
     *
     * @param ak          AK information for the space
     * @param sk          SK information for the space
     * @param logFilePath Path for HTTP request logs
     */
    public static void init(String ak, String sk, String putUrl, String getUrl, String mgrUrl, String logFilePath) {
        AK = ak;
        SK = sk;
        PUT_URL = putUrl;
        GET_URL = getUrl;
        MGR_URL = mgrUrl;
        LOG_FILE_PATH = logFilePath;
    }

    /**
     *
     * @param ak          AK information for the space
     * @param sk          SK information for the space
     * @param logFilePath Path for HTTP request logs
     * @param connectionTimeOut Request timeout time
     * @param socketTimeOut Data transmission timeout time
     */
    public static void init(String ak, String sk, String putUrl, String getUrl, String mgrUrl, String logFilePath,
                            int connectionTimeOut, int socketTimeOut) {
        AK = ak;
        SK = sk;
        PUT_URL = putUrl;
        GET_URL = getUrl;
        MGR_URL = mgrUrl;
        LOG_FILE_PATH = logFilePath;
        CONNECTION_TIME_OUT = connectionTimeOut;
        SOCKET_TIME_OUT = socketTimeOut;
    }

    /**
     *
     * @param ak          AK information for the space
     * @param sk          SK information for the space
     * @param logFilePath Path for HTTP request logs
     * @param connectionTimeOut Request timeout time
     * @param socketTimeOut Data transmission timeout time
     * @param requestRetryTimes Number of retries
     */
    public static void init(String ak, String sk, String putUrl, String getUrl, String mgrUrl, String logFilePath,
                            int connectionTimeOut, int socketTimeOut, int requestRetryTimes) {
        AK = ak;
        SK = sk;
        PUT_URL = putUrl;
        GET_URL = getUrl;
        MGR_URL = mgrUrl;
        LOG_FILE_PATH = logFilePath;
        CONNECTION_TIME_OUT = connectionTimeOut;
        SOCKET_TIME_OUT = socketTimeOut;
        REQUEST_RETRY_TIMES = requestRetryTimes;
    }

    /**
     *
     * @param ak          AK information for the space
     * @param sk          SK information for the space
     * @param logFilePath Path for HTTP request logs
     * @param connectionTimeOut Request timeout time
     * @param socketTimeOut Data transmission timeout time
     * @param requestRetryTimes Number of retries
     * @param trafficLimit Traffic limit
     */
    public static void init(String ak, String sk, String putUrl, String getUrl, String mgrUrl, String logFilePath,
                            int connectionTimeOut, int socketTimeOut, int requestRetryTimes, int trafficLimit) {
        AK = ak;
        SK = sk;
        PUT_URL = putUrl;
        GET_URL = getUrl;
        MGR_URL = mgrUrl;
        LOG_FILE_PATH = logFilePath;
        CONNECTION_TIME_OUT = connectionTimeOut;
        SOCKET_TIME_OUT = socketTimeOut;
        REQUEST_RETRY_TIMES = requestRetryTimes;
        TRAFFIC_LIMIT = trafficLimit;
    }
}
