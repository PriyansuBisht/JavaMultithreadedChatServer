package P1.server;
import java.util.*;

import P1.*;

import java.io.*;
import java.net.* ;


public class ServerThread_File extends Thread {
	
	public static int port = 8080;
	//public static String webroot = "/temp";
	public static Socket s = null; 
	public ArrayList<Client_List> list = new ArrayList<Client_List>();
	public int index;
	String username;
	public static  Date date = new Date();
	public static Client_List cl;
	public String fileS;
	public String fileR;
	
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
	
	public ServerThread_File(ArrayList<Client_List> list , int y) {
		this.list=list;
		this.index=y;
		cl = list.get(index);
		ServerThread_Chat.s = cl.s;
		this.username=cl.username;
		fileS ="C:/Users/Priyansu Bisht/Desktop/Server/";
		fileR="C:/Users/Priyansu Bisht/Desktop/Client/";
	}
	
	public void run() {
        try {

        	String x = cl.in.readUTF();
			fileS = fileS.concat(x);
			try {
				File f = new File(fileS);
				FileInputStream fin = new FileInputStream(f);
				System.out.println("Asked for file : " + x);
				System.out.println("File Located : " + fileS);
				cl.out.write(1);
				long n = (long) f.length();
				cl.out.writeLong(n);
				byte[] b = new byte[(int) n];
				System.out.println("Sending File......................");
				while (fin.read(b) > 0) {
					cl.out.write(b);
					cl.out.flush();
				}
				fin.close();
				System.out.println("File Sent !!!");
			} catch (Exception e) {
				System.out.println("File not Found!!!");
				cl.out.write(0);
			}

		} catch (Exception e) {}
	}
}
