package P1.server;
import java.util.*;

import P1.*;


import java.io.*;
import java.net.* ;


public class ServerThread_Chat {
	
	public static int port = 1000;
	//public static String webroot = "/temp";
	public static Socket s = null; 
	public ArrayList<Client_List> list = new ArrayList<Client_List>();
	public int index;
	public String username;
	public Client_List n;
	public static  Date date = new Date();
	Scanner sc;
	
	public static void display(String username,String message){
		String s = " > "+(date.toString())+" >> "+(username)+" >>>	 	"+message;
		System.out.print(s);
	}
	public static void display(String message){
		String s = " > "+(date.toString())+" >> "+message;
		System.out.print(s);
	}
	public static void displays(String username,String message){
		String s = " > "+(date.toString())+" >> "+(username)+" >>>	 	"+message;
		System.out.println(s);
	}
	public static void displays(String message){
		String s = " > "+(date.toString())+" >> "+message;
		System.out.println(s);
	}
	
	public ServerThread_Chat(ArrayList<Client_List> list , int y) {
		this.list=list;
		this.index=y;
		n = list.get(index);
		ServerThread_Chat.s = n.s;
		this.username=n.username;
		sc= new Scanner(System.in);
		try {
			Thread t1 = new Thread(new Read());
			Thread t2 = new Thread(new Write());
			t1.start();
			t2.start();		
		}catch(Exception e) {}
		if (s == null) {
				try {
					displays("Connection Closed  :  " + s.getInetAddress());
					s.close();
					list.remove(index);
				} catch (IOException e) {}
			}
	}
	
	
	class Read implements Runnable{
		public void run() {
			String message="";
			do{
				try {
						message=n.in.readUTF();
						String w[] = message.split(" ",2);					
						if(w[0].charAt(0)=='@'){
								String tocheck=w[0].substring(1);
								if(tocheck.equalsIgnoreCase("Server")) {	
										try {
												displays(username,w[1]);
												display(" Reply ");
												String S = sc.nextLine();
												String s = " Reply from Server >>>  "+S;
												n.out.writeUTF(s);
										} catch (Exception e) { e.printStackTrace(); }	
								}else{
										for(int y=list.size()-1;y>=0;--y){
												Client_List cl = list.get(y);
												String check = cl.username;
												if(check.equals(tocheck) && !cl.username.equalsIgnoreCase(username)){
														if(cl.s.isConnected()) {
																try {
																		String str = username+" >>> 	"+w[1];
																		cl.out.writeUTF(str);
																		cl.out.flush();
																} catch (IOException e) {displays("Error on Transmitting message",e.toString()); }
														}else {
																list.remove(y);
																displays(username,"Disconnected!!!!!!!!!!!!");
																cl.s.close();
														}
														break;
												}
										}
								}
						}else{
								displays(username,message);
								for(int i = list.size()-1; i >= 0;--i) {
										Client_List cl1 = list.get(i);
										if(!cl1.username.equalsIgnoreCase(username)) {
												if(cl1.s.isConnected() ) {
														try {
															String str = username+" >>> 	"+message;
															cl1.out.writeUTF(str);
															cl1.out.flush();
														} catch (IOException e) {
																displays("Error on Transmitting message",e.toString());
														}
														}else {
																				cl1.s.close();
																				list.remove(i);
																				displays(username,"Disconnected!!!!!!!!!!!!");
																		}
										}
								}
						}
				}catch (Exception e) {
					displays(username ," Exception reading Streams: " );
					break;				
				}
		}while(s.isConnected());
			
		}
	}

	
	
	class Write implements Runnable{
		public void run() {
			while(true) {
				String message = null;
				try {
					message = sc.nextLine();
				} catch (Exception e ){ e.printStackTrace(); }
				
				String w[] = message.split(" ",2);					
				if(w[0].charAt(0)=='@'){
					String tocheck=w[0].substring(1);
					for(int y=list.size()-1;y>=0;--y){
							Client_List Cl = list.get(y);
							String check = Cl.username;
							if(check.equals(tocheck)){
								if(Cl.s.isConnected()) {
									try {
										String str = " Server  >>> 	"+w[1];
										Cl.out.writeUTF(str);
										Cl.out.close();
									} catch (IOException e) {displays("Error on Transmitting message",e.toString()); }
									
								}else {
									list.remove(y);
									displays(username,"Disconnected!!!!!!!!!!!!");
									try {
										Cl.s.close();
									} catch (IOException e) {e.printStackTrace();}
								}
								break;
								}
							}
					}else{
						for(int i = list.size()-1; i >= 0;--i) {
						Client_List Cl1 = list.get(i);
							if(Cl1.s.isConnected() ) {
								try {
									String str = " Server >>> 	"+message;
									Cl1.out.writeUTF(str);
									Cl1.out.flush();
								} catch (IOException e) {
									displays("Error on Transmitting message",e.toString());
									}
								}else {
									try {
										Cl1.s.close();
									} catch (IOException e) {e.printStackTrace();}
									list.remove(i);
									displays(username,"Disconnected!!!!!!!!!!!!");
								}
						}
					}
			}
		}
	}
	
		
}