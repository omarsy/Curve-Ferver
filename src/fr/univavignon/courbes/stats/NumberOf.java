package fr.univavignon.courbes.stats;

import java.io.Serializable;

import fr.univavignon.courbes.network.central.Stats;
/**
 * L'ensemble des donnes qui caraterise les statistiques d'un joueur
 * nottament sur le nombre de parties et de matches jouees gagnes perdu
 * @author pc
 *
 */
public class NumberOf implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * pseudo du profil
	 */
	String username;
	/**
	 * Nombre de matches joues
	 */
	private int numbrematch;
	/**
	 * nombre de matche gagne
	 */
	private int numbrematchwin;
	/**
	 * nombre de matche perdu
	 */
	private int numberpartieslost;
	/**
	 * nombre de parties jouees
	 */
	private int numberparties;
	/**
	 * La cause de ces defaites en genereal
	 */
	private int mostcausedefeat;
	/**
	 * La moyenne des parties gagnees
	 */
	private double avgpartiewin;
	
	
	/**
	 * @param username le pseudo de l'utilisateur
	 */
	public  NumberOf(String username)
	{
		this.username = username;
		updateAll();
	}
	/**
	 * 
	 * @return nombre de match
	 */
	public int getNumbrematch() {
		return numbrematch;
	}
	/**
	 * Permmet de faire un mis a jour au niveau de la base de donnees pour le nombre de jouer
	 * 
	 */
	public void updateNumbrematch() {
		numberparties=Stats.GetNumberParties(username);
	}
	/**
	 * 
	 * 
	 * @return nombre de matche gagne
	 */
	public int getNumbrematchwin() {
		return numbrematchwin;
	}
	/**
	 * 
	 * Mis a jour du nombre de match gagne au niveau de la base de donnes
	 */
	public void updateNumbrematchwin() {
		this.numbrematchwin = Stats.GetNumberPartiesWin(username);
	}
	/**
	 * 
	 * @return nombre de parties perdues
	 */
	public int getNumberpartieslost() {
		return numberpartieslost;
	}
	/**
	 * Mis a jour au niveau de la base de donnnes  pour le nombre de parties perdues
	 */
	public void updateNumberpartieslost() {
		numberpartieslost=Stats.GetNumberPartiesLost(username);;
	}
	/**
	 * 
	 * @return nombre de parties
	 */
	public int getNumberparties() {
		return numberparties;
	}
	/**
	 * 
	 *Mis a jour du nombre de parties joues
	 */
	public void updateNumberparties() {
		numberparties=Stats.GetNumberParties(username);
	}
	/**
	 * 
	 * @return ce qui le plus sa defaite
	 */
	public int getMostcausedefeat() {
		return mostcausedefeat;
	}
	/**
	 * 
	 * Mis a jour de la cause de defaite
	 */
	public void updateMostcausedefeat() {
		this.mostcausedefeat=Stats.GetCausesdefeat(username);
	}
	/**
	 * 
	 * @return la moyennne des parties gagnees
	 */
	public double getAvgpartiewin() {
		return avgpartiewin;
	}
	/**
	 * Mis a jour de la moyenne des parties gangnees
	 * 
	 */
	public void updateAvgpartiewin() {
	avgpartiewin = (numberparties==0)?0:numbrematchwin/numberparties;
	}
	/**
	 * Mis a jour l'ensemble des donnees du nombreof
	 * @return  le pseudo de l utilisateur
	 */
	public String getUserName()
	{
		return username;
	}
	/**
	 * Mis a jour de l'ensemble des donnees caracterisant un Numberof
	 */
	public void updateAll()
	{
		updateMostcausedefeat();
		updateNumberparties();
		updateNumberpartieslost();
		updateNumbrematchwin();
		updateAvgpartiewin();
	}

}
