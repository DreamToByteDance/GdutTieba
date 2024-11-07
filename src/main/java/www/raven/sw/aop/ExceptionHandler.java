package www.raven.sw.aop;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import www.raven.sw.exception.BizException;
import www.raven.sw.result.Result;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 刘家辉
 * @date 2023/10/29
 */
@ControllerAdvice
@Component
@Slf4j
public class ExceptionHandler {

	/**
	 * bind exception handler 处理 form data方式调用接口校验失败抛出的异常
	 *
	 * @param e e
	 * @return {@link Result}<{@link Void}>
	 */
	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
	public Result<Void> bindExceptionHandler(BindException e) {
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		List<String> collect = fieldErrors.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
		return Result.operateFailure("请求参数异常：" + String.join(",", collect));
	}

	/**
	 * method argument not valid exception handler
	 * <2> 处理 json 请求体调用接口校验失败抛出的异常
	 *
	 * @param e e
	 * @return {@link Result}<{@link Void}>
	 */
	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public Result<Void> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException e) {
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		List<String> collect = fieldErrors.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
		return Result.operateFailure("请求参数异常：" + String.join(",", collect));
	}

	/**
	 * constraint violation exception handler
	 * <3> 处理单个参数校验失败抛出的异常
	 *
	 * @param e e
	 * @return {@link Result}<{@link Void}>
	 */
	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
	public Result<Void> constraintViolationExceptionHandler(
			ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		List<String> collect = constraintViolations.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		return Result.operateFailure("请求参数异常：" + String.join(",", collect));
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
	public Result<Void> handlerNullPointerException(
			NullPointerException ex) {
		log.error("空指针异常", ex);
		return Result.operateFailure(ex.getMessage());
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
	public Result<Void> handlerIllegalArgumentException(
			IllegalArgumentException ex) {
		return Result.operateFailure(ex.getLocalizedMessage());
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
	public Result<Void> handlerRuntimeException(RuntimeException ex) {
		log.error("运行时异常", ex);
		return Result.operateFailure(ex.getMessage());
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public Result<Void> handlerException(Exception ex) {
		log.error("未知异常", ex);
		return Result.operateFailure(ex.getLocalizedMessage());
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public Result<Void> handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
		log.error("数据重复!请确保使用的数据并未入库", ex);
		return Result.operateFailure(ex.getLocalizedMessage());
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(BizException.class)
	public Result<Void> handlerBizException(BizException ex) {
		return Result.operateFailure(ex.getMessage());
	}
}