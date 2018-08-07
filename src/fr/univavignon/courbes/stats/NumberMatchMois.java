package fr.univavignon.courbes.stats;

import java.io.Serializable;

import fr.univavignon.courbes.network.central.Stats;
/**
 * Classe caracterisant le statistique de chaque profil
 * sur le nombre de matches
 * @author pc
 *
 */
public class NumberMatchMois implements Serializable {
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
	 * le nombre de matches par mois
	 */
	private int[] nmbMatch;
	/**
	 * 
	 * @param username le pseudo du profil dont on veut creer le statistique du profil
	 */
	public NumberMatchMois(String username)
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
	 * @return le nombre de matches par mois
	 */
	public int[] getNmbMatch()
	{
		return nmbMatch;
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
		int data[][] =  Stats.getNumberMatchByMois(username);
		if(data != null)
		{
		Mois=data[1];
		nmbMatch=data[0];
		}
		else
		{
			Mois = null;
			nmbMatch = null;
		}
	}
}
