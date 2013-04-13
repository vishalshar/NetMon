import jpcap.*;
public class PacAnal
{

    public PacAnal()
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                MainGui mg = new MainGui();
                mg.setTitle("NetMon Packet Analyzer");
                mg.setVisible(true);
            }
        });
    }
}