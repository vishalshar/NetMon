import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;
import java.util.regex.*;

public class ShowComp extends JFrame implements ActionListener
{
	JButton b1;
	JMenuBar mb;
	JMenu File;
	JMenuItem exit;
	JLabel sel;
	static JTextArea ta;
	public ShowComp()throws InterruptedException,IOException
	{
        super("CONNECTED COMPUTERS");
        int x=0,d=20;
		mb=new JMenuBar();
		File=new JMenu("File");
		mb.add(File);
		exit=new JMenuItem("Exit");
		exit.addActionListener(this);
		File.add(exit);
        ta=new JTextArea();
        ta.setBounds(20,30,315,470);
        ta.setEditable(false);
        add(ta);

        setJMenuBar(mb);

		sel=new JLabel("The connected computers are..");
		sel.setBounds(15,5,300,30);
		add(sel);
		b1=new JButton("<< BACK");
		b1.setBounds(140,510,100,30);
		b1.setToolTipText("Back to main page");
		b1.addActionListener(this);
		add(b1);
		setLayout(null);
		while(x<360)
		{
			x=x+d;
			setBounds(675,50,x,600);
			this.show();
		}
		//setVisible(true);
		String s="192.168.0.",temp=null;
        Printer printer = new Printer();
        printer.start();
        Connector connector = new Connector(printer);
        connector.start();

        LinkedList targets = new LinkedList();
        for (int i = 1; i <= 255; i++) {
        	    temp=s+Integer.toString(i);
           	    Target t = new Target(temp);
                targets.add(t);
                connector.add(t);
        }
        Thread.sleep(2000);
        connector.shutdown();
        connector.join();

        for (Iterator i = targets.iterator(); i.hasNext();) {
	        Target t = (Target)i.next();
             if (!t.shown)
             		t.show();
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Exit"))
			this.dispose();
		if(ae.getSource()==b1)
			this.dispose();
	}


	static int port = 113;
    static class Target {

	InetSocketAddress address;
	SocketChannel channel;
	String add;
	Exception failure;
	long connectStart;
	long connectFinish = 0;
	boolean shown = false;

	Target(String host) {
	    add=host;
	    try {
		address = new InetSocketAddress(InetAddress.getByName(host),
						port);
	    } catch (IOException x) {
		failure = x;
	    }
	}

	void show() {
	    if (connectFinish != 0)
	           ta.setText(ta.getText()+"\n"+add);
        shown = true;
	}

    }


    static class Printer
	extends Thread
    {
	LinkedList pending = new LinkedList();

	Printer() {
	    setName("Printer");
	    setDaemon(true);
	   }

	void add(Target t) {
	    synchronized (pending) {
		pending.add(t);
		pending.notify();
	    }
	}

	public void run() {
	    try {
		for (;;) {
		    Target t = null;
		    synchronized (pending) {
			while (pending.size() == 0)
			    pending.wait();
			t = (Target)pending.removeFirst();
		    }
		    t.show();
		}
	    } catch (InterruptedException x) {
		return;
	    }
	}

    }


    static class Connector
	extends Thread
    {
	Selector sel;
	Printer printer;


	LinkedList pending = new LinkedList();

	Connector(Printer pr) throws IOException {
	    printer = pr;
	    sel = Selector.open();
	    setName("Connector");
	}

	void add(Target t) {
	    SocketChannel sc = null;
	    try {

		sc = SocketChannel.open();
		sc.configureBlocking(false);

		boolean connected = sc.connect(t.address);

		t.channel = sc;
		t.connectStart = System.currentTimeMillis();

		if (connected) {
		    t.connectFinish = t.connectStart;
		    sc.close();
		    printer.add(t);
		} else {
		    synchronized (pending) {
		        pending.add(t);
		    }

		    sel.wakeup();
		}
	    } catch (IOException x) {
		if (sc != null) {
		    try {
			sc.close();
		    } catch (IOException xx) { }
		}
		t.failure = x;
		printer.add(t);
	    }
	}


	void processPendingTargets() throws IOException {
	    synchronized (pending) {
		while (pending.size() > 0) {
		    Target t = (Target)pending.removeFirst();
		    try {


			t.channel.register(sel, SelectionKey.OP_CONNECT, t);

		    } catch (IOException x) {

			t.channel.close();
			t.failure = x;
			printer.add(t);

		    }

		}
	    }
	}

	void processSelectedKeys() throws IOException {
	    for (Iterator i = sel.selectedKeys().iterator(); i.hasNext();) {


		SelectionKey sk = (SelectionKey)i.next();
		i.remove();


		Target t = (Target)sk.attachment();
		SocketChannel sc = (SocketChannel)sk.channel();

		try {
		    if (sc.finishConnect()) {
			sk.cancel();
			t.connectFinish = System.currentTimeMillis();
			sc.close();
			printer.add(t);
		    }
		} catch (IOException x) {
		    sc.close();
		    t.failure = x;
		    printer.add(t);
		}
	    }
	}

	volatile boolean shutdown = false;


	void shutdown() {
	    shutdown = true;
	    sel.wakeup();
	}


	public void run() {
	    for (;;) {
		try {
		    int n = sel.select();
		    if (n > 0)
			processSelectedKeys();
		    processPendingTargets();
		    if (shutdown) {
			sel.close();
			return;
		    }
		} catch (IOException x) {
		    x.printStackTrace();
		}
	    }
	}

    }


  /*  public static void main(String args[])// throws InterruptedException, IOException
	{   try{
		ShowComp sc=new ShowComp();
		}catch(Exception e){}//Ping();
	}*/
}