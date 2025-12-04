package com.chinanetcenter.api.emum;

/**
 * Enumeration class for encryption algorithm types
 */
public enum EncryptionType {
    /**
     * SHA1 algorithm
     */
    SHA1("SHA1", "SHA-1"),
    
    /**
     * SM3 national encryption algorithm
     */
    SM3("SM3", "SM3");

    private final String code;
    private final String headerValue;

    EncryptionType(String code, String headerValue) {
        this.code = code;
        this.headerValue = headerValue;
    }

    /**
     * Get encryption algorithm code
     * @return Encryption algorithm code
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取HTTP请求头中使用的值
     * @return HTTP请求头中的值
     */
    public String getHeaderValue() {
        return headerValue;
    }

    /**
     * 通过代码获取枚举类型
     * @param code 代码
     * @return 枚举类型
     */
    public static EncryptionType fromCode(String code) {
        for (EncryptionType type : EncryptionType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return SHA1; // Default return SHA1
    }
} 