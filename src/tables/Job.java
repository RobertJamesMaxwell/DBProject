package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Job {
	
	public Job(int jobCode, int posCode, String jobType, int payRate, String payType, int compID){
		setJobCode(jobCode);
		setPosCode(posCode);
		setJobType(jobType);
		setPayRate(payRate);
		setPayType(payType);
		setCompID(compID);
	}
	
	public Job(Person person) {
	
	}
	
	final String addJobString = "INSERT INTO takes VALUES(?, ?, ?, ?, ?, ?)";
	private PreparedStatement ps;
	
	private int jobCode;
	private int posCode;
	private String jobType;
	private int payRate;
	private String payType;
	private int compID;
	
	public int getJobCode(){
		return this.jobCode;
	}
	
	public void setJobCode(int jobCode){
		this.jobCode = jobCode;
	}
	
	public int getPosCode(){
		return this.posCode;
	}
	
	public void setPosCode(int posCode){
		this.posCode = posCode;
	}
	
	public String getJobType(){
		return this.jobType;
	}
	
	public void setJobType(String jobType){
		this.jobType = jobType;
	}
	
	public int getPayRate(){
		return this.payRate;
	}
	
	public void setPayRate(int payRate){
		this.payRate = payRate;
	}
	
	public String getPayType(){
		return this.payType;
	}
	
	public void setPayType(String payType){
		this.payType = payType;
	}
	
	public int getCompID(){
		return this.compID;
	}
	
	public void setCompID(int compID){
		this.compID = compID;
	}

	public int insert( Connection conn ){
		
		try {
			ps = conn.prepareStatement(addJobString);
			
			ps.setInt(1, this.jobCode );
			ps.setInt(2, this.posCode );
			ps.setString(3, this.jobType );
			ps.setInt(4, this.payRate );
			ps.setString(5, this.payType );
			ps.setInt(6, this.compID );
			return ps.executeUpdate();
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage() );
			return -1;
		}
		
	}
	
}
