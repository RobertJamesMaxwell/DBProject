package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Takes {
	
	Takes(Person person, Section section){
		setPerID(person.getPerID());
		setcCode(section.getcCode());
		setSecNo(section.getSecNo());
		setSemester(section.getSemester());
		setYear(section.getYear());
	}
	
	final String addTakesString = "INSERT INTO person VALUES(?, ?, ?, ?, ?)";
	private PreparedStatement ps;
	
	private int perID;
	private String cCode;
	private int secNo;
	private String semester;
	private int year;
	
	public int getPerID(){
		return this.perID;
	}
	
	public void setPerID(int perID){
		this.perID = perID;
	}
	
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

	public int insert( Connection conn ){
		
		try {
			ps = conn.prepareStatement(addTakesString);
			
			ps.setInt(1, this.perID );
			ps.setString(2, this.cCode );
			ps.setInt(3, this.secNo );
			ps.setString(4, this.semester );
			ps.setInt(5, this.year );
			return ps.executeUpdate();
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage() );
			return -1;
		}
		
	}
	
}
