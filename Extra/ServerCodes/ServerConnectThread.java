package MainServer.ServerCodes;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerConnectThread  extends Thread{

	public static int port = 8080;
	public static String webroot = "/temp";
	public ServerSocket ss;
	public static Date d = new Date();
	
	public ServerConnectThread() {
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
				while(ss.isBound() && !ss.isClosed()){
						Socket s = ss.accept();					
						WorkOfServerThread x= new WorkOfServerThread(s);
						x.start();
				}
			} catch (Exception e) {
					System.out.println(" >> "+ (d.toString()) + "     >>Problem with Socket !!!  { "+ e + "}");
			}finally {
					try {
							if(ss!=null)
									ss.close();
					} catch(Exception e) {}
			}		
	}

}
