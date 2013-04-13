import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class NetPac extends JFrame implements ActionListener
{
	JButton btnSC,b2,b3,b4;
	JMenuBar mb;
	JMenu File,Help;
	JMenuItem exit,about;
	JLabel sel;
	public NetPac()
	{
		super("NetMon 1.0");
		mb=new JMenuBar();
		File=new JMenu("File");
		mb.add(File);
		Help=new JMenu("Help");
		mb.add(Help);
		exit=new JMenuItem("Exit");
		exit.addActionListener(this);
		about=new JMenuItem("About NetMon");
		File.add(exit);
		Help.add(about);

		setJMenuBar(mb);

		sel=new JLabel("Choose an option ..");
		sel.setBounds(7,15,300,30);
		add(sel);
		btnSC=new JButton("CONNECTED COMPUTERS");
		btnSC.setBounds(7,50,300,50);
		btnSC.setToolTipText("Shows the list of computers connected to the system");
		btnSC.addActionListener(this);
		add(btnSC);
		b2=new JButton("PORT SCANNER");
		b2.setBounds(7,100,300,50);
		b2.setToolTipText("Scan the specified ports of remote host");
		b2.addActionListener(this);
        add(b2);
        b3=new JButton("PACKET ANALYZER");
        b3.setToolTipText("Satrt packet analyzer");
        b3.addActionListener(this);
        b3.setBounds(7,150,300,50);
        add(b3);
        setBounds(355,50,320,600);
        setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==btnSC)
		{try {
				new ShowComp();
		}catch(Exception e){}}
		if(ae.getSource()==b2)
			new PortScan();
		if(ae.getSource()==b3)
			new PacAnal();
		if(ae.getActionCommand().equals("Exit"))
			System.exit(0);
	}


	/*public static void main(String args[])
	{
		NetPac np=new NetPac();
	} */
}