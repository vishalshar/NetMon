/*		11 æƒ…¿“§¡ 2548    23:45:21 
		AppName : Loottenj
		License : GPL
		By : Hussachai   Puripunpinyo
*/

package net.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class ScannerEventHandle implements ActionListener{
	
	JNetList hl,pl;
	JList hls,pls;
	String host,port;
	JNetCore jnet;
	JTable output;
	Vector row;
	JRadioButton spec;
	JTextField start,stop;
	JButton sBtn;
	ScanThread1 thread1;
	ScanThread2 thread2;
	
	public ScannerEventHandle(JTable output,Vector row){
		
		this.output=output;
		this.row=row;
		
		jnet = new JNetCore();
		
	}
	
	public void addListObject1(JNetList hl,JList hls,JNetList pl,JList pls){
		
		this.hl=hl;
		this.pl=pl;
		this.hls=hls;
		this.pls=pls;
		
	}
	public void addListObject2(JRadioButton spec,JTextField start,JTextField stop,JButton sBtn){
		this.spec=spec;	
		this.start=start;
		this.stop=stop;
		this.sBtn=sBtn;
	}
	public void actionPerformed(ActionEvent ev){
		
		if(ev.getActionCommand().equals("Add")){
			port =JOptionPane.showInputDialog(null,"Add port number");
			if(!port.equals(null)){
				if(! isDuplicatePort(port) ){
					pl.addListItem(port);
					pl.saveValueToFile();
					pls.updateUI();
				}else{
					JOptionPane.showMessageDialog(null,"Port is duplicate");
				}
			}
			
		}else if(ev.getActionCommand().equals("Del")){

			pl.removeElement(pls.getSelectedValue().toString());
			pl.saveValueToFile();
			pls.updateUI();
		}else if(ev.getActionCommand().equals("+")){
			
			host =JOptionPane.showInputDialog(null,"Add host name or IP");
			if(!host.equals(null)){
				if(! isDuplicateHost(host) ){
					hl.addListItem(host);
					hl.saveValueToFile();
					hls.updateUI();
				}else{
					JOptionPane.showMessageDialog(null,"Port is duplicate");
				}
			}
			
		}else if(ev.getActionCommand().equals("-")){
			
			hl.removeElement(hls.getSelectedValue().toString());
			hl.saveValueToFile();
			hls.updateUI();
				
		}else if(ev.getActionCommand().equals("Start")){
			
			if(hls.getSelectedValue()==null){
				JOptionPane.showMessageDialog(null,"Please select host before!!","HOST NOT DEFINED",JOptionPane.ERROR_MESSAGE);
			}else{
				sBtn.setText("Stop");
				if(spec.isSelected()){
					thread1=new ScanThread1(hl,hls,pl,pls,row,output,sBtn);
					thread1.start();
				}
				else{
					int begin = Integer.parseInt(start.getText());
					int end = Integer.parseInt(stop.getText());
					thread2=new ScanThread2(hl,hls,begin,end,row,output,sBtn);
					thread2.start();
					
				}
			}
		}else if(ev.getActionCommand().equals("Stop")){
			try{
				thread1.stop();
				thread2.stop();
			}catch(Exception ex){}
			finally{
				sBtn.setText("Start");
			}
			
		}
		
	}
	
	public boolean isDuplicatePort(String port){
		
		for(int i=0;i<pl.size();i++){
			if(port.equals(pl.elementAt(i).toString())){
				return true; //Yeah It's duplicate		
			}
		}
		return false; //not duplicate
	}
	
	public boolean isDuplicateHost(String host){
		
		for(int i=0;i<hl.size();i++){
			if(host.equals(hl.elementAt(i).toString())){
				return true; //Yeah It's duplicate		
			}
		}
		return false; //not duplicate
	}
	
	

}

class ScanThread1 extends Thread {
	
	Vector row;
	JList hls,pls;
	JNetList hl,pl;
	JNetCore jnet;
	JTable output;
	JButton sBtn;
	
	ScanThread1(JNetList hl,JList hls,JNetList pl,JList pls,Vector row,JTable output,JButton sBtn){
		this.row=row;
		this.output=output;
		this.hls=hls;
		this.pls=pls;
		this.hl=hl;
		this.pl=pl;
		this.sBtn=sBtn;
		jnet = new JNetCore();
	}
	
	public void run(){
		System.gc();
		row.removeAllElements();
		String hosti = hls.getSelectedValue().toString();
		int portNum;
		int open=0,all=0;
		//Vector col[] = new Vector[pl.size()];
		for(int i=0;i<pl.size();i++){
			Vector col = new Vector();
			portNum = Integer.parseInt(pl.elementAt(i).toString());
			System.out.print ("port : "+portNum);
			col.addElement(portNum);
			if(jnet.probePort(hosti,portNum)){
				col.addElement("Open");
				System.out.println (" is open");
				open++;
				all++;
			}else{
				col.addElement("Close");
				System.out.println (" is Close");
				all++;
			}
			row.addElement(col);
			output.updateUI();
			
		}
		sBtn.setText("Start");
		JOptionPane.showMessageDialog(null,"Opened : "+open+" of "+all,"REPORT",JOptionPane.INFORMATION_MESSAGE);

	}
	
}

class ScanThread2 extends Thread {
	
	Vector row;
	JList hls;
	JNetList hl;
	JNetCore jnet;
	JTable output;
	JButton sBtn;
	int start,stop;
	
	ScanThread2(JNetList hl,JList hls,int start,int stop,Vector row,JTable output,JButton sBtn){
		this.row=row;
		this.output=output;
		this.hls=hls;
		this.hl=hl;
		this.start=start;
		this.stop=stop;
		this.sBtn=sBtn;
		jnet = new JNetCore();
	}
	
	public void run(){
		System.gc();
		row.removeAllElements();
		String hosti = hls.getSelectedValue().toString();
		int portNum;
		int open=0,all=0;
		//Vector col[] = new Vector[pl.size()];
		for(int i=start;i<=stop;i++){
			Vector col = new Vector();
			System.out.print ("port : "+i);
			col.addElement(i);
			if(jnet.probePort(hosti,i)){
				col.addElement("Open");
				System.out.println (" is open");
				open++;
				all++;
			}else{
				col.addElement("Close");
				System.out.println (" is Close");
				all++;
			}
			row.addElement(col);
			output.updateUI();
		}
		sBtn.setText("Start");
		JOptionPane.showMessageDialog(null,"Opened : "+open+" of "+all,"REPORT",JOptionPane.INFORMATION_MESSAGE);

	}
	
}