package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Person {

	final String addPersonString = "INSERT INTO person VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	final String getPersonCount = "SELECT * FROM person";
	
	private PreparedStatement ps;
	
	private int perID;
	private String firstName;
	private String lastName;
	private int streetNumber;
	private String streetName;
	private String city;
	private String state;
	private int zip;
	private String email;
	
	public int getPerID() {
		return perID;
	}
	public void setPerID(int perID) {
		this.perID = perID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}
	
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int insert( Connection conn ){
		
		try {
			ps = conn.prepareStatement(addPersonString);
			
			ps.setInt(1, this.perID );
			ps.setString(2, this.firstName );
			ps.setString(3, this.lastName );
			ps.setInt(4, this.streetNumber );
			ps.setString(5, this.streetName );
			ps.setString(6, this.city );
			ps.setString(7, this.state );
			ps.setInt(8, this.zip );
			ps.setString(9, this.email );
			return ps.executeUpdate();
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage() );
			return -1;
		}
		
	}
	
	public int getCount( Connection conn ){
		
		try {
			ps = conn.prepareStatement(getPersonCount);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while (rs.next()) {
			    count++;
			}
			//rs.last();
			System.out.println(count);
			return count;
				
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage() );
			return -1;
		}
		
	}
	
	
}
