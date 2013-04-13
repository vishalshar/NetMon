import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Test extends JFrame
{
	JLabel l1;
	JLabel l2;
	JTextArea t1;
	JPasswordField t2;
	JButton b1;
	JTextField d;
	JScrollPane jp1;
    public Test()
	{
		super("LOGIN");
		l1=new JLabel ("UserName");
		l2=new JLabel ("Password");
		t1=new JTextArea();
		t2=new JPasswordField();
		d=new JTextField("r");
		b1=new JButton("Let me in !!");

		setBounds(300,250,380,240);

		l1.setBounds(20,50,100,30);
		l2.setBounds(20,90,100,30);
		jp1=new JScrollPane();
		jp1.setBounds(120,50,150,100);
        jp1.setViewportView(t1);
		t2.setBounds(120,90,150,30);
		b1.setBounds(180,130,150,25);
		add(l1);
		add(l2);
		add(jp1);
		add(t2);
		add(b1);

		setLayout(null);
		setVisible(true);



		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String args[])
	{
		Test lg=new Test();
	}
}