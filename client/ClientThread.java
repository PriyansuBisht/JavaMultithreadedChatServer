package P1.client;
import java.net.*;
import java.io.*;
import java.util.*;


public class ClientThread {
	
	public static Socket s;
	public static String username;
	public static  Date date = new Date();
	public String file = "";
	public static DataInputStream in;
	public static DataOutputStream out;
	public static BufferedReader br;
	
	public static void display(String username,String message){
		String s = " > "+(date.toString())+" >> "+(username)+" >>>	 	"+message;
		System.out.print(s);
	}
	public static void display(String message){
		String s = " > "+(date.toString())+" >> 	"+message+" >>> ";
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
	
	public ClientThread ()
	{
		try{
			displays("Waiting for Connection............");
			Thread.sleep(1000);
			s= new Socket("localhost",1000);
			
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
		} catch(Exception e)
		{
			displays(e.toString());
		}
	}
	class Read implements Runnable{

		public void run() {
			while(s.isConnected()) {
				try {
					String msg = in.readUTF();
					displays(msg);
					}catch(Exception e) {
						displays("Server has closed the connection: " ,e.toString() );
						break;
					}
				}
		}
	}
	
	class Write implements Runnable {

		public void run() {
			while(s.isConnected()) {
					String message="";
					do{	
						try {	
							message=br.readLine();
							out.writeUTF(message);
						} catch (IOException e) {e.printStackTrace();}
					}while(s.isConnected());
					/*
					* try {
					*	in.close();
					*	out.close();
					*	s.close();
					*	display(username,"CLIENT OFFLINE");
					*	} catch (IOException e) {e.printStackTrace();}
					*/
			}
		}
	}
	
	
	class fileTransfer implements Runnable{
		public void run() {
		
			while(s.isConnected())
			{
				System.out.println("Asking for File..........."); 
				System.out.println("Enter File name : ");
				File f;
				FileOutputStream fout;
				int flag = 0;
				try {
						file=br.readLine();
						out.writeUTF(file);
						out.flush();
						flag=in.read();
					} catch (IOException e1) {e1.printStackTrace();}				
					if(flag != 0) {
						System.out.println("Receiving File...........................");
						String a="",b="";
						try {
							System.out.println("Enter Save location : ");
							a=br.readLine();
							System.out.println("Save as : ");
							b=br.readLine();
							a=a.concat(b);
							System.out.println("File Received !!!");
							System.out.println("Location : "+a);
							f=new File(a);
							fout = new FileOutputStream(f);
							long n=in.readLong();
							byte[] bt=new byte[(int)n];
							while(in.read(bt)>0) {
								fout.write(bt);
							}
							fout.close();
						} catch (Exception e) {e.printStackTrace();}
					}else {
						System.out.println("File not found !!!");
					}
			}
		}
	}
		
	
	public static void main(String args[]) throws Exception{
		try {
			ClientThread x = new ClientThread();
			display("Enter Username  ");
			username = br.readLine();
			out.writeUTF(username);
			displays(" CONNECTED TO SERVER !!!!!!!! ");
			displays("CHOOSE : ");
			displays("1. Chat");
			displays("2. Request File");
			displays("3. Exit");
			display(" Choice ");
			int n =Integer.parseInt(br.readLine());
			out.write(n);
			if(n==1) {
				try {
					Thread t1=new Thread(x.new Read());
					Thread t2=new Thread(x.new Write());
					t1.start();
					t2.start();
				} catch (Exception e) {e.printStackTrace();}
			}else if(n==2) {
				try {
					Thread t = new Thread(x.new fileTransfer());
					t.start();
				} catch (Exception e) {e.printStackTrace();}
			}else if(n==3) {
				try {
					s.close();
				} catch (Exception e) {e.printStackTrace();}
			}else {
				System.out.println("Invalid");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

