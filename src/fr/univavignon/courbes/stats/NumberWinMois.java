package fr.univavignon.courbes.stats;

import java.io.Serializable;

import fr.univavignon.courbes.network.central.Stats;
/**
 * Classe caracterisant le statistique de chaque profil
 * sur le nombre de parties gagnees
 * @author pc
 *
 */
public class NumberWinMois implements Serializable {
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
	 * le nombre de parties gagnees par mois
	 */
private int[] nmbgagne;
/**
 * 
 * @param username le pseudo du profil dont on veut creer le statistique du profil
 */
public NumberWinMois (String username)
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
 * @return le nombre de parties gagnees par mois
 */
public int[] getWin()
{
	return nmbgagne;
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
	int data[][] =  Stats.getNumberPartiesWinByMois(username);
	if(data != null)
	{
	Mois=data[1];
	nmbgagne=data[0];
	}
	else
	{
		Mois = null;
		nmbgagne = null;
	}
}

}
