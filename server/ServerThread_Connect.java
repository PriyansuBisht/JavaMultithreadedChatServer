package P1.server;
import P1.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ServerThread_Connect extends Thread {
	
	public static ServerSocket ss = null;
	public static int port = 1000;
	//public static String webroot = "/temp";
	public ArrayList<Client_List> list = new ArrayList<Client_List>();
	DataInputStream in ;
	public static  Date date = new Date();
	
	public static void display(String username,String message){
		String s = " > "+(date.toString())+" >> "+(username)+" >>>	 	"+message;
		System.out.print(s);
	}
	public static void display(String message){
		String s = " > "+(date.toString())+" >> 	"+message;
		System.out.print(s);
	}
	public static void displays(String username,String message){
		String s = " > "+(date.toString())+" >> "+(username)+" >>>	 	"+message;
		System.out.println(s);
	}
	public static void displays(String message){
		String s = " > "+(date.toString())+" >> 	"+message;
		System.out.println(s);
	}
	
	public ServerThread_Connect() {		
		try {
			ss = new ServerSocket(port);
			displays("SERVER ONLINE");
		} catch (Exception e) {
			displays(e.toString());
		}	
	}
	
	@Override
	public void run()
	{
		try {
			while(ss.isBound() && !ss.isClosed()){
				if(list.size()<=10) {
					Socket s = ss.accept();
					in= new DataInputStream(s.getInputStream());
					String username = in.readUTF();
					displays("Connection Accepted : "+s.getInetAddress(), "Client Name : "+username);
					Client_List x = new Client_List(s,username);
					list.add(x);
					int y = list.indexOf(x);
					int f= in.read();
					if(f==1) {
						displays(username+" Chose to chat");
						new ServerThread_Chat(list,y);
					}else if(f==2) {
						displays(username+" Chose FileTransfer");
						ServerThread_File z = new ServerThread_File(list,y);
						z.start();
					}else if(f==3){
						displays(username+"  wants to disconnect" , "DISCONNECTING............");
						s.close();
						displays(username+"  Left");
						list.remove(x);
					}
				}
				else {
					displays("SERVER BUSY..........");
				}
					
			}
		} catch (Exception e) {
			 displays(" PROBLEM WITH SOCKET ", e.toString());
		}finally {
			try {
				if(ss!=null)
						ss.close();
			} catch(Exception e) {}
		}		
	}

}
