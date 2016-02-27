import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainFrame {
	public MainFrame() throws SQLException, HeadlessException {
		JFrame frame = new JFrame("Student Database");
		JTable jb = new JTable();
		JScrollPane sp = new JScrollPane(jb);
		
		///left panel
		JPanel jsp1 = new JPanel();
		jsp1.setBackground(Color.BLACK);
		jsp1.add(sp);
		
		///right panel
		JPanel jsp2 = new JPanel(new GridLayout(6, 1));
		jsp2.setBackground(Color.BLACK);

		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsp1, jsp2);
		pane.setResizeWeight(0.5);

		/////// ----add items in right panel------
		JLabel s_name_l = new JLabel("name: ");
		JLabel s_rollno_l = new JLabel("rollno: ");
		JLabel s_marks_l = new JLabel("marks: ");
		///modified part
		JLabel s_course_l = new JLabel("course: ");

		JTextField s_name_f = new JTextField();
		JTextField s_rollno_f = new JTextField();
		JTextField s_marks_f = new JTextField();
		JTextField s_course_f = new JTextField();

		JPanel jsp3 = new JPanel(new GridLayout(4, 2));
		jsp3.setBackground(Color.WHITE);
		jsp3.add(s_name_l);
		jsp3.add(s_name_f);
		jsp3.add(s_rollno_l);
		jsp3.add(s_rollno_f);
		jsp3.add(s_marks_l);
		jsp3.add(s_marks_f);
		
		///modified part
		jsp3.add(s_course_l);
		jsp3.add(s_course_f);
		////// ----end---------------------------
		
		///modified part
		Update up = new Update(s_name_f, s_rollno_f, s_marks_f, s_course_f);
		Delete de = new Delete(s_name_f, s_rollno_f, s_marks_f, s_course_f);
		Show sh = new Show(jsp1, jb);
		Create cr = new Create(s_name_f, s_rollno_f, s_marks_f, s_course_f);
		Search sc = new Search(s_name_f, s_rollno_f, s_marks_f, s_course_f, jsp1, jb);

		JButton jb1 = new JButton("update");
		JButton jb2 = new JButton("delete");
		JButton jb3 = new JButton("show");
		JButton jb4 = new JButton("create");
		JButton jb5 = new JButton("search");

		jb1.addActionListener(up);
		jb2.addActionListener(de);
		jb3.addActionListener(sh);
		jb4.addActionListener(cr);
		jb5.addActionListener(sc);
		jsp2.add(jsp3);

		jsp2.add(jb1);
		jsp2.add(jb2);
		jsp2.add(jb3);
		jsp2.add(jb4);
		jsp2.add(jb5);
		frame.add(pane);
		frame.setSize(800, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) throws SQLException {
		new MainFrame();

	}

}
