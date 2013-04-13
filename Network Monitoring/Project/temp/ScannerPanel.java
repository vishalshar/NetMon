package net.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class ScannerPanel extends JPanel {

	JButton startBtn;
    JLabel scanByLabel;
    JLabel fromLabel;
    JTable resultTable;
    JList specPortList;
    JScrollPane sp_specPortList;
    JLabel specPortLabel;
    JButton addBtn;
    JButton removeBtn;
    JLabel resultLabel;
    ButtonGroup cbg;
    JRadioButton specRadio;
    JLabel toLabel;
    JRadioButton rangeRadio;
    JTextField fromText;
    JTextField toText;
    JLabel specHostLabel;
    JList specHostList;
    JScrollPane sp_specHostList;
    JButton addBtn1;
    JButton removeBtn1;

    Vector t_head;
    Vector t_row;

	JNetList portList,hostList;

    public ScannerPanel() {




        scannerLayout customLayout = new scannerLayout();

        setFont(new Font("Helvetica", Font.PLAIN, 12));
        setLayout(customLayout);

		t_head = new Vector();
		t_head.addElement("Port");
		t_head.addElement("Status");

		t_row = new Vector();

        startBtn = new JButton("Start");

        add(startBtn);

        scanByLabel = new JLabel("Scan by : ");
        add(scanByLabel);

        fromLabel = new JLabel("From : ");
        add(fromLabel);



        resultTable = new JTable(t_row,t_head);
        add(new JScrollPane(resultTable));



        DefaultListModel listModel_specPortList = new DefaultListModel();
        specPortList = new JList(listModel_specPortList);
        sp_specPortList = new JScrollPane(specPortList);
        add(sp_specPortList);


        specPortLabel = new JLabel("Specified Port");
        add(specPortLabel);


        addBtn = new JButton("Add");
        addBtn.setFont(new Font("Serif",Font.BOLD,10));
        add(addBtn);



        removeBtn = new JButton("Del");
        removeBtn.setFont(new Font("Serif",Font.BOLD,10));
        add(removeBtn);

        resultLabel = new JLabel("Result");
        add(resultLabel);

        cbg = new ButtonGroup();
        specRadio = new JRadioButton("Specified", false);
        specRadio.setSelected(true);
        cbg.add(specRadio);
        add(specRadio);

        toLabel = new JLabel("to");
        add(toLabel);

        rangeRadio = new JRadioButton("Range", false);
        rangeRadio.addChangeListener(new ChangeListener(){

        	public void stateChanged(ChangeEvent changeEvent){

        		if(rangeRadio.isSelected()){

        			fromText.setEnabled(true);
        			toText.setEnabled(true);

        		}else{
        			fromText.setEnabled(false);
        			toText.setEnabled(false);
        		}

        	}

        });
        cbg.add(rangeRadio);
        add(rangeRadio);

        fromText = new JTextField("");
        add(fromText);

        toText = new JTextField("");
        add(toText);

		specHostLabel = new JLabel("Specified Host");
        add(specHostLabel);

		DefaultListModel listModel_specHostList = new DefaultListModel();
        specHostList = new JList(listModel_specHostList);
        sp_specHostList = new JScrollPane(specHostList);
        add(sp_specHostList);

        addBtn1 = new JButton("+");
        addBtn1.setFont(new Font("Arial",Font.BOLD,14));
        add(addBtn1);

        removeBtn1 = new JButton("-");
        removeBtn1.setFont(new Font("Arial",Font.BOLD,14));
        add(removeBtn1);


        setSize(getPreferredSize());


        //*****************************
        //PortList extends Vector Class
    	portList = new JNetList(1);
    	specPortList.setListData(portList);
    	hostList = new JNetList(0);
    	specHostList.setListData(hostList);

    	ScannerEventHandle evHandle = new ScannerEventHandle(resultTable,t_row);
    	evHandle.addListObject1(hostList,specHostList,portList,specPortList);
    	evHandle.addListObject2(specRadio,fromText,toText,startBtn);

    	//Add Action Listener
    	addBtn.addActionListener(evHandle);
    	removeBtn.addActionListener(evHandle);
    	addBtn1.addActionListener(evHandle);
    	removeBtn1.addActionListener(evHandle);

    	startBtn.addActionListener(evHandle);

    }

}

class scannerLayout implements LayoutManager {

    public scannerLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 417 + insets.left + insets.right;
        dim.height = 351 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) {

        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+272,insets.top+384,96,32);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+144,insets.top+248,88,24);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+144,insets.top+312,88,24);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+144,insets.top+40,232,176);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+264,120,120);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+232,120,24);}
        c = parent.getComponent(6);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+392,56,24);}
        c = parent.getComponent(7);
        if (c.isVisible()) {c.setBounds(insets.left+72,insets.top+392,56,24);}
        c = parent.getComponent(8);
        if (c.isVisible()) {c.setBounds(insets.left+144,insets.top+8,104,24);}
        c = parent.getComponent(9);
        if (c.isVisible()) {c.setBounds(insets.left+144,insets.top+280,96,24);}
        c = parent.getComponent(10);
        if (c.isVisible()) {c.setBounds(insets.left+240,insets.top+344,32,24);}
        c = parent.getComponent(11);
        if (c.isVisible()) {c.setBounds(insets.left+264,insets.top+280,104,24);}
        c = parent.getComponent(12);
        if (c.isVisible()) {c.setBounds(insets.left+168,insets.top+344,56,24);}
        c = parent.getComponent(13);
        if (c.isVisible()) {c.setBounds(insets.left+280,insets.top+344,56,24);}
        c = parent.getComponent(14);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+8,120,24);}
        c = parent.getComponent(15);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+40,120,136);}
        c = parent.getComponent(16);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+184,56,24);}
        c = parent.getComponent(17);
        if (c.isVisible()) {c.setBounds(insets.left+72,insets.top+184,56,24);}
    }
public static void main(String args[])
{
              ScannerPanel sp=new ScannerPanel();
}
}