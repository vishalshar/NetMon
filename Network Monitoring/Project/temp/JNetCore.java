import java.net.*;
import java.io.*;
import java.util.*;

public class JNetCore {

	public JNetCore(){


	}

	public boolean probePort(String host,int portNum){

		try{

			Socket socket = new Socket(host,portNum);
			return true;

		}catch(IOException iOException){
			return false;
		}

	}
}