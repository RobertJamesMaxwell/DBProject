package dbgui;
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
				compID.setBounds(750, 0, 119, 28);
			}
			{
				compIDField = new JTextField("Enter company ID");
				getContentPane().add(compIDField);
				compIDField.setBounds(900, 0, 119, 28);
				compIDField.setEditable(false);
			}
			
			//Set per_id field
			{
				perID = new JLabel();
				getContentPane().add(perID);
				perID.setText("Set per_id: ");
				perID.setBounds(750, 50, 119, 28);
			}
			{
				perIDField = new JTextField("Enter person ID");
				getContentPane().add(perIDField);
				perIDField.setBounds(900, 50, 119, 28);
				perIDField.setEditable(false);
			}
			
			//Set job_profile field
			{
				jobProfile = new JLabel();
				getContentPane().add(jobProfile);
				jobProfile.setText("Set job_profile: ");
				jobProfile.setBounds(750, 100, 119, 28);
			}
			{
				jobProfileField = new JTextField("Enter job profile");
				getContentPane().add(jobProfileField);
				jobProfileField.setBounds(900, 100, 119, 28);
				jobProfileField.setEditable(false);
			}
			
			//Create dropdown menu for queries
			{
				qLabel = new JLabel();
				getContentPane().add(qLabel);
				qLabel.setText("Choose a Query: ");
				qLabel.setBounds(7, 0, 91, 28);
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
				qJCombo.setBounds(7, 28, 231, 28);
				qJCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						qJComboActionPerformed(evt);
					}
				});
			}
			
			{
				qPane = new JScrollPane();
				getContentPane().add(qPane);
				qPane.setBounds(245, 0, 445, 121);
				{
					qArea = new JTextArea();
					qPane.setViewportView(qArea);
					qArea.setText("Enter query");
				}
			}
			
			
			{
				message = new JTextArea();
				getContentPane().add(message);
				message.setText("database message");
				message.setBounds(42, 154, 623, 84);
			}
			{
				messagePane = new JScrollPane();
				getContentPane().add(messagePane);
				messagePane.setBounds(35, 147, 658, 98);
				messagePane.setViewportView(message);
				message.setPreferredSize(new java.awt.Dimension(655, 95));
			}
			
			{
				execute = new JButton();
				getContentPane().add(execute);
				execute.setText("Run Query");
				execute.setBounds(35, 595, 161, 35);
				execute.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						executeActionPerformed(evt);
					}
				});
			}
			{
				resultPane = new JScrollPane();
				getContentPane().add(resultPane);
				resultPane.setBounds(35, 252, 658, 329);
				{
					TableModel resultTableModel = new DefaultTableModel(
						new String[][] { { "", "" }, { "", "" } },
						new String[] { "", "" });
					resultTable = new JTable();
					resultPane.setViewportView(resultTable);
					resultTable.setModel(resultTableModel);
					resultTable.setPreferredSize(new java.awt.Dimension(658, 308));
				}
			}
			
			pack();
			this.setSize(1122, 671);
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
			setVariableFields(queryNum);

	}
	
	private void setVariableFields(int queryNum){
		if (queryNum == 2 || queryNum == 3){
			this.compIDField.setEditable(true);
		}
		else {
			this.compIDField.setEditable(false);
			this.perIDField.setEditable(false);
			this.jobProfileField.setEditable(false);
		}

	}
	

	
	private void drawToScreen(int queryNum, String queryString){

		if (queryNum == 1){
			String[] queryStringSpaced = queryString.split(":");
			qArea.setText(queryStringSpaced[0]);
			for (int i = 1; i < queryStringSpaced.length; i++)	{
				qArea.append(" \n" + queryStringSpaced[i]);

			};
		}
		else if (queryNum == 2 || queryNum == 3) {
			String compIDString = compIDField.getText();
			queryString = queryString.replaceFirst("\\?", compIDString);
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

		if ( queryNum == 2 || queryNum == 3 ){
			String compIDString = compIDField.getText();
			int compIDInt = Integer.parseInt(compIDString);
			ps.setInt(1, compIDInt);
		}
		return ps;
	}
}
