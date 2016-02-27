import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Show implements ActionListener {
	PreparedStatement ps1;
	ResultSet rs1;
	Connection con;
	JPanel jsp;
	JTable jb;

	public Show(JPanel jsp1, JTable jb) throws SQLException {
		this.jsp = jsp1;
		this.jb = jb;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:5228/student", "root", "root");
			///modified part
			ps1 = con.prepareStatement(
					"select name, student.rollno, course, marks from student, marks, course where student.rollno = marks.rollno && student.rollno = course.rollno");
			rs1 = ps1.executeQuery();
			
			jb.setModel(buildTableModel(rs1));
			jsp.paintImmediately(0, 0, 100, 400);
			
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

	}

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);

	}

	public JTable getTable() {
		return jb;
	}

}
