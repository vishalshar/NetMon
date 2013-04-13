import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class PortScan extends JFrame implements ActionListener
{
	JButton b1,sn;
	JMenuBar mb;
	JMenu File;
	JMenuItem exit;
	JLabel sel,host,start,end;
	static JTextArea ta;
	JTextField st,sp,t1;
	public PortScan()
	{
		super("PORT SCANNER");
		int x=335,y=0,d=20;
		mb=new JMenuBar();
		File=new JMenu("File");
		mb.add(File);
		exit=new JMenuItem("Exit");
		exit.addActionListener(this);
		File.add(exit);
        ta=new JTextArea();
        ta.setBounds(18,240,300,250);
        ta.setEditable(false);
        add(ta);

        setJMenuBar(mb);

		host=new JLabel("Host Name / IP");
		host.setToolTipText("Enter a host name whose ports to be scanned");
		host.setBounds(18,15,150,20);
		add(host);
		t1=new JTextField();
		t1.setBounds(110,13,190,30);
		add(t1);
        start=new JLabel("Start");
        start.setBounds(18,75,30,20);
        add(start);
        st=new JTextField();
        st.setBounds(60,73,75,30);
        add(st);
        end=new JLabel("End");
        end.setBounds(190,75,30,20);
        add(end);
        sp=new JTextField();
        sp.setBounds(225,73,75,30);
        add(sp);
        sn=new JButton("START SCAN");
        sn.setBounds(100,150,150,30);
        sn.addActionListener(this);
        add(sn);
        sel=new JLabel("The opened ports are..");
		sel.setBounds(18,210,500,30);
		add(sel);
		b1=new JButton("<< BACK");
		b1.setBounds(135,510,100,30);
		b1.setToolTipText("Back to main page");
		b1.addActionListener(this);
		add(b1);
		setLayout(null);
		while(y<340)
		{
			if(x>15)
                    x=x-d;
			y=y+d;
			setBounds(x,50,y,600);
			this.show();
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent ae)
	{   if(ae.getSource()==sn)
		{
            ta.setText("");
            int beg,en;
            String host,temp;
            host=t1.getText();
            temp=st.getText();
            beg=Integer.parseInt(temp);
            temp=sp.getText();
            en=Integer.parseInt(temp);
            for(int k=beg;k<=en;k++)
            {
                    (new Sniff(host,k)).start();
            }
        }
        if(ae.getActionCommand().equals("Exit"))
			this.dispose();
		if(ae.getSource()==b1)
			this.dispose();
	}
	static class Sniff extends Thread
    {
        String hn;
        int p;
        public Sniff(String host,int cp)
        {
               hn=host;
               p=cp;
        }
        public void run()
        {
               try
			   {
			       Socket s=new Socket(hn,p);
			       ta.setText(ta.getText()+"Port "+p+" is open\n");
				}catch(Exception e){}
         }
    }
}