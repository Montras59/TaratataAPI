package fr.doctorwho.enums;

import org.bukkit.ChatColor;

// Rank List
public enum EnumRank {

	JOUEUR(0, "joueur", "§7",ChatColor.GRAY),
	ALPHA(5, "alpha", "§7[§aAplha§7] §7",ChatColor.GRAY), 
	PREMIUM(10, "premium", "§7[§aPrenium§7] §7",ChatColor.GRAY), 
	PREMIUMPLUS(20, "premiumPlus","§7[§bPrenium§d✩§7] §7",ChatColor.GRAY), 
	VIDEASTE(30, "videaste", "§8[§3Vidéaste§8] §3",ChatColor.DARK_AQUA), 
	SCENARISTE(40, "scenariste", "§8[§eScénariste§8] §e",ChatColor.DARK_PURPLE), 
	GRAPHISTE(50,"graphiste", "§8[§5Graphiste§8] §5",ChatColor.GREEN), 
	GUIDE(60, "guide", "§8[§aGuide§8] §a",ChatColor.DARK_GREEN), 
	BUILDER(65, "builder","§8[§6Builder§8] §6",ChatColor.GOLD), 
	MODERATEUR(70, "moderateur","§8[§2Modérateur§8] §2",ChatColor.GOLD), 
	DEVELOPPEUR(75,"developpeur","§8[§dDev§8] §d",ChatColor.LIGHT_PURPLE), 
	RESPONSABLESCENARISTE(80, "respdev", "§8[§bResp.Scénariste§8] §b",ChatColor.AQUA), 
	RESPONSABLEGUIDE(81, "respguide", "§8[§bResp.Guide§8] §b",ChatColor.AQUA), 
	RESPONSABLEMODO(82, "respmodo", "§8[§bResp.Modo§8] §b",ChatColor.AQUA), 
	RESPONSABLEBUILD(83, "respbuild", "§8[§bResp.Build§8] §b",ChatColor.AQUA), 
	RESPONSABLEDEV(84, "respdev", "§8[§bResp.Dev§8] §b",ChatColor.AQUA), 
	CM(90, "community-manager","§8[§9C.Manager§8] §9",ChatColor.BLUE),
	MANAGER(95, "manager","§8[§9Manager§8] §9",ChatColor.BLUE),
	ADMINISTRATEUR(100, "administrateur", "§8[§cAdmin§8] §c",ChatColor.RED); 

	int power;

	String rankName;

	String rankPrefix;
	
	ChatColor chatColor;

	private EnumRank(int power, String rankName, String rankPrefix,ChatColor chatColor) {
		this.power = power;
		this.rankName = rankName;
		this.rankPrefix = rankPrefix;
		this.chatColor = chatColor;
	}

	/**
	 * Gets the power
	 * @return power
	 */
	public int getPower() {
		return power;
	}

	/**
	 * Sets power
	 * @param power
	 */
	public void setPower(int power) {
		this.power = power;
	}

	/**
	 * Gets Rank Name
	 * @return rankName
	 */
	public String getRankName() {
		return rankName;
	}
	
	/**
	 * Gets rank
	 * @param power the rank ID
	 * @return Rank
	 */
	public static EnumRank getRank(int power) {
		for (EnumRank rank : values())
		{
			if (power == rank.getPower())
			{
				return rank;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets rank
	 * @param rankName the rank Name
	 * @return Rank
	 */
	public static EnumRank getRank(String rankName) {
		for (EnumRank rank : values())
		{
			if (rank.getRankName().equalsIgnoreCase(rankName))
			{
				return rank;
			}
		}
		
		return null;
	}


	/**
	 * Sets rank Name
	 * @param rankName
	 */
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	/**
	 * Gets rank prefix
	 * @return rankPrefix
	 */
	public String getRankPrefix() {
		return rankPrefix;
	}

	/**
	 * Sets rank prefix
	 * @param rankPrefix
	 */
	public void setRankPrefix(String rankPrefix) {
		this.rankPrefix = rankPrefix;
	}

	public ChatColor getChatColor() {
		return chatColor;
	}

	public void setChatColor(ChatColor chatColor) {
		this.chatColor = chatColor;
	}

}

