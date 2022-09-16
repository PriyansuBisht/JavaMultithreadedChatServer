package MainServer.ServerCodes;

import java.io.*;
import java.net.*;
import java.util.*;
public class WorkOfServerThread extends Thread{
	
	public static int port = 8080;
	public static String webroot = "/temp";
	public Socket s;
	public static Date d = new Date();
	
	public WorkOfServerThread(Socket s) {
		this.s=s;
	}
	
	@Override
	public void run(){
		InputStream in=null;
		OutputStream out=null;
		try{
			System.out.println(" >> "+ (d.toString()) + "     >>Connection Accepted : "+s.getInetAddress());
			in= s.getInputStream();
			out = s.getOutputStream();
			
			String html="<html> "
								+ "<head> <title> MULTITHREADED HTML WED SERVER </title> </head> "
								+ "<body> <h1> This page is linked to a java server </h1> </body> "
								+ "</html>";
			final String CR_LF ="\n\r";
			String response ="HTTP/1.1 200 OK"+ CR_LF +
					"Content-Length: " + html.getBytes().length + CR_LF + 
					CR_LF + html + CR_LF + CR_LF;
			out.write(response.getBytes());
		} catch(Exception e) {
			System.out.println(" >> "+ (d.toString()) + "     >>Problem Connecting !!!  { " + e + "}");
		}finally {
				if (in!= null) {
					try {
						in.close();
					} catch (Exception e2) {} 
				}
				
				if (out != null) {
					try {
						out.close();
					} catch (Exception e) {}
				}
				
				if (s != null) {
					try {
						System.out.println(" >> "+ (d.toString()) + "     >>Connection Closed  :  " + s.getInetAddress());
						s.close();
					} catch (IOException e) {}
				}
		}
	
	}
}
