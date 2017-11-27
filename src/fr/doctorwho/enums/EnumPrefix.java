package fr.doctorwho.enums;


// Prefix Message
public enum EnumPrefix {
    

	DOCTORWHORP("§c[§bDoctorWhoRP§c]§f ");
	
    	private String message;
	
	private EnumPrefix(String message) {
		this.message = message;
	}
	



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
