package cn.org.citycloud.handler;

import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.org.citycloud.bean.ErrorResponse;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.exception.TokenValidationException;

/**
 * 全局异常处理
 * @author Lanbo
 *
 */
@ControllerAdvice
public class ApiExceptionHandler {
	
	private static Logger logger = Logger.getLogger(ApiExceptionHandler.class);

	/**
	 * Token验证异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(TokenValidationException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponse handleTokenValidationException(TokenValidationException ex) {
		
		return new ErrorResponse(ErrorCodes.TOKEN_ERROR, ex.getMessage());
	}
	
	/**
	 * 业务异常处理
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BusinessErrorException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleBusinessErrorException(BusinessErrorException ex) {
		
		return new ErrorResponse(ex.getCode(), ex.getMessage());
	}

	/**
	 * 不合法的请求格式异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleTypeMismatchException(TypeMismatchException ex) {
		return new ErrorResponse(ErrorCodes.TYPE_MISMATCH, "不合法的请求格式");
	}

	/**
	 * 请求参数验证异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		StringBuilder sb = new StringBuilder();
		sb.append("参数错误: ");

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			sb.append(fieldError.getField());
			sb.append(fieldError.getDefaultMessage());
			sb.append(";");
		}
		return new ErrorResponse(ErrorCodes.INVALID_PARAMETER, sb.toString());
	}

	/**
	 * HTTP参数不可读
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleHttpMessageNotReadableException(
			HttpMessageNotReadableException ex) {
		return new ErrorResponse(ErrorCodes.HTTPMESSAGE_NOT_READABLE, ex.getMessage());
	}

	/**
	 * 500系统内部错误
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleUnexpectedServerError(RuntimeException ex) {
		logger.error("系统错误", ex);
		
		return new ErrorResponse(ErrorCodes.SYSTEM_ERROR, ex.getMessage());
	}
}
