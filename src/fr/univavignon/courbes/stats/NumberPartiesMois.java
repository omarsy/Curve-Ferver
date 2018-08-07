package fr.univavignon.courbes.stats;

import java.io.Serializable;

import fr.univavignon.courbes.network.central.Stats;
/**
 * Classe caracterisant le statistique de chaque profil
 * sur le nombre de parties
 * @author pc
 *
 */
public class NumberPartiesMois implements Serializable{
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
	 * le nombre de parties par mois
	 */
	private int[] nmbParties;
	/**
	 * 
	 * @param username le pseudo du profil dont on veut creer le statistique du profil
	 */
	public NumberPartiesMois(String username)
	{
		this.username= username;
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
	 * @return le nombre de parties par mois
	 */
	public int[] getNmbParties()
	{
		return nmbParties;
	}
	/**
	 * 
	 * @return le pseudo du joueur
	 */
	public String getUserName()
	{
		return username;
	}
	/**
	 * Mis a jour des donneess
	 */
	private void update()
	{
		int data[][] =  Stats.getNumberPartiesByMois(username);
		if(data != null)
		{
		Mois=data[1];
		nmbParties=data[0];
		}
		else
		{
			Mois=null;
			nmbParties=null;
		}
	}
}
