package asus.ft.autoBrowsing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import android.util.Log;


public class FileIO {
	public static String CurrentTime(){
		Date now = new Date();
		return new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now);
	}	
	
	public static void WriteAPI_Result(String Result){	
		CreateAPIResult();
		 Log.d(PathAndFlag.LogcatTAG,"WriteAPI_Result");
		try{
			BufferedWriter bw = null;
			try{			
				FileWriter fw = new FileWriter(PathAndFlag.path_result, true);
				
				bw = new BufferedWriter(fw);
		        
		        bw.write("["+CurrentTime()+"]: "+Result);
		        bw.newLine();
		        bw.flush();
		        bw.close();
		    }catch(IOException e){
		    	WriteLogcat("[IOException]Write the API result to file error" + e.getMessage());
		    }
		}
		catch(Exception e){
			WriteLogcat("[Exception]Write the API result to file error" + e.getMessage());
		}
	}
	
	public static void DeleteAPIResultFile(){
		try{
			File APIFile = new File(PathAndFlag.path_result);
			if(APIFile.exists()){
				APIFile.delete();			
			}			
		}
		catch(Exception e)
		{
			FileIO.WriteLogcat("DeleteAPIResultFile() error" + e.getMessage());
		}

	}
	
	private static void CreateAPIResult(){
		try{
			File file = new File(PathAndFlag.path_result);
			if(!file.exists()){
				file.getParentFile().mkdir();
			}
				
		}
		catch(Exception e){
			WriteLogcat("Create API result file error : " + e.getMessage());
		}
	}
	
	
	public static void WriteLogcat(String log){
		try{
			Log.d(PathAndFlag.LogcatTAG,log);
		}catch(Exception e)
		{
			WriteLogcat("WriteLogcat error : " + e.getMessage());
		}	
	}
}
