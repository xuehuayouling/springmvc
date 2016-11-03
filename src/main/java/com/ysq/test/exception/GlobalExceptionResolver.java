package com.ysq.test.exception;

import com.ysq.test.other.DescribableEnum;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ysq on 16/10/28.
 */
public class GlobalExceptionResolver extends AbstractHandlerExceptionResolver {
    private HttpMessageConverter<Object> jsonMessageConverter;

    public void setJsonMessageConverter(HttpMessageConverter<Object> jsonMessageConverter) {
        this.jsonMessageConverter = jsonMessageConverter;
    }

    public HttpMessageConverter<Object> getJsonMessageConverter() {
        return jsonMessageConverter;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ex.printStackTrace();
        HttpMessageConverter<Object> messageConverter = getJsonMessageConverter();
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        outputMessage.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        outputMessage.getHeaders().add("Cache-Control", "no-cache");
        Map<String, Object> error = new HashMap<String, Object>();
        error.put("statusCode", DescribableEnum.SYSTEM_ERROR.getCode());
        error.put("message", DescribableEnum.SYSTEM_ERROR.getMsg() + "[请与管理员联系!]");
        try {
            messageConverter.write(error, MediaType.APPLICATION_JSON, outputMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
