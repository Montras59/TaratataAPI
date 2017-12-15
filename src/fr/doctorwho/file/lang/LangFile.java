package fr.doctorwho.file.lang;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

@SuppressWarnings("deprecation")
public class LangFile {

	// FILE LANG
	private File french;
	private File english;

	public LangFile() {
		this.french = new File("/home/safety_data/lang/french.txt");
		this.english = new File("/home/safety_data/lang/english.txt");
	}
	
	public void load(){
		if(!french.exists()) throw new IllegalArgumentException("File French is not exist in " + french.getAbsolutePath());
		if(!english.exists()) throw new IllegalArgumentException("File English is not exist in " + english.getAbsolutePath());
	}
	
	public String getMessage(String message,int lang){
		if(lang == 0) return getMessageFrench(message);
		if(lang == 1) return getMessageEnglish(message);
		else return "§cError Get Message";
	}
	
	
	/*
	 * Value default is the message
	 * @param message
	 * @return value
	 */
	
	private String getMessageFrench(String message){
		String value = message;
		try {
			for(String line : FileUtils.readLines(french)){
				String[] args = line.split(":");
				if(args[0].equalsIgnoreCase(message)) value = args[1];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return value.replaceAll("&", "§");
	}
	
	private String getMessageEnglish(String message){
		String value = message;
		try {
			for(String line : FileUtils.readLines(english)){
				String[] args = line.split(":");
				if(args[0].equalsIgnoreCase(message)) value = args[1];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return value.replaceAll("&", "§");
	}
}
