package com.chinanetcenter.api.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * File hash/etag utility
 * Created by xiexb on 2014/5/30.
 */
public class WetagUtil {
    private static final int BLOCK_BITS = 22;
    private static final int BLOCK_SIZE = 1 << BLOCK_BITS;//2^22 = 4M
    private static final byte BYTE_LOW_4 = 0x16;// Files <= 4M prepend a single byte with value 0x16
    private static final byte BYTE_OVER_4 = (byte) 0x96;// Files > 4M prepend a single byte with value 0x96

    /**
     * Calculate file block count, 4M per block
     *
     * @param fileLength
     * @return
     */
    private static long blockCount(long fileLength) {
        return ((fileLength + (BLOCK_SIZE - 1)) >> BLOCK_BITS);
    }

    /**
     * Read specified file block data Sha1
     *
     * @param fis
     * @return
     */
    private static MessageDigest calSha1(BufferedInputStream fis) {
        MessageDigest sha1 = null;
        try {
            byte[] buffer = new byte[1024];
            int numRead = 0;
            int total = 0;
            sha1 = MessageDigest.getInstance("SHA-1");
            while ((numRead = fis.read(buffer)) > 0) {
                sha1.update(buffer, 0, numRead);
                total += numRead;
                if (total >= BLOCK_SIZE) {// Read at most 4M each time
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha1;
    }

    /**
     * Get hash/etag, calculate hash value based on File
     *
     * @param file File
     * @return
     */
    public static String getEtagHash(File file) {
        String etagHash = null;
        BufferedInputStream fis = null;
        try {
            if (file.exists()) {
                byte[] ret = new byte[21];
                long blockCount = blockCount(file.length());
                fis = new BufferedInputStream(new FileInputStream(file));
                if (blockCount <= 1) { // File has 1 or fewer blocks
                    MessageDigest sha1 = calSha1(fis);
                    if (null != sha1) {
                        byte[] input = sha1.digest();
                        ret[0] = BYTE_LOW_4;
                        for (int i = 0; i < 20; ++i) {// SHA1 algorithm is 20 bytes
                            ret[i + 1] = input[i];
                        }
                    }
                } else {// Concatenate all sha1 values in block order
                    byte[] rec = new byte[(int) blockCount * 20];
                    ret[0] = BYTE_OVER_4;
                    int i, cnt = 0;
                    for (i = 0; i < blockCount; i++) {// Calculate sha1 for each block
                        MessageDigest sha1 = calSha1(fis);
                        if (null != sha1) {
                            byte[] tmp = sha1.digest();
                            for (int j = 0; j < 20; j++) {
                                rec[cnt++] = tmp[j];
                            }
                        }
                    }
                    MessageDigest sha1 = MessageDigest.getInstance("SHA-1");// Calculate sha1 again on concatenated data
                    sha1.update(rec, 0, (int) blockCount * 20);
                    byte[] tmp = sha1.digest();
                    for (i = 0; i < 20; ++i) {// Prepend a single byte with value 0x96
                        ret[i + 1] = tmp[i];
                    }
                }
                etagHash = EncodeUtils.urlsafeEncodeString(ret);
            } else {
                System.out.println("File[" + file.getAbsolutePath() + "] Not Exist,Cannot Calculate Hash!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return etagHash;
    }

    /**
     * Get hash/etag, used to calculate hash value for non-chunked local files
     *
     * @param filePath File physical path
     * @param fileName File name
     * @return
     */
    public static String getEtagHash(String filePath, String fileName) {
        File f = new File(filePath, fileName);
        return getEtagHash(f);
    }

    /**
     * Get hash/etag, used to calculate hash value for non-chunked files
     *
     * @param fileInputStream File input stream
     * @param fileLength      File size
     * @return
     */
    public static String getEtagHash(InputStream fileInputStream, long fileLength) {
        String etagHash = null;
        BufferedInputStream fis = null;
        try {
            byte[] ret = new byte[21];
            long blockCount = blockCount(fileLength);
            fis = new BufferedInputStream(fileInputStream);
            if (blockCount <= 1) { // File has 1 or fewer blocks
                MessageDigest sha1 = calSha1(fis);
                if (null != sha1) {
                    byte[] input = sha1.digest();
                    ret[0] = BYTE_LOW_4;
                    for (int i = 0; i < 20; ++i) {// SHA1 algorithm is 20 bytes
                        ret[i + 1] = input[i];
                    }
                }
            } else {// Concatenate all sha1 values in block order
                byte[] rec = new byte[(int) blockCount * 20];
                ret[0] = BYTE_OVER_4;
                int i, cnt = 0;
                for (i = 0; i < blockCount; i++) {// Calculate sha1 for each block
                    MessageDigest sha1 = calSha1(fis);
                    if (null != sha1) {
                        byte[] tmp = sha1.digest();
                        for (int j = 0; j < 20; j++) {
                            rec[cnt++] = tmp[j];
                        }
                    }
                }
                MessageDigest sha1 = MessageDigest.getInstance("SHA-1");// Calculate sha1 again on concatenated data
                sha1.update(rec, 0, (int) blockCount * 20);
                byte[] tmp = sha1.digest();
                for (i = 0; i < 20; ++i) {// Prepend a single byte with value 0x96
                    ret[i + 1] = tmp[i];
                }
            }
            etagHash = EncodeUtils.urlsafeEncodeString(ret);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return etagHash;
    }

    /**
     * Calculate Sha1 for specified data
     *
     * @param data byte array
     * @return
     */
    private static MessageDigest calSha1(byte[] data) {
        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
            sha1.update(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha1;
    }

    /**
     * Get hash/etag, used to calculate hash value for already chunked files
     *
     * @param data File's byte array
     * @return
     */
    public static String getEtagHash(byte[] data) {
        String etagHash = null;
        try {
            byte[] ret = new byte[21];
            MessageDigest sha1 = calSha1(data);
            if (null != sha1) {
                byte[] input = sha1.digest();
                ret[0] = BYTE_LOW_4;
                for (int i = 0; i < 20; ++i) {// SHA1 algorithm is 20 bytes
                    ret[i + 1] = input[i];
                }
            }
            etagHash = EncodeUtils.urlsafeEncodeString(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return etagHash;
    }

    public static long crc32(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    public static String getFileMD5String(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            return toHex(md.digest());
        } catch (IllegalStateException e) {
            return null;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String toHex(byte buffer[]) {
        StringBuilder sb = new StringBuilder();
        String s;
        for (byte aBuffer : buffer) {
            s = Integer.toHexString((int) aBuffer & 0xff);
            if (s.length() < 2) {
                sb.append('0');
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
