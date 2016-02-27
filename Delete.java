import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;


public class Delete implements ActionListener{
	Statement ps1, ps2, ps3;
	int rs1;
	int rs2;
	int rs3;
	Connection con;
	JTextField name; 
	JTextField rollno;
	JTextField marks;
	JTextField course;
	
	public Delete(JTextField s_name_f, JTextField s_rollno_f, JTextField s_marks_f, JTextField s_course_f) throws SQLException {
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
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:5228/student","root","root");  
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}  
		  
		///initialising variable for query
		int r=1, m=1;
		String n = "", c="";
		
		if(rollno.getText().length() != 0){
			r=Integer.valueOf(rollno.getText());
		}
		
		if(marks.getText().length() != 0){
			m = Integer.valueOf(marks.getText());
		}
		
		n = name.getText();
		///modified part
		c = course.getText();
		
		try {
			
			ps2 = con.createStatement();ps1 = con.createStatement(); ps3 = con.createStatement();
	
			if( rollno.getText().length() != 0){
				///modified part
				rs1 = ps1.executeUpdate("delete from student where rollno = "+r +" ");
				rs2 = ps2.executeUpdate("delete from marks where rollno = "+r +" ");
				rs3 = ps3.executeUpdate("delete from course where rollno = "+r +" ");
			
			}else if(name.getText().length() != 0){
				///modified part
				rs1 = ps1.executeUpdate("delete from marks where rollno in ( select rollno from student where  name = \' "+n +"\') ");
				rs3 =  ps3.executeUpdate("delete from course where rollno in ( select rollno from student where  name = \' "+n +"\') ");
				rs2 = ps2.executeUpdate("delete from student where name = \'"+n +"\' ");
			
			}else if(marks.getText().length() != 0){
				///modified part
				rs1 = ps1.executeUpdate("delete from student where rollno in ( select rollno from marks where  marks =  "+m +") ");
				rs3 = ps3.executeUpdate("delete from course where rollno in ( select rollno from marks where  marks =  "+m +") ");
				rs2 = ps2.executeUpdate("delete from marks where marks = "+m +" ");
			
			}else if(course.getText().length() != 0){
				///modified part
				rs1 = ps1.executeUpdate("delete from student where rollno in ( select rollno from course where  course = \' "+c +"\') ");
				rs3 = ps3.executeUpdate("delete from marks where rollno in (  select rollno from course where  course = \' "+c +"\') ");
				rs2 = ps2.executeUpdate("delete from course where course = \' "+c + "\' ");
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
