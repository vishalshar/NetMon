import java.net.*;

class PortSniffer
{
	public static void main(String args[]) throws Exception
	{     for(int i=0;i<1000;i++){
            try{
            Socket s=new Socket("127.0.0.1",i);
            System.out.println(i+"is open");
            }catch(Exception e){
            System.out.println(i+" is close");
            }
            }
    }
}