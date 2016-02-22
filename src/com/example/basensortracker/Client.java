package com.example.basensortracker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import android.os.AsyncTask;


public class Client {
     
	Socket socket;
	PrintWriter printWriter;
	
	String ip = "192.168.0.115"; 
 	int port = 11111;
 	
 	
 	Queue<String> q;
 	
 	
	public Client(){
		this.q = new LinkedList<String>();
		new DownloadFilesTask().execute(1);
		
	 		
	}
	

	private class DownloadFilesTask extends AsyncTask<Integer, Integer, Integer> {
	     protected Integer doInBackground(Integer... urls) {
	    	 try {
	 			socket = new Socket(ip,port);
	 			printWriter =
	 			 	    new PrintWriter(
	 			 	 	new OutputStreamWriter(
	 			 		    socket.getOutputStream()));
	 		} catch (UnknownHostException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} // verbindet sich mit Server
	         return 0;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(Long result) {
	         
	     }
	 }
     
	private class SendTask extends AsyncTask<Integer, Integer, Integer> {
	     protected Integer doInBackground(Integer... urls) {
	    	 synchronized (q) {
	    		 if(q!=null && !q.isEmpty()){
	    		 	printWriter.println(q.poll());
	    		 	printWriter.flush();
	    		 }
			}
	         return 0;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(Long result) {
	         
	     }
	 }


	private class CloseTask extends AsyncTask<Integer, Integer, Integer> {
	     protected Integer doInBackground(Integer... urls) {
	    	 
	  	 	printWriter.close();
	  	 	try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  
		
	         return 0;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(Long result) {
	         
	     }
	 }



    
    void schreibeSensorWerte(float[] a, float[] b, float[]c) {
    	if(printWriter!=null){
    		q.add(a[0]+";"+a[1]+";"+a[2]+";"+b[0]+";"+b[1]+";"+b[2]+";"+c[0]+";"+c[1]+";"+c[2]);
    		new SendTask().execute(1);
    	}else{
    		
    	
    		System.out.println("Socket not yet ready");
    	}
    	
    }
    
    public void close(){
    	new CloseTask().execute(1);
    }
    

    

    
    
    
 }