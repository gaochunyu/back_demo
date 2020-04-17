package com.cennavi.tp.common.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cennavi.tp.common.result.FastJsonExcludesAndIncludes;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * 处理接口返回的json信息
 */
@Configuration
public class CustomFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
    @Override
    public void write(Object t, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders headers = outputMessage.getHeaders();
        if (headers.getContentType() == null) {
            if (contentType == null || contentType.isWildcardType() || contentType.isWildcardSubtype()) {
                contentType = getDefaultContentType(t);
            }
            if (contentType != null) {
                headers.setContentType(contentType);
            }
        }
        if (headers.getContentLength() == -1) {
            Long contentLength = getContentLength(t, headers.getContentType());
            if (contentLength != null) {
                headers.setContentLength(contentLength);
            }
        }
        writeInternal(t, outputMessage);
        outputMessage.getBody().flush();
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        HttpHeaders headers = outputMessage.getHeaders();
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();

        SimplePropertyPreFilter excludesFilter = setExcludes(obj);
        int length = getFastJsonConfig().getSerializeFilters().length;
        SerializeFilter[] filters = new SerializeFilter[length + 1];
        System.arraycopy(getFastJsonConfig().getSerializeFilters(), 0, filters, 0, length);
        filters[filters.length - 1] = excludesFilter;

        int len = JSON.writeJSONString(outnew, //
                getFastJsonConfig().getCharset(), //
                obj, //
                getFastJsonConfig().getSerializeConfig(), //
                filters, //
                getFastJsonConfig().getDateFormat(), //
                JSON.DEFAULT_GENERATE_FEATURE, //
                getFastJsonConfig().getSerializerFeatures());
        headers.setContentLength(len);
        OutputStream out = outputMessage.getBody();
        outnew.writeTo(out);
        outnew.close();
    }

    private SimplePropertyPreFilter setExcludes(Object obj) {
        SimplePropertyPreFilter serializeFilter = new SimplePropertyPreFilter();

        if (!(obj instanceof FastJsonExcludesAndIncludes)) {
            return serializeFilter;
        }

        serializeFilter.getExcludes().add("excludes");
        serializeFilter.getExcludes().add("includes");
        FastJsonExcludesAndIncludes fastJsonExcludesAndIncludes = (FastJsonExcludesAndIncludes) obj;
        Set<String> excludes = fastJsonExcludesAndIncludes.getExcludes();
        if (excludes != null && !excludes.isEmpty()) {
            serializeFilter.getExcludes().addAll(excludes);
        }

        Set<String> includes = fastJsonExcludesAndIncludes.getIncludes();
        if (includes != null && !includes.isEmpty()) {
            serializeFilter.getIncludes().addAll(includes);
        } else {
            serializeFilter.getIncludes().clear();
        }


        return serializeFilter;
    }
}
