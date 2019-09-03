package com.cloud.common.exception.handler;

import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.CashLoadSystemEnum;
import com.cloud.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 系统全局异常统一处理
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    /**
     * POST 提交form 数据校验异常 - 400
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({BindException.class, ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.OK)
    public JsonResult handleBindException(BindException exception) {
        log.error("参数验证失败：", exception);
        return JsonResult.build(CashLoadSystemEnum.BAD_REQUEST.code, CashLoadSystemEnum.BAD_REQUEST.message, null);
    }

    /**
     * 由于部分业务代码中有使用到这个异常，并且异常信息各不相同
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult badRequestException(IllegalArgumentException exception) {
        log.warn("参数校验失败: {}", exception.getLocalizedMessage());
        return JsonResult.build(CashLoadSystemEnum.BAD_REQUEST.code, exception.getMessage(), null);
    }

    /**
     * 不支持当前请求方法 - 405
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.error("不支持当前请求方法", exception);
        return JsonResult.build(CashLoadSystemEnum.METHOD_NOT_ALLOWED.code, CashLoadSystemEnum.METHOD_NOT_ALLOWED.message, null);
    }

    /**
     * Content-Type 类型错误 - 415
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        log.error("不支持当前 Content-Type 类型：", exception);
        return JsonResult.build(CashLoadSystemEnum.UNSUPPORTED_MEDIA_TYPE.code, CashLoadSystemEnum.UNSUPPORTED_MEDIA_TYPE.message, null);
    }

    /**
     * 目前还有部分业务代码中使用 - 替换成businessException后删除
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.OK)
    public JsonResult exception(RuntimeException exception) {
        log.error("发生异常：{}", exception);
        return JsonResult.errorException(exception.getMessage());
    }

    /**
     * 自定义业务场景异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult handleBusinessException(BusinessException exception) {
        if (exception.getStatus()==null){
            log.error("系统业务异常：code= {}, message= {}", 1004, exception.getMessage());
            //统一业务异常code 为1 提示语已代码传入的异常信息为提示
            return JsonResult.build(1004,exception.getMessage(),null);
        }else {
            log.error("系统业务异常：code= {}, message= {}", exception.getStatus().getCode(), exception.getStatus().getMessage());
        }
        return JsonResult.build(exception.getStatus().getCode(), exception.getStatus().getMessage(), null);
    }

    /**
     * 服务器运行异常 - 500
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult handleException(Exception exception) {
        log.error("服务器运行异常: ", exception);
        return JsonResult.errorMsg("Server running abnormal");
    }

    /**
     * 无访问权限异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonResult accessDeniedException(AccessDeniedException exception) {
        log.warn("无访问权限异常：{}", exception);
        return JsonResult.build(CashLoadSystemEnum.ACCESS_DENIED.getCode(), CashLoadSystemEnum.ACCESS_DENIED.getMessage(), null);
    }
    //
    // @ExceptionHandler({ClientException.class, Throwable.class})
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public Map<String, Object> serverException(Throwable throwable) {
    //     log.error("服务端异常", throwable);
    //     Map<String, Object> data = new HashMap<>(16);
    //     data.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
    //     data.put("msg", "服务端异常，请联系管理员");
    //
    //     return data;
    // }

    // @ExceptionHandler({LoginCountException.class})
    // public Map<String, Object> loginCountException(LoginCountException exception) {
    //     Map<String, Object> data = new HashMap<>(16);
    //     data.put("code", exception.getStatus());
    //     data.put("msg", exception.getMessage());
    //     return data;
    // }
}
