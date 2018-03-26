package com.jc.core.web;

import java.beans.PropertyEditorSupport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jc.common.utils.DateUtils;
import com.jc.common.utils.StringUtils;
import com.jc.core.beanvalidator.BeanValidators;
import com.jc.core.constants.Global;

/**
 * Description: 基础 controller类
 *
 * @author 高研
 * @date 2015年9月10日 下午5:31:37
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Validator validator;

    /**
     * Description: 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；验证失败：将错误信息添加到 message 中
     */
    protected Map<String, Object> beanValidator(Object object, Class<?>... groups) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            resultMap = BeanValidators.extractPropertyAndMessage(ex);
            resultMap.put(Global.RESULT_STATE, false);
            return resultMap;
        }
        return resultMap;
    }

    /**
     * Description: 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；验证失败：将错误信息添加到 flash message 中
     */
    protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            addMessage(redirectAttributes, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }

    /**
     * Description: 添加Model消息
     *
     * @param model
     * @param messages void
     * @throws
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * @param resultMap 添加对象
     * @param state     true表示成功，false表示失败
     * @param messages  提示信息
     * @return Map<String               ,               Object>
     * @Description: 添加返回信息
     */
    protected Map<String, Object> addMessage(Map<String, Object> resultMap, boolean state, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        resultMap.put(Global.RESULT_STATE, state);
        resultMap.put(Global.RESULT_MESSAGE, sb.toString());
        return resultMap;
    }

    /**
     * Description 初始化后台公共校验方法
     *
     * @param request 服务器请求类
     * @param binder  绑定类
     * @throws
     */
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        //去空格
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(text == null ? null : text.trim());
            }
        });
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringUtils.htmlEncode(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * Description 公共处理Exception方法
     *
     * @param e 捕获的Exception类
     * @return String 无实际意义
     * @throws
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Object baseException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();

        //shiro权限异常
        if (e.getCause() != null && e.getCause().toString().startsWith(AuthorizationException.class.getName())) {
            throw new UnauthorizedException();
        }
        //业务异常
        else {
            resultMap.put(Global.RESULT_MESSAGE, "系统错误");
            logger.error(e.getMessage(), e);
        }
        //判断是否是Ajax请求
        if (isAjaxRequest(request)) {
            resultMap.put(Global.RESULT_STATE, false);
            return resultMap;
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/system/500.error");
            } catch (IOException e1) {
                logger.error("出现异常，跳转页面错误", e1);
            }
        }
        return null;
    }

    /**
     * Description 下载文件
     *
     * @param path 路径和文件名
     *             void
     * @throws
     */
    public void sendFile(String path, HttpServletResponse response) {
        sendFile(path, response, "application/octet-stream");
    }

    /**
     * Description: 下载文件
     *
     * @param path        文件路径
     * @param response
     * @param contentType response类型
     * @throws
     */
    public void sendFile(String path, HttpServletResponse response, String contentType) {
        try {
            File file = new File(path);
            String filename = file.getName();
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            if ("application/octet-stream".equals(contentType)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            }
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType(contentType);
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description: 下载文件（字符串）
     *
     * @param fileName 文件路径
     * @param response
     * @param response 类型
     */
    public void sendFileByStr(String content, String fileName, HttpServletResponse response) {
        try {
            byte[] buffer = content.getBytes();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description 判断是否为ajax请求
     *
     * @param request 请求request
     * @return 判断结果
     */
    protected boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)) {
            return true;
        }
        return false;
    }

}
