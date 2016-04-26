package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class JobProfile {
	
	final String addJobProfile = "INSERT INTO job_profile VALUES(?, ?, ?, ?)";
	final String getJobProfileCount = "SELECT * FROM job_profile";
	
	private PreparedStatement ps;
	
	private int posCode;
	private String jobTitle;
	private String description;
	private int avgPay;
	
	public int getPosCode() {
		return posCode;
	}

	public void setPosCode(int posCode) {
		this.posCode = posCode;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAvgPay() {
		return avgPay;
	}

	public void setAvgPay(int avgPay) {
		this.avgPay = avgPay;
	}
	
	
	public int insert( Connection conn ){
		
		try {
			ps = conn.prepareStatement(addJobProfile);
			
			ps.setInt(1, this.posCode );
			ps.setString(2, this.jobTitle );
			ps.setString(3, this.description );
			ps.setInt(4, this.avgPay );
			return ps.executeUpdate();
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage() );
			return -1;
		}
		
	}
	
	public int getCount( Connection conn ){
		
		try {
			ps = conn.prepareStatement(getJobProfileCount);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {
			    count++;
			}
			return count;
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage() );
			return -1;
		}
		
	}


}
