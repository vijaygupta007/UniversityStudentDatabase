import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;

public class Update implements ActionListener {
	Statement ps1, ps2;
	int rs1;
	int rs2;
	Connection con;
	JTextField name;
	JTextField rollno;
	JTextField marks;
	JTextField course;

	public Update(JTextField s_name_f, JTextField s_rollno_f, JTextField s_marks_f, JTextField s_course_f)
			throws SQLException {
		this.name = s_name_f;
		this.rollno = s_rollno_f;
		this.marks = s_marks_f;
		this.course = s_course_f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:5228/student", "root", "root");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			/// initialising variables -------
			ps1 = con.createStatement();
			int m = -1;
			if (marks.getText().length() != 0) {
				m = Integer.valueOf(marks.getText());
			}
			int r = -1;
			if (rollno.getText().length() != 0) {
				r = Integer.valueOf(rollno.getText());
			}
			String c = course.getText();
			String n = name.getText();

			if (marks.getText().length() != 0 && rollno.getText().length() != 0) {

				rs2 = ps1.executeUpdate("update marks set marks = " + m + " where rollno = " + r + " ");
			} else if (course.getText().length() != 0 && rollno.getText().length() != 0) {
				///modified part
				rs2 = ps1.executeUpdate("update course set course =  \'" + c + "\' where rollno = " + r + " ");
			} else if (name.getText().length() != 0 && marks.getText().length() != 0) {
			
				rs2 = ps1.executeUpdate("update marks set marks = " + m
						+ " where rollno in ( select rollno from student where  name = \'" + n + "\') ");
			} else if (name.getText().length() != 0 && course.getText().length() != 0) {
				///modified part
				rs2 = ps1.executeUpdate("update course set course =  \'" + c
						+ "\' where rollno in ( select rollno from student where  name = \'" + n + "\') ");
			}

			
		} catch (SQLException e1) {
			 e1.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
