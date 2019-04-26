package beans;

public class Expense {

	/**
	 * 保持データ
	 */
	private String empId;
	private String advanceYmd;
	private int amount;
	private String category;
	private String dscrpt;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getAdvanceYmd() {
		return advanceYmd;
	}
	public void setAdvanceYmd(String advanceYmd) {
		this.advanceYmd = advanceYmd;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDscrpt() {
		return dscrpt;
	}
	public void setDscrpt(String dscrpt) {
		this.dscrpt = dscrpt;
	}
}
