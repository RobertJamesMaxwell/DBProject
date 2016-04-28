package dbgui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dbaccess.TableInfo;
import tables.JobProfile;
import tables.Person;
import tables.Section;
import tables.Takes;
import tables.Works;

public class BusinessProcesses extends javax.swing.JFrame {

	private	JTabbedPane tabbedPane;
	private	JPanel personPanel;
	private	JPanel skillPanel;
	private	JPanel findJobPanel;
	private	JPanel findWorkerPanel;
	private	JPanel sectorPanel;
	private TableInfo ti;
	private JButton addPersonBut;
	private PreparedStatement ps = null;
	private JTextField pIDField;
	private JTextField fnField;
	private JTextField lnField;
	private JTextField sNumField;
	private JTextField snField;
	private JTextField cityField;
	private JTextField stField;
	private JTextField zipField;
	private JTextField eField;
	private JTextField pField;
	
	private JLabel perIDSkillLabel;
	private JComboBox pIDCombo;
	private JLabel sectionSkillLabel;
	private JComboBox sectionCombo;
	private JButton updatePersonBut;
	
	private JLabel fjPanelPersonLabel;
	private JComboBox fjPanelPersonCombo;
	private JTable resultTable; 
	private JTable fjresultTable;
	private JButton addPersonJobBut;
	
	private JTable fwresultTable;
	private JComboBox fwPanelJobProfileCombo;
	
	private java.sql.ResultSet fjrs;


