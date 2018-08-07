package fr.univavignon.courbes.network.central;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * Avoir les statistique depuis le serveur central
 * @author pc
 *
 */
public class Stats 
{
	/**
	 * @param username le pseudo du joueur qu'on veut recuperer ces donnees
	 * 
	 * @return le nombre de parties jouees par le joueur
	 */
	public static Integer GetNumberParties(String username)
	{
		String query="Select count(*) from player_in_partie join player using(id_player) where pseudo=? ";
		Connection con=BDD.Connecte();
		if(con != null)
		{
			try {
				PreparedStatement prepare = con.prepareStatement(query);
				prepare.setString(1,username);
				ResultSet res = prepare.executeQuery();
				if( res.next())
				{
					
					return res.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * 
	 * @param username le pseudo du joueur qu on veut recupere l'information
	 * @return la cause generale de ces  defaites
	 */
	public static Integer GetCausesdefeat(String username)
	{
		String query="Select eliminatedBy from manche join player using(id_player) where player.pseudo=? group by eliminatedBy having count(eliminatedBy)>=ALL(Select count(*) from manche join player using(id_player) where player.pseudo=? group by eliminatedBy ) Limit 1 ";
		Connection con=BDD.Connecte();
		if(con != null)
		{
			try {
				PreparedStatement prepare = con.prepareStatement(query);
				prepare.setString(1,username);
				prepare.setString(2,username);
				ResultSet res = prepare.executeQuery();
				if( res.next())
				{
					return res.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * 
	 * @param username pseudo du joueur qu'on veut recupetrer le nombre de parties qu'il a gagne
	 * @return le nombre de parties qagnees
	 */
	public  static Integer GetNumberPartiesWin(String username)
	{
		String query="Select count(*) from player_in_partie join player using(id_player) where player.pseudo=? and rang = 1 ";
		Connection con=BDD.Connecte();
		if(con != null)
		{
			try {
				PreparedStatement prepare = con.prepareStatement(query);
				prepare.setString(1,username);
				ResultSet res = prepare.executeQuery();
				if( res.next())
				{
					System.out.println(res.getInt(1));
					return res.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * 
	 * @param username le pseudo qu'on veut recuperer ces informations
	 * @return le nombre de parties perdues
	 */
	public  static Integer GetNumberPartiesLost(String username)
	{
		String query="Select count(*) from player_in_partie join player using(id_player) where pseudo=? and rang!=1 ";
		Connection con=BDD.Connecte();
		if(con != null)
		{
			try {
				PreparedStatement prepare = con.prepareStatement(query);
				prepare.setString(1,username);
				ResultSet res = prepare.executeQuery();
				if( res.next())
				{
					return res.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	/**
	 * 
	 * @param username le pseudo du profil qu'on veut recuperer ces informations
	 * @return le nombre de parties par mois dont return[0][x] correspond au nombre de parties
	 * et eturn[1][x] correspond au mois
	 */
	public static  int[][]  getNumberPartiesByMois(String username)
	{
		String query="Select count(*),date_part('month',date) from player_in_partie join player using(id_player)  join  partie using(id_partie)    group by date_part('month',date),player.id_player having player.pseudo=?";
		Connection con=BDD.Connecte();
		if(con != null)
		{
			try {
				PreparedStatement prepare = con.prepareStatement(query,ResultSet.FETCH_REVERSE,ResultSet.FETCH_FORWARD);
				prepare.setString(1,username);
				ResultSet res = prepare.executeQuery();
				int size = 0;
				while( res.next())
					size++;
				res.beforeFirst();
				System.out.println(size);
				if(size != 0)
				{
				int data[][]= new int[2][size] ;
				int i=0;
				while( res.next())
				{
					
					data[0][i]= res.getInt(1);
					data[1][i]= res.getInt(2);
					i++;
				}
				return data;
				}
				return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

/**
 * 
 * @param username Le pseudo du profill qu'on veut recuperere les informations
 * @return le nombre de parties par mois dont return[0][x] correspond au nombre de parties perdues
 * et return[1][x] correspond au mois
 */
	public static  int[][]  getNumberPartiesLostByMois(String username)
	{
		String query="Select count(*),date_part('month',date) from player_in_partie join player using(id_player) join  partie using(id_partie)  where rang!=1   group by date_part('month',date),player.id_player having player.pseudo=?";
		Connection con=BDD.Connecte();
		if(con != null)
		{
			try {
				PreparedStatement prepare = con.prepareStatement(query,ResultSet.FETCH_REVERSE,ResultSet.FETCH_FORWARD);
				prepare.setString(1,username);
				ResultSet res = prepare.executeQuery();
				int size = 0;
				while( res.next())
					size++;
				res.beforeFirst();
				System.out.println(size);
				if(size != 0)
				{
				int data[][]= new int[2][size] ;
				int i=0;
				while( res.next())
				{
					
					data[0][i]= res.getInt(1);
					data[1][i]= res.getInt(2);
					i++;
				}
				return data;
				}
				return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 
	 * @param username le pseudo du profil qu'on veut recuperer ces informations 
	 *@return le nombre de parties par mois dont return[0][x] correspond au nombre de parties gagnees
 * et return[1][x] correspond au mois
	 */
	public static  int[][]  getNumberPartiesWinByMois(String username)
	{
		String query="Select count(*),date_part('month',date) from player_in_partie join player using(id_player) join  partie using(id_partie)  where rang=1    group by date_part('month',date),player.id_player having player.pseudo=?";
		Connection con=BDD.Connecte();
		if(con != null)
		{
			try {
				PreparedStatement prepare = con.prepareStatement(query,ResultSet.FETCH_REVERSE,ResultSet.FETCH_FORWARD);
				prepare.setString(1,username);
				ResultSet res = prepare.executeQuery();
				int size = 0;
				while( res.next())
					size++;
				res.beforeFirst();
				System.out.println(size);
				if(size != 0)
				{
				int data[][]= new int[2][size] ;
				int i=0;
				while( res.next())
				{
					
					data[0][i]= res.getInt(1);
					data[1][i]= res.getInt(2);
					i++;
				}
				return data;
				}
				return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	* @param username le pseudo du profil qu'on veut recuperer ces informations 
	 *@return le nombre de parties par mois dont return[0][x] correspond au nombre de matches 
 * et return[1][x] correspond au mois
	 */
	public static  int[][]  getNumberMatchByMois(String username)
	{
		String query="Select count(*),date_part('month',date) from manche join player using(id_player) join  partie using(id_partie)   group by date_part('month',date),player.id_player having player.pseudo=?";
		Connection con=BDD.Connecte();
		if(con != null)
		{
			try {
				PreparedStatement prepare = con.prepareStatement(query,ResultSet.FETCH_REVERSE,ResultSet.FETCH_FORWARD);
				prepare.setString(1,username);
				ResultSet res = prepare.executeQuery();
				int size = 0;
				while( res.next())
					size++;
				res.beforeFirst();
				System.out.println(size);
				if(size != 0)
				{
				int data[][]= new int[2][size] ;
				int i=0;
				while( res.next())
				{
					
					data[0][i]= res.getInt(1);
					data[1][i]= res.getInt(2);
					i++;
				}
				return data;
				}
				return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}