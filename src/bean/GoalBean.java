package bean;

/**
 * 1つの目標のデータを保存するクラス
 */
public class GoalBean {
	private final int userId;
	private final String columnName;
	private final int goalNumber;
	private int clearStatus;
	
	/**
	 * @param userId
	 * @param columnName
	 * @param goalNumber
	 * @param clearStatus
	 */
	public GoalBean(final int userId, final String columnName, final int goalNumber, final int clearStatus) {
		this.userId = userId;
		this.columnName = columnName;
		this.goalNumber = goalNumber;
		this.clearStatus = clearStatus;
	}
	
	public int userId() {
		return userId;
	}

	public String columnName() {
		return columnName;
	}
	
	public int goalNumber() {
		return goalNumber;
	}
	
	public int clearStatus() {
		return clearStatus;
	}
}
