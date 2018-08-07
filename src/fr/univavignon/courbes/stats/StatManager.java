package fr.univavignon.courbes.stats;

import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.inter.simpleimpl.profiles.ProfileManager;
/**
 * Classe permettant de recuper et de sauvegarder l'ensemble des statisques sauvegarder localement 
 * @author pc
 *
 */
public class StatManager {

	/**
	 * Repertoires ou sont sauvegardees les donnees Numberof
	 */
	private static final String Numberof ="res/stat/Numberof/";
	/**
	 * Repertoires ou sont sauvegardees les donnnes Numberon
	 */
	private static final String Numberwin ="res/stat/Numberwin/";
	/**
	 * Repertoires ou sont sauvegardees les donnees Numberparties
	 */
	private static final String Numberparties ="res/stat/Numberparties/";
	/**
	 * Repertoires ou sont sauvegardees les donnees Numbermatch
	 */
	private static final String Numbermatch ="res/stat/Numbermatch/";
	/**
	 * Repertoires ou sont sauvegardees les donnees Numberlost
	 */
	private static final String Numberlost ="res/stat/Numberlost/";
	/**
	 * @param nmb Objet NumberOf qu'on veut sauvegarder
	 */
	public static void saveNumberOf(NumberOf nmb)
	{
		Fichier.Save(nmb,Numberof+nmb.getUserName());
	}
	/**
	 * 
	 * @param win Objet Numberwin qu on veut sauvegarder
	 */
	public static void saveNumberWinMois(NumberWinMois win)
	{
		Fichier.Save(win,Numberwin+win.getUserName());
	}
	/**
	 * 
	 * @param parties Objet number parties qu on veut recuperer
	 */
	public static void saveNumberPartiesMois(NumberPartiesMois parties)
	{
		Fichier.Save(parties,Numberparties+parties.getUserName());
	}
	/**
	 * 
	 * @param parties Objet NumberDefaitesMois qu on veut sauvegarder
	 */
	public static void saveNumberPartiesLostMois(NumberDefaitesMois parties)
	{
		Fichier.Save(parties,Numberlost+parties.getUserName());
	}
	/**
	 * 
	 * @param parties Objet NumberMatchMois qu'on veut sauvegarder
	 */
	public static void saveNumberMatchMois(NumberMatchMois parties)
	{
		Fichier.Save(parties,Numbermatch+parties.getUserName());
	}
	/***
	 * 
	 * @param username le pseudo de l'utilisateur dont on veut recuperer ces donnees
	 * @return @see Numberof
	 */
	public static NumberOf loadNumberOf(String username)
	{
		return (NumberOf)Fichier.Charger(Numberof+username);
	}
	/***
	 * 
	 * @param username le pseudo de l'utilisateur dont on veut recuperer ces donnees
	 * @return @see NumberWinMois
	 */
	public static NumberWinMois loadNumberWinMois(String username)
	{
		return (NumberWinMois)Fichier.Charger(Numberwin+username);
	}
	/***
	 * 
	 * @param username le pseudo de l'utilisateur dont on veut recuperer ces donnees
	 * @return @see NumberPartiesMois
	 */
	public static NumberPartiesMois loadNumberPartiesMois(String username)
	{
		return (NumberPartiesMois)Fichier.Charger(Numberparties+username);
	}
	/***
	 * 
	 * @param username le pseudo de l'utilisateur dont on veut recuperer ces donnees
	 * @return @see NumberDefaitesMois
	 */
	public static NumberDefaitesMois loadNumberPartiesLostMois(String username)
	{
		return (NumberDefaitesMois) Fichier.Charger(Numberlost+username);
	}
	/***
	 * 
	 * @param username le pseudo de l'utilisateur dont on veut recuperer ces donnees
	 * @return @see NumberMatchMois
	 */
	public static NumberMatchMois loadNumberMatchMois(String username)
	{
		return (NumberMatchMois) Fichier.Charger(Numbermatch+username);
	}
	/**
	 * Mis a jour de l'ensemble des donnees de tous les utilisateurs
	 */
	public static void update()
	{
		for(Profile profile : ProfileManager.getProfiles())
		{
			NumberDefaitesMois def = new NumberDefaitesMois(profile.userName);
			NumberMatchMois match = new NumberMatchMois(profile.userName);
			NumberOf  nmb = new NumberOf(profile.userName);
			NumberPartiesMois parties = new NumberPartiesMois(profile.userName);
			NumberWinMois win = new NumberWinMois(profile.userName);
			StatManager.saveNumberPartiesLostMois(def);
			StatManager.saveNumberMatchMois(match);
			StatManager.saveNumberOf(nmb);
			StatManager.saveNumberPartiesMois(parties);
			StatManager.saveNumberWinMois(win);
		}
	}
}
