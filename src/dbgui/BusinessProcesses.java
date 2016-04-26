package dbgui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import tables.Person;
import tables.Section;

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
		tabbedPane.addTab( "Add person skills", skillPanel );
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
		
		//Make this a pop-up notification
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
		
		//Do the same thing as drop down to get a list of courses
			
	}
	
	private void pIDComboActionPerformed(ActionEvent evt)	{
		
	}
	private void sectionComboActionPerformed(ActionEvent evt)	{
		
	}
	private void updatePersonButActionPerformed(ActionEvent evt)	{
		
	}
	
	
	
	/****************************
	 * FIND A JOB
	 *******************************/

	public void createFindJobPanel()
	{
		findJobPanel = new JPanel();
		findJobPanel.setLayout( new GridLayout( 3, 2 ) );

		findJobPanel.add( new JLabel( "Field 1:" ) );
		findJobPanel.add( new TextArea() );
		findJobPanel.add( new JLabel( "Field 2:" ) );
		findJobPanel.add( new TextArea() );
		findJobPanel.add( new JLabel( "Field 3:" ) );
		findJobPanel.add( new TextArea() );
	}
	
	public void createFindWorkerPanel()
	{
		findWorkerPanel = new JPanel();
		findWorkerPanel.setLayout( new GridLayout( 3, 2 ) );

		findWorkerPanel.add( new JLabel( "Worker INFO 1:" ) );
		findWorkerPanel.add( new TextArea() );

	}
	
	public void createSectorPanel()
	{
		sectorPanel = new JPanel();
		sectorPanel.setLayout( new GridLayout( 3, 2 ) );

	}


	
}
