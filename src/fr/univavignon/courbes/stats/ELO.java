package fr.univavignon.courbes.stats;


import fr.univavignon.courbes.common.Player;
/**
 * Classe contenant toutes les methodes permettant de caculer le classement ELO
 * @author pc
 *
 */
public class ELO {
/**
 * Calcul de la probabilité de gagner du joueur P1
 * @param elo1 classement elo du joueur 1
 * @param elo2 classement elo du joi=ueur 2
 * @return la probalite que le joueur 2 gagne
 */
	public static double estimation(int elo1,int elo2)
	{
		double exp = (elo1 - elo2) /400;
		return 1/(1+Math.pow(10, exp)); 
	
	}
	/**
	 * 
	 * @param elo1 le clasement elo du joueur1
	 * @param elo2 le clasement elo du joueur1
	 * @param score le score entr p1 et p2
	 *score = 1 si P1 gagne
	*score = 0 si P1 perd
	*score = 0.5 s'il y a match nul
	 * @return le nouveau elo si les deux joueur jouees
	 */
	public static double calculEloP1(int elo1,int elo2,double score)
	{
		int k;
		k = valeurK(elo1);
		double estimation = estimation(elo1,elo2);
		double nrang =  (elo1 + k * (score - estimation));
		if(nrang < 300)
		{
			nrang = 300;
		}
		
		return nrang;
	}
	/**
	 * 
	 * @param elo Rang elo
	 * @return Valeur K en fontion de la cote du joeur
	 */
	public static int  valeurK( int elo)
	{
		int k=0;
		if (elo < 1000)
		{
			k = 80;
		}
		if (elo >= 1000 && elo < 2000)
		{
			k = 50;
		}
		if (elo >= 2000 && elo <= 2400)
		{
			k = 30;
		}
		if (elo > 2400)
		{
			k = 20;
		}
		return k;
	}
	/**
	 * 
	 * @param a L'ensemble des joueur
	 * @param j indice du joueur qu'on veut calcule le classement elo
	 * @return le nouveau classement ELo
	 */
	public static int calculELOF(Player[] a,int j) 
	{
		int tmp = a.length;
		double nrang = 0;
		double score=0;
		for(int i=0;i<tmp;i++)
		{	
			
			if(i!=j)
			{
				if(a[i].totalScore<a[j].totalScore)
					score=1;
				else if (a[i].totalScore==a[j].totalScore)
					score=0.5;
				else if (a[i].totalScore>a[j].totalScore)
					score=0;
				nrang += calculEloP1(a[j].profile.eloRank, a[i].profile.eloRank, score);

			}
		 
	}
		
		return (int) Math.round(nrang /(tmp-1));
		
	}
}