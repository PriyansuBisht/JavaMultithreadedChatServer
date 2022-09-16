package P1;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client_List {
	
	public Socket s;
	public String username;
	public  DataInputStream in;
	public  DataOutputStream out;
	public  BufferedReader br;
	
	
	public Client_List(Socket s, String username) {
		this.s = s;
		this.username=username;
		try {
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
