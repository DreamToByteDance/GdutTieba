package www.raven.sw.constant;

/**
 * topic status enum
 *
 * @author Rawven
 * @date 2024/11/07
 */
public enum TopicStatusEnum {
	/**
	 * 待处理
	 */
	PENDING,
	/**
	 * 同意
	 */
	APPROVED,
	/**
	 * 拒绝
	 */
	REJECTED;
	
	public static TopicStatusEnum getByName(String name) {
		for (TopicStatusEnum value : TopicStatusEnum.values()) {
			if (value.name().equals(name)) {
				return value;
			}
		}
		return null;
	}
}
