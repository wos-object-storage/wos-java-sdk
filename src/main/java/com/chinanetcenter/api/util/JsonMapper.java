/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.chinanetcenter.api.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Simple encapsulation of Jackson, implementing JSON String <-> Java Object Mapper.
 * <p>
 * Encapsulates different output styles, creating instances using different builder functions.
 *
 * @author calvin
 */
public class JsonMapper {

    private ObjectMapper mapper;

    public JsonMapper() {
        this(null);
    }

    public JsonMapper(Include include) {
        mapper = new ObjectMapper();
        // Set the style of including properties during output
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // Set to ignore properties that exist in JSON string but not in Java object during input
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * Creates a Mapper that only outputs non-Null and non-Empty (such as List.isEmpty) properties to Json string, recommended for external interfaces.
     */
    public static JsonMapper nonEmptyMapper() {
        return new JsonMapper(Include.NON_EMPTY);
    }

    /**
     * Creates a Mapper that only outputs properties with changed initial values to Json string, the most storage-efficient way, recommended for internal interfaces.
     */
    public static JsonMapper nonDefaultMapper() {
        return new JsonMapper(Include.NON_DEFAULT);
    }

    /**
     * Object can be POJO, Collection or array. If the object is Null, returns "null". If the collection is empty, returns "[]".
     */
    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deserializes POJO or simple Collection such as List&lt;String&gt;.
     * <p>
     * If the JSON string is Null or "null" string, returns Null. If the JSON string is "[]", returns empty collection.
     * <p>
     * For deserializing complex Collection such as List&lt;MyBean&gt;, please use fromJson(String, JavaType)
     *
     * @see #fromJson(String, com.fasterxml.jackson.databind.JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deserializes complex Collection such as List&lt;Bean&gt;,
     * first construct the type using createCollectionType() or contructMapType(), then call this function.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Constructs Collection type.
     */
    @SuppressWarnings("rawtypes")
    public JavaType contructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * Constructs Map type.
     */
    @SuppressWarnings("rawtypes")
    public JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * When JSON only contains some properties of the Bean, updates an existing Bean, only overriding those properties.
     */
    public void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
        } catch (IOException e) {
        }
    }

    /**
     * Outputs JSONP format data.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * Sets whether to use Enum's toString function to read/write Enum, when False uses Enum's name() function to read/write Enum, default is False.
     * Note that this function must be called after creating the Mapper and before all read/write operations.
     */
    public void enableEnumUseToString() {
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    /**
     * Supports using Jaxb's Annotation, so that annotations on POJO do not need to be coupled with Jackson.
     * By default, it will first look for jaxb annotations, if not found, then look for jackson annotations.
     */
    public void enableJaxbAnnotation() {
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        mapper.registerModule(module);
    }

    /**
     * Gets the Mapper for further settings or using other serialization APIs.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