	public BusinessProcesses(TableInfo ti)
	{
		
		this.ti = ti;
		setTitle( "Business Processes" );
		setSize( 1250, 900 );
		setBackground( Color.gray );

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create the tab pages
		createPersonPanel();
		createSkillPanel();
		createFindJobPanel();
		createFindWorkerPanel();
		createSectorPanel();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "Add person", personPanel );
		tabbedPane.addTab( "Add person's courses", skillPanel );
		tabbedPane.addTab( "Find a Job", findJobPanel );
		tabbedPane.addTab( "Find a Job Candidate", findWorkerPanel );
		tabbedPane.addTab( "Explore Job Sector Opportunities", sectorPanel );
		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}

	public void createPersonPanel()
	{
		this.personPanel = new JPanel();
		personPanel.setLayout(null);

		//Person ID
		JLabel pIDLabel = new JLabel( "Person ID: " );
		pIDLabel.setBounds( 10, 15, 150, 20 );
		personPanel.add( pIDLabel );

		pIDField = new JTextField();
		pIDField.setBounds( 10, 35, 150, 20 );
		personPanel.add( pIDField );

		//First Name
		JLabel fnLabel = new JLabel( "First Name: " );
		fnLabel.setBounds( 10, 60, 150, 20 );
		personPanel.add( fnLabel );

		fnField = new JTextField();
		fnField.setBounds( 10, 80, 150, 20 );
		personPanel.add( fnField );
		
		//Last Name
		JLabel lnLabel = new JLabel( "Last Name: " );
		lnLabel.setBounds( 10, 105, 150, 20 );
		personPanel.add( lnLabel );

		lnField = new JTextField();
		lnField.setBounds( 10, 125, 150, 20 );
		personPanel.add( lnField );
		
		//Street Number
		JLabel sNumLabel = new JLabel( "Street Number: " );
		sNumLabel.setBounds( 10, 150, 150, 20 );
		personPanel.add( sNumLabel );

		sNumField = new JTextField();
		sNumField.setBounds( 10, 170, 150, 20 );
		personPanel.add( sNumField );
		
		//Street Name
		JLabel snLabel = new JLabel( "Street Name: " );
		snLabel.setBounds( 10, 195, 150, 20 );
		personPanel.add( snLabel );

		snField = new JTextField();
		snField.setBounds( 10, 215, 150, 20 );
		personPanel.add( snField );
		
		//City
		JLabel cityLabel = new JLabel( "City: " );
		cityLabel.setBounds( 10, 240, 150, 20 );
		personPanel.add( cityLabel );

		cityField = new JTextField();
		cityField.setBounds( 10, 260, 150, 20 );
		personPanel.add( cityField );
		
		//State
		JLabel stLabel = new JLabel( "State: " );
		stLabel.setBounds( 10, 285, 150, 20 );
		personPanel.add( stLabel );

		stField = new JTextField();
		stField.setBounds( 10, 305, 150, 20 );
		personPanel.add( stField );
		
		//Zip
		JLabel zipLabel = new JLabel( "Zip: " );
		zipLabel.setBounds( 10, 330, 150, 20 );
		personPanel.add( zipLabel );

		zipField = new JTextField();
		zipField.setBounds( 10, 350, 150, 20 );
		personPanel.add( zipField );
		
		//Email
		JLabel eLabel = new JLabel( "Email: " );
		eLabel.setBounds( 10, 375, 150, 20 );
		personPanel.add( eLabel );

		eField = new JTextField();
		eField.setBounds( 10, 395, 150, 20 );
		personPanel.add( eField );
		
		//Phone 
		JLabel pLabel = new JLabel( "Phone: " );
		pLabel.setBounds( 10, 420, 150, 20 );
		personPanel.add( pLabel );

		pField = new JTextField();
		pField.setBounds( 10, 440, 150, 20 );
		personPanel.add( pField );
		
		addPersonBut = new JButton();
		personPanel.add(addPersonBut);
		addPersonBut.setText("Add Person to Database");
		addPersonBut.setBounds(10, 480, 250, 40);
		addPersonBut.setEnabled(true);
		addPersonBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addPersonButActionPerformed(evt);
			}
		});
		
		
	}
	
	private void addPersonButActionPerformed(ActionEvent evt) {
		
		Person person = new Person();
		
		person.setPerID( Integer.parseInt( pIDField.getText() ));
		person.setFirstName( fnField.getText() );
		person.setLastName( lnField.getText() );
		person.setStreetNumber( Integer.parseInt( sNumField.getText() ) );
		person.setStreetName( snField.getText() );
		person.setCity( cityField.getText() );
		person.setState( stField.getText() );
		person.setZip( Integer.parseInt( zipField.getText() ) );
		person.setEmail( eField.getText() );
		
		int returnVal = person.insert( ti.getConn() );
		
		if (returnVal == 1){
			JOptionPane.showMessageDialog(null, "Insert Successful" );
		}
		else {
			JOptionPane.showMessageDialog(null, "Insert Not Successful" );
		}
		
	}
	
	
	
	

	public void createSkillPanel()
	{
		skillPanel = new JPanel();
		skillPanel.setLayout( null );

		{
			perIDSkillLabel = new JLabel();
			skillPanel.add(perIDSkillLabel);
			perIDSkillLabel.setText("Select a person ID: ");
			perIDSkillLabel.setBounds(7, 10, 120, 28);
		}
		{
			int numPeople = new Person().getCount( ti.getConn() );
			String[] perIDs = new String[ numPeople + 1 ];
			perIDs[0] = "Select Person ID";
			for (int i = 1; i < perIDs.length; i++)	{
				perIDs[i] = "" + i;
			}
			ComboBoxModel perIDComboModel = new DefaultComboBoxModel(perIDs);
			pIDCombo = new JComboBox();
			skillPanel.add(pIDCombo);
			pIDCombo.setModel(perIDComboModel);
			pIDCombo.setBounds(7, 30, 181, 28);
			pIDCombo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					pIDComboActionPerformed(evt);
				}
			});
		}
		//Course Section list
		{
			sectionSkillLabel = new JLabel();
			skillPanel.add(sectionSkillLabel);
			sectionSkillLabel.setText("Select a course section: ");
			sectionSkillLabel.setBounds(7, 60, 120, 28);
		}
		{
			ArrayList courseList = new Section().getList( ti.getConn() );
			String[] courses = new String[ (courseList.size() / 4) + 1 ];
			courses[0] = "Select Course Section";
			
			int counter = 1;
			String course = "";
			for (int i = 1; i <= courseList.size(); i++ ) {
				

				course = course + courseList.get(i - 1) + " | ";
				if ((i) % 4 == 0) {	
					StringBuilder builder = new StringBuilder(course);
					builder.replace(course.lastIndexOf("|"), course.lastIndexOf("|") + 1, "" );
					course = builder.toString();
					courses [counter] = course;
					course = "";
					counter++;
				}
			}
			
			ComboBoxModel sectionComboModel = new DefaultComboBoxModel(courses);
			sectionCombo = new JComboBox();
			skillPanel.add(sectionCombo);
			sectionCombo.setModel(sectionComboModel);
			sectionCombo.setBounds(7, 80, 181, 28);
			sectionCombo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					sectionComboActionPerformed(evt);
				}
			});
		}
		
		updatePersonBut = new JButton();
		skillPanel.add(updatePersonBut);
		updatePersonBut.setText("Update person's course");
		updatePersonBut.setBounds(10, 480, 250, 40);
		updatePersonBut.setEnabled(true);
		updatePersonBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updatePersonButActionPerformed(evt);
			}
		});
			
	}
	
	private void pIDComboActionPerformed(ActionEvent evt)	{
		
	}
	private void sectionComboActionPerformed(ActionEvent evt)	{
		
	}
	private void updatePersonButActionPerformed(ActionEvent evt)	{
		
		int perID = pIDCombo.getSelectedIndex();
		String courseSection = (String) sectionCombo.getItemAt(sectionCombo.getSelectedIndex());
		String[] courseSectionSplit = courseSection.split("\\|");
		for ( int i = 0; i < courseSectionSplit.length; i++ ) {
			courseSectionSplit[i] = courseSectionSplit[i].trim();
		}
		Takes takes = new Takes();
		
		takes.setPerID( perID );
		takes.setcCode( courseSectionSplit[0] );
		takes.setSecNo( Integer.parseInt( courseSectionSplit[1] ) );
		takes.setSemester( courseSectionSplit[2] );
		takes.setYear( Integer.parseInt( courseSectionSplit[3] ) );
		
		int returnVal = takes.insert( ti.getConn() );
		
		//Get skills for that course
		String getSkillsForGivenCourse = "SELECT ks_code FROM course_skill WHERE c_code = ? ";
		ArrayList skillsList = new ArrayList();
		try {
			PreparedStatement ps = ti.getConn().prepareStatement(getSkillsForGivenCourse);
			ps.setString(1, courseSectionSplit[0] );
			ResultSet rs = ps.executeQuery();
			
		//	rs.getWarnings();
			
			while (rs.next() == true)	{
			        skillsList.add( rs.getInt(1) ); // Or even rs.getObject()
			};
			//System.out.println(skillsList.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Update person's skills from skillList
		String updatePersonSkills = "";
		int resultOfSkillInsert = 0;
		for (int i = 0; i < skillsList.size(); i++){
			updatePersonSkills = "INSERT INTO obtained_skills VALUES(?,?)";

			try {
				PreparedStatement ps = ti.getConn().prepareStatement(updatePersonSkills);
				ps.setInt(1, perID);
				ps.setInt(2,  (int) skillsList.get(i));
				resultOfSkillInsert = ps.executeUpdate();
				
				if (resultOfSkillInsert == 0) {
					JOptionPane.showMessageDialog(null, ps.getWarnings() );
				}
				
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//Get certs for that course
		String getCertsForGivenCourse = "SELECT cer_code FROM requires WHERE c_code = ? ";
		ArrayList<String> certsList = new ArrayList<String>();
		try {
			PreparedStatement ps = ti.getConn().prepareStatement(getCertsForGivenCourse);
			ps.setString(1, courseSectionSplit[0] );
			ResultSet rs = ps.executeQuery();
			while (rs.next() == true)	{
			        certsList.add( rs.getString(1) ); // Or even rs.getObject()
			};
			System.out.println(certsList.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Update person's certs from certsList
		String updatePersonCerts = "";
		int resultOfCertInsert = 0;
		for (int i = 0; i < certsList.size(); i++){
			updatePersonCerts = "INSERT INTO obtained_certificates VALUES(?,?)";

			try {
				PreparedStatement ps = ti.getConn().prepareStatement(updatePersonCerts);
				ps.setInt(1, perID);
				ps.setString(2,  certsList.get(i));
				resultOfCertInsert = ps.executeUpdate();				
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

		
		if (returnVal == 1 && resultOfSkillInsert == 1 && resultOfCertInsert == 1){
			JOptionPane.showMessageDialog(null, "Update Successful" );
		}
		else {
			//JOptionPane.showMessageDialog(null, "Update Not Successful" );
		}
		
	}
	
	
	
	/****************************
	 * FIND A JOB
	 *******************************/

	public void createFindJobPanel()
	{
		findJobPanel = new JPanel();
		findJobPanel.setLayout( null );

		{
			fjPanelPersonLabel = new JLabel();
			findJobPanel.add(fjPanelPersonLabel);
			fjPanelPersonLabel.setText("Select a person ID: ");
			fjPanelPersonLabel.setBounds(7, 10, 120, 28);
		}
		{
			int numPeople = new Person().getCount( ti.getConn() );
			String[] perIDs = new String[ numPeople + 1 ];
			perIDs[0] = "Select Person ID";
			for (int i = 1; i < perIDs.length; i++)	{
				perIDs[i] = "" + i;
			}
			ComboBoxModel fjPanelPersonModel = new DefaultComboBoxModel(perIDs);
			fjPanelPersonCombo = new JComboBox();
			findJobPanel.add(fjPanelPersonCombo);
			fjPanelPersonCombo.setModel(fjPanelPersonModel);
			fjPanelPersonCombo.setBounds(7, 30, 181, 28);
			fjPanelPersonCombo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fjPanelPersonComboActionPerformed(evt);
				}
			});
		}
		{
			JLabel fjResultPaneLabel = new JLabel();
			findJobPanel.add(fjResultPaneLabel);
			fjResultPaneLabel.setText("Person is qualified for the following jobs: ");
			fjResultPaneLabel.setBounds(35, 322, 320, 28);
		}
		{
			JScrollPane resultPane = new JScrollPane();
			findJobPanel.add(resultPane);
			resultPane.setBounds(35, 352, 658, 329);
			{
				TableModel fjresultTableModel = new DefaultTableModel(
					new String[][] { { "", "" }, { "", "" } },
					new String[] { "", "" });
				fjresultTable = new JTable();
				resultPane.setViewportView(fjresultTable);
				fjresultTable.setModel(fjresultTableModel);
			}
		}
		
		addPersonJobBut = new JButton();
		findJobPanel.add(addPersonJobBut);
		addPersonJobBut.setText("Add person to this job");
		addPersonJobBut.setBounds(10, 710, 250, 40);
		addPersonJobBut.setEnabled(true);
		addPersonJobBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addPersonJobButActionPerformed(evt);
			}
		});
		
				
	}
	
	private void addPersonJobButActionPerformed(ActionEvent evt) {
		int rowSelection = fjresultTable.getSelectedRow();
		int jobCode = 0;
		try {
			fjrs.first();
			for ( int i = 0; i < rowSelection; i++ ){
				fjrs.next();
			}
			jobCode = fjrs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int perID = fjPanelPersonCombo.getSelectedIndex();
		Date date = new Date( new java.util.Date().getTime() );
		

		Works works = new Works(perID, jobCode, date, null);	
		
		int worksInsert = works.insert( ti.getConn() );

		if (worksInsert == 1) {
			JOptionPane.showMessageDialog(null, "Update Successful" );
		}
		
	}
	
	protected void fjPanelPersonComboActionPerformed(ActionEvent evt) {
		
		int perID = fjPanelPersonCombo.getSelectedIndex();
		
		//Update psString to pull from Query 15 in QueryList
		String psString = "WITH vacant_jobs(job_code) AS ( " +
						"SELECT job_code " +
						"FROM job MINUS " +
						"SELECT job_code " +
						"FROM works ) " +
						"SELECT *  " +
						"FROM job J " +
						"WHERE EXISTS ( " +
						"SELECT job_code  " +
						"FROM vacant_jobs " +
						"WHERE job_code = J.job_code)"; 
		
		try {
			ps = ti.getConn().prepareStatement(psString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//ps.setInt(1, perID);;

			fjrs = ps.executeQuery();
			TableModel tableModel = new DefaultTableModel(ti.resultSet2Vector(fjrs), ti.getTitlesAsVector(fjrs));
			fjresultTable.setModel(tableModel);
			
			//Add table mouse listener
	/*		MouseListener tableMouseListener = new MouseAdapter()  {
				
				public void mouseClicked(MouseEvent e) {
					
				}
				
			}
			fjresultTable.addMouseListener(tableMouseListener);
			int x = fjresultTable.getSelectedRow();
			System.out.println(x);
			
	*/
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, sqle.getMessage() );
		}
		
		
	}

	/****************************
	 * FIND A WORKER
	 *******************************/
	
	public void createFindWorkerPanel()
	{
		findWorkerPanel = new JPanel();
		findWorkerPanel.setLayout( null );

		{
			JLabel fwPanelJobLabel = new JLabel();
			findWorkerPanel.add(fwPanelJobLabel);
			fwPanelJobLabel.setText("Select a job profile: ");
			fwPanelJobLabel.setBounds(7, 10, 120, 28);
		}
		{
			int numJobProfiles = new JobProfile().getCount( ti.getConn() );
			String[] jobProfiles = new String[ numJobProfiles + 1 ];
			jobProfiles[0] = "Select Job Profile";
			for (int i = 1; i < jobProfiles.length; i++)	{
				jobProfiles[i] = "" + i;
			}
			
			ComboBoxModel fwPanelJobProfileModel = new DefaultComboBoxModel(jobProfiles);
			fwPanelJobProfileCombo = new JComboBox();
			findWorkerPanel.add(fwPanelJobProfileCombo);
			fwPanelJobProfileCombo.setModel(fwPanelJobProfileModel);
			fwPanelJobProfileCombo.setBounds(7, 30, 181, 28);
			fwPanelJobProfileCombo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					fwPanelJobProfileComboActionPerformed(evt);
				}
			});
		}
		{
			JLabel fjResultPaneLabel = new JLabel();
			findWorkerPanel.add(fjResultPaneLabel);
			fjResultPaneLabel.setText("The following people are qualified for that job profile: ");
			fjResultPaneLabel.setBounds(35, 322, 320, 28);
		}
		{
			JScrollPane resultPane = new JScrollPane();
			findWorkerPanel.add(resultPane);
			resultPane.setBounds(35, 352, 658, 329);
			{
				TableModel fwresultTableModel = new DefaultTableModel(
					new String[][] { { "", "" }, { "", "" } },
					new String[] { "", "" });
				fwresultTable = new JTable();
				resultPane.setViewportView(fwresultTable);
				fwresultTable.setModel(fwresultTableModel);
			}
		}
		
				
	}
	
	protected void fwPanelJobProfileComboActionPerformed(ActionEvent evt) {
		
		int posCode = fwPanelJobProfileCombo.getSelectedIndex();
		
		//Update psString to pull from Query 17 in QueryList
		String psString = "WITH q_by_skill(per_id, per_first_name, per_last_name, per_email) AS ( " +
						  "SELECT P.per_id, per_first_name, per_last_name, per_email FROM person P " +
						  "WHERE NOT EXISTS ( " +
						  "SELECT ks_code FROM required_skill " +
						  "WHERE pos_code = ?  " +
						  "MINUS " +
						  "SELECT ks_code FROM obtained_skills " +
						  "WHERE P.per_id = per_id " +
						  ")), " +
						  "q_by_cert(per_id, per_first_name, per_last_name, per_email) AS ( " +
						  "SELECT P.per_id, per_first_name, per_last_name, per_email FROM person P " +
						  "WHERE NOT EXISTS ( " +
						  "SELECT cer_code FROM job_cert " +
						  "WHERE pos_code = ? " +
						  "MINUS " +
						  "SELECT cer_code FROM obtained_certificates " +
						  "WHERE P.per_id = per_id " +
						  ")) " +
						  "SELECT per_id, per_first_name, per_last_name, per_email " +
						  "FROM q_by_skill " +
						  "INTERSECT " +
						  "SELECT per_id, per_first_name, per_last_name, per_email " +
						  "FROM q_by_cert";

		try {
			ps = ti.getConn().prepareStatement(psString);
			ps.setInt(1, posCode);
			ps.setInt(2, posCode);

			java.sql.ResultSet rs = ps.executeQuery();
			TableModel tableModel = new DefaultTableModel(ti.resultSet2Vector(rs), ti.getTitlesAsVector(rs));
			fwresultTable.setModel(tableModel);
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, sqle.getMessage() );
		}
		

	}
	
	/****************************
	 * EXPLORE A SECTOR
	 *******************************/
	
	public void createSectorPanel()
	{
		sectorPanel = new JPanel();
		sectorPanel.setLayout( null );

		{
			JLabel sectorResultPaneLabel = new JLabel();
			sectorPanel.add(sectorResultPaneLabel);
			sectorResultPaneLabel.setText("Job distribution by sector: ");
			sectorResultPaneLabel.setBounds(35, 322, 320, 28);
		}
		{
			JScrollPane resultPane = new JScrollPane();
			sectorPanel.add(resultPane);
			resultPane.setBounds(35, 352, 658, 329);
			{
				TableModel resultTableModel = new DefaultTableModel(
					new String[][] { { "", "" }, { "", "" } },
					new String[] { "", "" });
				resultTable = new JTable();
				resultPane.setViewportView(resultTable);
				resultTable.setModel(resultTableModel);
			}
		}
		
		QueryList query = new QueryList();
		String queryString = query.getQuery(26);
		queryString = queryString.replaceAll(":", "");
			

		try {
			ps = ti.getConn().prepareStatement(queryString);

			java.sql.ResultSet rs = ps.executeQuery();
			TableModel tableModel = new DefaultTableModel(ti.resultSet2Vector(rs), ti.getTitlesAsVector(rs));
			resultTable.setModel(tableModel);
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, sqle.getMessage() );
		}
		

	}



	
}
