import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class LoginP extends JFrame implements ActionListener
{
	JLabel l1;
	JLabel l2;
	JTextField t1;
	JPasswordField t2;
	JButton b1;
	JTextField d;
	public LoginP()
	{
		super("LOGIN");
		l1=new JLabel ("UserName");
		l2=new JLabel ("Password");
		t1=new JTextField();
		t2=new JPasswordField();
		d=new JTextField("r");
		b1=new JButton("Let me in !!");

		setBounds(300,250,380,240);

		l1.setBounds(20,50,100,30);
		l2.setBounds(20,90,100,30);
		t1.setBounds(120,50,150,30);
		t1.setText("Administrator");
		t2.setBounds(120,90,150,30);
		b1.setBounds(180,130,150,25);
		add(l1);
		add(l2);
		add(t1);
		add(t2);
		add(b1);

		setLayout(null);
		setVisible(true);

		b1.addActionListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String user=t1.getText();
		String pass=t2.getText();
		if(ae.getSource()==b1)
		{
			if(pass.equals("netmon"))
			{
				NetPac pAn=new NetPac();
				this.setVisible(false);
			}
			else
		    	{
                		JOptionPane.showMessageDialog(null,"Error!! Enter correct password.");
            		}
        }
	}
	public static void main(String args[])
	{
		LoginP lg=new LoginP();
	}
}
