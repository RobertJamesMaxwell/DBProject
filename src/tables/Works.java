package tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Works {
	
	public Works(Person person, Job job){
		setPerID(person.getPerID());
		setJobCode(job.getJobCode());
		setStartDate(null);
		setEndDate(null); 
	}
	
	public Works(Person person, Job job, Date startDate, String endDate){
		setPerID(person.getPerID());
		setJobCode(job.getJobCode());
		setStartDate(startDate);
		setEndDate(endDate);
	}
	
	public Works(int perID, int jobCode, Date startDate, String endDate){
		setPerID(perID);
		setJobCode(jobCode);
		setStartDate(startDate);
		setEndDate(endDate);
	}
	
	final String addWorksString = "INSERT INTO works VALUES(?, ?, ?, ?)";
	private PreparedStatement ps;
	
	private int perID;
	private int jobCode;
	private Date startDate;
	private String endDate;
	
	public int getPerID(){
		return this.perID;
	}
	
	public void setPerID(int perID){
		this.perID = perID;
	}
	
	public int getJobCode(){
		return this.jobCode;
	}
	
	public void setJobCode(int jobCode){
		this.jobCode = jobCode;
	}
	
	public Date getStartDate(){
		return this.startDate;
	}
	
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	
	public String getEndDate(){
		return this.endDate;
	}
	
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}
	
	public int insert( Connection conn ){
		
		try {
			ps = conn.prepareStatement(addWorksString);
			
			ps.setInt(1, this.perID );
			ps.setInt(2, this.jobCode );
			ps.setDate(3, this.startDate );
			ps.setString(4, this.endDate );
			return ps.executeUpdate();
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage() );
			return -1;
		}
		
	}
	
}
