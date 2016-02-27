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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Search implements ActionListener {
	PreparedStatement ps1;
	ResultSet rs1;
	Connection con;
	JPanel jsp;
	JTable jb;
	String name;
	String course;
	int rollno = -1;
	int marks = -1;
	JTextField s_name;
	JTextField s_rollno;
	JTextField s_marks;
	JTextField s_course;

	public Search(JTextField s_name_f, JTextField s_rollno_f, JTextField s_marks_f, JTextField s_course_f, JPanel jsp1,
			JTable jb) throws SQLException {
		this.jsp = jsp1;
		this.jb = jb;
		this.s_name = s_name_f;
		this.s_rollno = s_rollno_f;
		this.s_marks = s_marks_f;
		this.s_course = s_course_f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:5228/student", "root", "root");
			
			
			if (s_rollno.getText().length() != 0) {
				rollno = Integer.parseInt(s_rollno.getText());
			}
			if (s_marks.getText().length() != 0) {
				marks = Integer.parseInt(s_marks.getText());
			}
			if (s_name.getText().length() != 0) {
				name = s_name.getText();
			}
			if (s_course.getText().length() != 0) {
				course = s_course.getText();
			}
			
			
			if (s_name.getText().length() != 0) {

				ps1 = con.prepareStatement(
						"select name, student.rollno, course, marks from student, marks, course where student.rollno = marks.rollno && student.rollno = course.rollno && student.name = \'"
								+ name + "\'");
			} else if (rollno != -1) {

				ps1 = con.prepareStatement(
						"select name, student.rollno, course, marks from student, marks, course where student.rollno = marks.rollno && student.rollno = course.rollno && student.rollno = "
								+ rollno + "");

			} else if (marks != -1) {

				ps1 = con.prepareStatement(
						"select name, student.rollno, course, marks from student, marks, course where student.rollno = marks.rollno && student.rollno = course.rollno && marks = "
								+ marks + "");
			} else if (s_course.getText().length() != 0) {
				///modified part
				ps1 = con.prepareStatement(
						"select name, student.rollno, course, marks from student, marks, course where student.rollno = marks.rollno && student.rollno = course.rollno && course.course = \'"
								+ course + "\'");
			}
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
			System.out.println(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				System.out.print(rs.getObject(columnIndex) + " ");
				vector.add(rs.getObject(columnIndex));
			}
			System.out.println();
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);

	}

	public JTable getTable() {
		return jb;
	}

}
