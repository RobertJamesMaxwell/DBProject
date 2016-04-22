package tables;

public class Certificate {

	private String cer_code;
	private String cer_title;
	private String description;
	private String expire_date;
	private String issued_by;
	private String t_code;
	
	public String getcer_code() {
		return cer_code;
	}
	public void setcer_code(String cer_code) {
		this.cer_code = cer_code;
	}
	public String getcer_title() {
		return cer_title;
	}
	public void setcer_title(String cer_title) {
		this.cer_title = cer_title;
	}
	public String getdescription() {
		return description;
	}
	public void setdescription(String description) {
		this.description = description;
	}
	public String getexpire_date() {
		return expire_date;
	}
	public void setexpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	public String getissued_by() {
		return issued_by;
	}
	public void setissued_by(String issued_by) {
		this.issued_by = issued_by;
	}
	public String gett_code() {
		return t_code;
	}
	public void sett_code(String t_code) {
		this.t_code = t_code;
	}
}
