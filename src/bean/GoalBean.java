package bean;

/**
 * 1つの目標のデータを保存するクラス
 */
public class GoalBean {
	private final int goalNumber;
	private final int clearStatus;
	
	/**
	 * @param goalNumber
	 * @param clearStatus
	 */
	public GoalBean(final int goalNumber, final int clearStatus) {
		this.goalNumber = goalNumber;
		this.clearStatus = clearStatus;
	}
	
	public int goalNumber() {
		return goalNumber;
	}
	
	public int clearStatus() {
		return clearStatus;
	}
}
