package P1.server;
import java.net.*;
import java.util.*;

public class Server_Base {
	
	public static int port = 1000;
	//public static String webroot = "/temp";	
	public static ServerSocket ss;
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

	
	public static void main(String[] args) throws Exception{
			displays( "SERVER   STARTING......................");
			displays( "SERVER : "+InetAddress.getLocalHost().getCanonicalHostName());
			displays( "Port being used : "+port);
			//displays( "Webroot being used : "+webroot);
			
			ServerThread_Connect x= new ServerThread_Connect();
			x.start();
	}
}