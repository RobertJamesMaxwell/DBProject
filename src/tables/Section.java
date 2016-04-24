package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Section {
	
	final String addSectionString = "INSERT INTO person VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private PreparedStatement ps;
	
	private String cCode;
	private int secNo;
	private String semester;
	private int year;
	private String date;
	private String format;
	private float priceChange;
	private int currentEnrollment;
	private int maxEnrollment;
	
	public String getcCode(){
		return this.cCode;
	}
	
	public void setcCode(String cCode){
		this.cCode = cCode;
	}
	
	public int getSecNo(){
		return this.secNo;
	}
	
	public void setSecNo(int secNo){
		this.secNo = secNo;
	}
	
	public String getSemester(){
		return this.semester;
	}
	
	public void setSemester(String semester){
		this.semester = semester;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getFormat(){
		return this.format;
	}
	
	public void setFormat(String format){
		this.format = format;
	}
	
	public float getPriceChange(){
		return this.priceChange;
	}
	
	public void setPriceChange(float priceChange){
		this.priceChange = priceChange;
	}
	
	public int getCurrentEnrollment(){
		return this.currentEnrollment;
	}
	
	public void setCurrentEnrollment(int currentEnrollment){
		this.currentEnrollment = currentEnrollment;
	}
	
	public int getMaxEnrollment(){
		return this.maxEnrollment;
	}
	
	public void setMaxEnrollment(int maxEnrollment){
		this.maxEnrollment = maxEnrollment;
	}

	public int insert( Connection conn ){
		
		try {
			ps = conn.prepareStatement(addSectionString);
			
			ps.setString(1, this.cCode );
			ps.setInt(2, this.secNo );
			ps.setString(3, this.semester );
			ps.setInt(4, this.year );
			return ps.executeUpdate();
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage() );
			return -1;
		}
		
	}
	
}
