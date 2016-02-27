import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;

public class Create implements ActionListener {
	Statement ps1, ps2, ps3;
	int rs1;
	int rs2;
	int rs3;
	Connection con;
	JTextField name;
	JTextField rollno;
	JTextField marks;
	///modified part
	JTextField course;

	public Create(JTextField s_name_f, JTextField s_rollno_f, JTextField s_marks_f, JTextField s_course_f)
			throws SQLException {
		this.name = s_name_f;
		this.rollno = s_rollno_f;
		this.marks = s_marks_f;
		///modified part
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

		int r = Integer.parseInt(rollno.getText());
		int m = Integer.parseInt(marks.getText());
		///modified part
		String c = course.getText();
		String n = name.getText();
		if (n != "") {
			try {
				ps1 = con.createStatement();
				ps2 = con.createStatement();
				ps3 = con.createStatement();
				rs1 = ps1.executeUpdate("insert into student values(" + r + ", \'" + n + "\')");
				rs2 = ps2.executeUpdate("insert into marks values(" + r + ", " + m + ")");
				///modified part
				rs3 = ps3.executeUpdate("insert into course values(" + r + ", \'" + c + "\')");
				
			} catch (SQLException e1) {
				 e1.printStackTrace();
			}
		}

		try {
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
