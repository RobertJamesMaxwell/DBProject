package dbgui;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import oracle.jdbc.OracleStatement;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dbaccess.TableInfo;

public class Queries extends javax.swing.JFrame {
	private JTextArea sQLstmt;
	private JTextArea message;
	private JScrollPane messagePane;
	private JScrollPane resultPane;
	private JScrollPane sqlstmtPane;
	private JLabel sqlstmtLab;
	private JButton execute;
	private JTable resultTable;
	private JLabel qLabel;
	private JComboBox qJCombo;
	private JScrollPane qPane;
	private JTextArea qArea;
	private JLabel compID;
	private JTextField compIDField;
	private JLabel perID;
	private JTextField perIDField;
	private JLabel jobProfile;
	private JTextField jobProfileField;
	private PreparedStatement ps = null;

	private TableInfo ti;			// the persistent manager 

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("usage: java MySQLPLUS db-username db-password"); 
			System.exit(1);
		}
		TableInfo ti = new TableInfo(args[0], args[1]); 
		Queries queries = new Queries(ti);
		queries.setVisible(true);
	}
	
	public Queries(TableInfo ti) {
		super();
		this.ti = ti;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			
			//Set comp_id field
			{
				compID = new JLabel();
				getContentPane().add(compID);
				compID.setText("Set comp_id: ");
				compID.setBounds(750, 10, 119, 28);
			}
			{
				compIDField = new JTextField("");
				getContentPane().add(compIDField);
				compIDField.setBounds(900, 10, 119, 28);
				compIDField.setEditable(false);
			}
			
			//Set per_id field
			{
				perID = new JLabel();
				getContentPane().add(perID);
				perID.setText("Set per_id: ");
				perID.setBounds(750, 60, 119, 28);
			}
			{
				perIDField = new JTextField("");
				getContentPane().add(perIDField);
				perIDField.setBounds(900, 60, 119, 28);
				perIDField.setEditable(false);
			}
			
			//Set job_profile field
			{
				jobProfile = new JLabel();
				getContentPane().add(jobProfile);
				jobProfile.setText("Set job_profile: ");
				jobProfile.setBounds(750, 110, 119, 28);
			}
			{
				jobProfileField = new JTextField("");
				getContentPane().add(jobProfileField);
				jobProfileField.setBounds(900, 110, 119, 28);
				jobProfileField.setEditable(false);
			}
			
			//Create dropdown menu for queries
			{
				qLabel = new JLabel();
				getContentPane().add(qLabel);
				qLabel.setText("Choose a Query: ");
				qLabel.setBounds(7, 0, 120, 28);
			}
			{
				String[] queryNumbers = new String[31];
				queryNumbers[0] = "Select a Query";
				for (int i = 1; i < queryNumbers.length; i++)	{
					queryNumbers[i] = "Query " + i;
				}
				ComboBoxModel qJComboModel = new DefaultComboBoxModel(queryNumbers);
				qJCombo = new JComboBox();
				getContentPane().add(qJCombo);
				qJCombo.setModel(qJComboModel);
				qJCombo.setBounds(7, 28, 181, 28);
				qJCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						qJComboActionPerformed(evt);
					}
				});
			}
			
			{
				qPane = new JScrollPane();
				getContentPane().add(qPane);
				qPane.setBounds(35, 80, 658, 260);
				{
					qArea = new JTextArea();
					qPane.setViewportView(qArea);
					qArea.setText("");
					qArea.setEditable(false);
					Color white = new Color(255,255,255);
					qArea.setBackground(white);
				}
			}
			
			
			{
				message = new JTextArea();
				getContentPane().add(message);
				message.setText("database message");
				message.setBounds(750, 350, 358, 248);
                message.setWrapStyleWord(true);
                message.setLineWrap(true);
			}
			{
				messagePane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				getContentPane().add(messagePane);
				messagePane.setBounds(750, 350, 358, 248);
				messagePane.setViewportView(message);
				//message.setPreferredSize(new java.awt.Dimension(655, 95));
			}
			
			{
				execute = new JButton();
				getContentPane().add(execute);
				execute.setText("Run Query");
				execute.setBounds(35, 695, 161, 35);
				execute.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						executeActionPerformed(evt);
					}
				});
			}
			{
				resultPane = new JScrollPane();
				//resultPane.setLayout(new GridBagLayout());
				getContentPane().add(resultPane);
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
			
			pack();
			this.setSize(1222, 771);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void executeActionPerformed(ActionEvent evt) {
		
		QueryList query = new QueryList();
		int queryNum = qJCombo.getSelectedIndex();
		String queryString = query.getQuery(queryNum);

		drawToScreen(queryNum, queryString);
		String psString = getPreparedStatementString(queryString);
		
		
		try {
				ps = ti.getConn().prepareStatement(psString);
				ps = setPreparedStatement(queryNum, ps);

				
				java.sql.ResultSet rs = ps.executeQuery();
				TableModel tableModel = new DefaultTableModel(ti.resultSet2Vector(rs), ti.getTitlesAsVector(rs));
				resultTable.setModel(tableModel);
			
		} catch (SQLException sqle) {
			message.setText(sqle.toString());
		}
	}
	
	private void qJComboActionPerformed(ActionEvent evt) {

			QueryList query = new QueryList();
			int queryNum = qJCombo.getSelectedIndex();
			compIDField.setText("");
			perIDField.setText("");
			jobProfileField.setText("");
			qArea.setText("");
			{
				TableModel resultTableModel = new DefaultTableModel(
					new String[][] { { "", "" }, { "", "" } },
					new String[] { "", "" });
				resultTable = new JTable();
				resultPane.setViewportView(resultTable);
				resultTable.setModel(resultTableModel);
			}
			setVariableFields(queryNum);

	}
	
	private void setVariableFields(int qn){
		if (qn == 1 || qn == 4 || qn == 5 || qn == 6 )	{
			this.compIDField.setEditable(false);
			this.perIDField.setEditable(false);
			this.jobProfileField.setEditable(false);
		}
			
		else if (qn == 2 || qn == 3){
			this.compIDField.setEditable(true);
			this.perIDField.setEditable(false);
			this.jobProfileField.setEditable(false);
		}
		
		else if (qn == 7 ){
			this.compIDField.setEditable(false);
			this.perIDField.setEditable(false);
			this.jobProfileField.setEditable(true);
		}
		
		else if (qn == 8 ){
			this.compIDField.setEditable(false);
			this.perIDField.setEditable(true);
			this.jobProfileField.setEditable(true);
		}
		
		else {
			this.compIDField.setEditable(false);
			this.perIDField.setEditable(false);
			this.jobProfileField.setEditable(false);
		}

	}
	

	
	private void drawToScreen(int qn, String queryString){

		if (qn == 1 || qn == 4 || qn ==5 || qn == 6){
			String[] queryStringSpaced = queryString.split(":");
			qArea.setText(queryStringSpaced[0]);
			for (int i = 1; i < queryStringSpaced.length; i++)	{
				qArea.append(" \n" + queryStringSpaced[i]);

			};
		}
		else if (qn == 2 || qn == 3) {
			String compIDString = compIDField.getText();
			queryString = queryString.replaceFirst("\\?", compIDString);
			String[] queryStringSpaced = queryString.split(":");
			qArea.setText(queryStringSpaced[0]);
			for (int i = 1; i < queryStringSpaced.length; i++)	{
				qArea.append(" \n" + queryStringSpaced[i]);

			};
		}
		else if (qn == 7) {
			String jobProfileString = jobProfileField.getText();
			queryString = queryString.replaceFirst("\\?", jobProfileString);
			String[] queryStringSpaced = queryString.split(":");
			qArea.setText(queryStringSpaced[0]);
			for (int i = 1; i < queryStringSpaced.length; i++)	{
				qArea.append(" \n" + queryStringSpaced[i]);

			};
		}
		else if (qn == 8) {
			String jobProfileString = jobProfileField.getText();
			String perIDString = perIDField.getText();
			queryString = queryString.replaceFirst("\\?", jobProfileString);
			queryString = queryString.replaceFirst("\\?", perIDString);
			queryString = queryString.replaceFirst("\\?", jobProfileString);
			queryString = queryString.replaceFirst("\\?", perIDString);
			String[] queryStringSpaced = queryString.split(":");
			qArea.setText(queryStringSpaced[0]);
			for (int i = 1; i < queryStringSpaced.length; i++)	{
				qArea.append(" \n" + queryStringSpaced[i]);

			};
		}
	}
	
	private String getPreparedStatementString(String queryString){
		queryString = queryString.replaceAll(":", "");
		return queryString;

	}
	
	private PreparedStatement setPreparedStatement(int queryNum, PreparedStatement ps) throws SQLException {
		int compIDInt = 0;
		int perIDInt = 0; 
		int jobProfileInt = 0;
		
		if ( compIDField.isEditable()  ) {
			String compIDString = compIDField.getText();			
			try {
				compIDInt = Integer.parseInt(compIDString);
			}
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter valid company ID" );
			}
		} 
		
		if ( perIDField.isEditable()  ) {
			String perIDString = perIDField.getText();			
			try {
				perIDInt = Integer.parseInt(perIDString);
			}
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter valid person ID" );
			}
		} 
		
		if ( jobProfileField.isEditable()  ) {
			String jobProfileString = jobProfileField.getText();			
			try {
				jobProfileInt = Integer.parseInt(jobProfileString);
			}
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter valid job profile number" );
			}
		} 
		
		
		if ( queryNum == 2 || queryNum == 3 ){
			ps.setInt(1, compIDInt);
		}
		if ( queryNum == 7 ){
			ps.setInt(1, jobProfileInt);
		}
		if ( queryNum == 8 ){
			ps.setInt(1, jobProfileInt);
			ps.setInt(2, perIDInt);
			ps.setInt(3, jobProfileInt);
			ps.setInt(4, perIDInt);
		}
		return ps;
	}
}
