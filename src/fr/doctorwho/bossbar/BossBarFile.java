package fr.doctorwho.bossbar;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class BossBarFile {

	private File bossbar;
	
	public BossBarFile() {
		bossbar = new File("/home/safety_data/bossbar.txt");
		
		if(!bossbar.exists()) throw new IllegalArgumentException("File BossBar is not exist in " + bossbar.getAbsolutePath());
	}
	
	@SuppressWarnings("deprecation")
	public String getMessage(int value){
		Map<Integer, String> message = new HashMap<>();
		
		try {
			int x = 0;
			for(String line : FileUtils.readLines(bossbar)){
				message.put(x++, line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return message.get(value);
	}
}
