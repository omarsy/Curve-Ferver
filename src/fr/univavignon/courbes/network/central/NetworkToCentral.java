package fr.univavignon.courbes.network.central;

import java.sql.*;
import java.util.Vector;

import fr.univavignon.courbes.common.Player;
import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.stats.ELO;

/**
 * Cette classe va nous permettre de 
 * @author pc
 *
 */
public class NetworkToCentral {



	/**
	 * Lier  le server au central pour que les utilisateurs puissent s y connecter automatiquement
	 * 
	 * @param profil @see Profile 
	 *  profile  de l'utilisateur qui veut partager son reseau 
	 * @param ip l'adresse ip du serveur
	 * @param port le port du serveur
	 * @return l id de la partie creer et -1 si l operation ne s est pas deroule normalement
	 */
	public static int MakeServerPublic(Profile profil,String ip,String port)
	{
	    if(BDD.PlayerConnecte(profil))
	    {
	    Connection con = BDD.Connecte();
		String query="Insert into partie(id_partie,id_player,ip_serveur,port,date,complet) ";	
		query+=" values(default,?,?,?,now(),false) RETURNING id_partie";
		PreparedStatement prepare;
		try 
		{
			prepare = con.prepareStatement(query);
			/////////////////////////////////
			/////Preparer les requetes /////
			//////////////////////////////
			prepare.setInt(1,profil.profileId);
			prepare.setString(2, ip);
			prepare.setString(3,port);
			//true si il a ecrit une ligne et false si ce nest pas le cas
		ResultSet res = prepare.executeQuery();
		if(res.next())
		{
			return res.getInt(1);
		}
		else
		{
			return -1;
		}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    
	    return -1;
	    }
	/**
	 * Permet de faire unn mise a jour a la base de
	 * donnee et signaler aux joueurs que la partie est
	 * complet
	 * @param idPartie correspond a l'id de la partie courante
	 */
   public void PartieBegin(int idPartie)
   {
	   Connection con = BDD.Connecte();
	   String query="Update into partie set complet=true where id_partie=?";
	   PreparedStatement prepare;
	try {
		prepare = con.prepareStatement(query);
		prepare.setInt(1, idPartie);
	prepare.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
   }
   /**
 * @param players l'ensemble de players
 * @param i le numero  du joeur qu on veut recuperre
 * @return le rang d'un joueur iu donne
 */
public static int rang(Player [] players ,int i)
   {
	   int points= players[i].totalScore;
	   int rang = 1; 
	   for(int j = 0 ; j<players.length; j++)
	   {
		   if(points < players[j].totalScore)
		   {
			   rang++;
		   }
	   }
	   return rang;
   }
/**
 * Permet de calculer le  nouveau des joeurs et le met a jour au niveau de la basse de donnee
 * @param players Les joueurs qu'on veut calculer leur nouveau classement
 * @see Player
 */
	public static  void updateElo(Player [] players)
	{
		String queryelo ="Update  player set rang_elo=? where pseudo=?"; 
		 Connection con = BDD.Connecte();
		   PreparedStatement prepare;
		for(int i = 0;i<players.length;i++)
		{
		   try {
			prepare = con.prepareStatement(queryelo);
			System.out.println(ELO.calculELOF(players, i));
			players[i].profile.eloRank = ELO.calculELOF(players, i);
			prepare.setInt(1, players[i].profile.eloRank);
			prepare.setString(2,  players[i].profile.userName);
		prepare.executeUpdate();
		prepare.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
   /**
    * Permettre de mettre les donnees a la fin du match dans la BDD
    * @param idPartie L'id de la partie courante
    * @param players les jouants participant a la partie actuelle 
    * @param EliminateBy les informations sur l'eliminations des joueurs pour chaque manche
    */
   public static void PartieEnd(int idPartie,Player [] players,Vector<Integer []> EliminateBy)
   {
	  String querypartie = "Insert into player_in_partie(id_partie,id_player,points,rang) values";
      String querymanche = "Insert into manche(id_partie,id_player,eliminatedby,numero_manche) values";
      for(int i = 0 ;i < players.length ; i++)
   {
	  querypartie +="(?,?,?,?)";
	  for(int j = 0 ;j < EliminateBy.size(); j++)
	  {
		  querymanche +="(?,?,?,?)";  
		  if (j < EliminateBy.size() - 1 || i < players.length - 1)
		  {
			  querymanche +=" ,";  
		  }
	  }
   if(i < players.length - 1 )
	   querypartie += " ,"; 
   }
   Connection con=BDD.Connecte();
   PreparedStatement prepare;
try {
	String query = querypartie + ";" + querymanche;
	prepare = con.prepareStatement(query);
	int numero = 1;
	for(int i = 0 ;i <players.length ; i++)
   {
   prepare.setInt(numero++, idPartie);
   prepare.setInt(numero++, players[i].profile.profileId);
   prepare.setInt(numero++, players[i].totalScore);
   prepare.setInt(numero++, rang(players,i));
   }
	for(int j = 0 ;j <players.length ; j++)
	   {
		for(int g = 0 ;g < EliminateBy.size(); g++)
		  {
			 
	   prepare.setInt(numero++, idPartie);
	   prepare.setInt(numero++, players[j].profile.profileId);
	   prepare.setInt(numero++,EliminateBy.get(g)[j]==null ? 0 :EliminateBy.get(g)[j] ); 
	   prepare.setInt(numero++, g);
		  }
		
	   }
	prepare.executeUpdate();
	updateElo(players);
	System.out.println(prepare.toString());
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	
}

   }
   /**
    * Methode permettant de supprimer une partie
    * Cette methode sera appele lorsque l'utilidateur
    * aura detecte un serveur ne marche pas 
    * @param idPartie  coorespond l' id de la partie qu'on 
    * veut supprimer
    **/
   public static void DeletePartie(int idPartie)
   {
	   Connection con = BDD.Connecte();
	   String query="Delete from partie where id_partie=?";
	   PreparedStatement prepare;
	try {
		prepare = con.prepareStatement(query);
		prepare.setInt(1, idPartie);
	prepare.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
 
 
   /**
    * Informer aux autres utilisateurs que la partie
    * est complet
 * @param idPartie
 * L'id de la partie courante
 * @return
 * True si l'operation c est realise
 * False si l'operation ne c est pas realise
 */
public static boolean putComplet(int idPartie)
   {
	   Connection con = BDD.Connecte();
	   String query = "Update partie set complet=true where id_partie=?";
	   PreparedStatement prepare;
	try {
		prepare = con.prepareStatement(query);
		prepare.setInt(1, idPartie);
	return prepare.executeUpdate() != 0;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return false;
   }

}
