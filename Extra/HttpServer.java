package MainServer;
//import java.io.*;
//import java.net.*;
import java.util.*;

import MainServer.ServerCodes.ServerConnectThread;


public class HttpServer {
	
	public static int port = 8080;
	public static String webroot = "/temp";
	public static Date d = new Date();
	
	/*
    * 	public void setPort() {
	*		this.port = 8080;
	*	}
	*	public void setWebroot() {
	*		this.webroot = " ";
	*	}
  	*/	
  	
	/*public HttpServer() {																										// TODO Auto-generated constructor stub
			try {
				ServerSocket ss = new ServerSocket(port);
				} catch (Exception e) {
				// TODO: handle exception
			}
  		}
    */
	
	public static void main(String[] args) {											// TODO Auto-generated method stub
		System.out.println(" >> "+ (d.toString()) + "     >> SERVER   STARTING...............................");
		System.out.println(" >> "+ (d.toString()) + "     >>Port being used : "+port);
		System.out.println(" >> "+ (d.toString()) + "     >>Webroot being used : "+webroot);
		
		ServerConnectThread x= new ServerConnectThread();
		x.start();
	}
}
