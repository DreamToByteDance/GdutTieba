package www.raven.sw.exception;

/**
 * 自定义业务异常
 *
 * @author Rawven
 * @date 2024/11/07
 */
public class BizException extends RuntimeException {
	public BizException(String message) {
		super(message);
	}
}
