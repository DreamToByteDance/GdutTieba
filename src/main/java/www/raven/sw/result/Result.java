package www.raven.sw.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * common result
 *
 * @author 刘家辉
 * @date 2023/11/21
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> {

	/**
	 * 是否成功
	 */
	boolean isSuccess;
	/**
	 * SUCCESS_CODE = 200; FAIL_CODE = 400;
	 */
	private Integer code;
	/**
	 * 信息
	 */
	private String message;
	/**
	 * 数据
	 */
	private T data;

	private Result(Integer code, boolean isSuccess, String message) {
		this.code = code;
		this.isSuccess = isSuccess;
		this.message = message;
	}

	private Result(Integer code, boolean isSuccess, String message,
	               T data) {
		this.code = code;
		this.isSuccess = isSuccess;
		this.message = message;
		this.data = data;
	}

	public static Result<Void> operateFailure(String message) {
		return new Result<>(ResultCode.FAIL_CODE, false, message);
	}

	public static Result<Void> operateSuccess(String message) {
		return new Result<>(ResultCode.SUCCESS_CODE, true, message);
	}

	public static <T> Result<T> operateSuccess(String message, T data) {
		return new Result<>(
				ResultCode.SUCCESS_CODE,
				true,
				message,
				data
		);
	}

	public static <T> Result<T> operateFailure(String message, T data) {
		return new Result<>(
				ResultCode.FAIL_CODE,
				false,
				message,
				data
		);
	}

	public static <T> Result<T> operateFailure(Integer code,
	                                           String message) {
		return new Result<>(
				code,
				false,
				message
		);
	}

	public static <T> Result<T> operateFailure(Integer code,
	                                           String message, T data) {
		return new Result<>(
				code,
				false,
				message,
				data
		);
	}
}
