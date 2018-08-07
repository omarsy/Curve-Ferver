package fr.univavignon.courbes.stats;

import java.io.Serializable;

import fr.univavignon.courbes.network.central.Stats;
/**
 * Classe caracterisant le statistique de chaque profil
 * sur le nombre de defaites 
 * @author pc
 *
 */
public class NumberDefaitesMois implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  le pseudo du profil 
	 */
	private String username;
	/**
	 * Les mois
	 */
	private int Mois[];
	/**
	 * le nombre de defaites par mois
	 */
	private int[] nmbDefaites;
	
	/**
	 * 
	 * @param username le pseudo du profil dont on veut creer le statistique du profil
	 */
	public NumberDefaitesMois(String username)
	{
		this.username = username;
		update();
	}
	/**
	 * 
	 * @return les mois
	 */
	public int[] getMois()
	{
		return Mois;
	}
	/**
	 * 
	 * @return le nombre de defaites par mois
	 */
	public int[] getNmbDefaites()
	{
		return nmbDefaites;
	}
	/**
	 * 
	 * @return le pseudo de l'utilisateur
	 */
	public String getUserName()
	{
		return username;
	}
	/**
	 * Mis a jour des donnees 
	 */
	private void update()
	{
		int data[][] =  Stats.getNumberPartiesLostByMois(username);
		if(data != null)
		{
		Mois=data[1];
		nmbDefaites = data[0];
		}
		else
		{
			Mois = null;
			nmbDefaites = null;
		}
	}
}
