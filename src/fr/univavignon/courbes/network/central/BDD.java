package fr.univavignon.courbes.network.central;

import java.sql.*;

import fr.univavignon.courbes.common.Profile;
/**
 * @author Groupe 3
 *
 */
public class BDD 
{
	/**
	 * Constant representant le driver a charger pour la connnection
	 */
	private static final String DRIVER = "org.postgresql.Driver";
	/**
	 *Url de connection sur le serveur 
	 */
	private static final String URL = "jdbc:postgresql://pedago02a.univ-avignon.fr:5432/etd";
	/**
	 * Le login de l'utilisateur qui veut se connecter
	 */
	private static final String NAME = "uapv1601678";
	/**
	 * Le mot de passe de l'utilisateur qui veut se connecter
	 */
	private static final String PASS = "YbZUjr";
	
	/**
	 * Methode qui va nous permettre de 
	 * nous connecter la base de donnees
	 * 
	 * @return Connection
	 * @see Connection
	 */
	public static Connection Connecte()
	{
		try {
			Class.forName(DRIVER);
			try {
				Connection con = DriverManager.getConnection(URL, NAME, PASS);
			return con;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			
		}
		return null;
	}
	/**
	 * Methode va permettre a verifier 
	 * estce que l'utilisateur est deja inscri dans la base de données
	 * @param profil @see Profil
	 * @return Boolean Qui indique si l'utilisateur s'est
	 * authentifier normalement ou pas
	 * 
	 */
	public static boolean PlayerConnecte (Profile profil)
	{
		// On ajoute l'utlisateur pour que si il n'est pas dans la base on l'y met 
		AddPlayer(profil);
		String query="Select * from player where pseudo=? and password=? ";
		Connection con=Connecte();
	if(con != null)
		try {
		PreparedStatement prepare = con.prepareStatement(query);
		prepare.setString(1,profil.userName);
		prepare.setString(2,profil.password);
		ResultSet res = prepare.executeQuery();
		if( res.next())
			{
			profil.profileId = res.getInt(1);
			profil.eloRank = res.getInt(4);
			return true;
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	}
		return false;
	}
	/**
	 * Cette methode va permettre d'ajouter un utilisateur
	 * dans la base de donnees 
	 * @param profil de l utilisateur qu on veut ajouter
	 * @return true si on a ajoute l utilisateur et false si ce n est pas le cas
	 */
	public static boolean AddPlayer(Profile profil)
	{
		
		String query="Insert into ";	
		query+=" player(id_player,pseudo,pays,rang_Elo,password,date_creation)";
		query+=" values(default,?,?,?,?,now()) RETURNING id_player";
	    PreparedStatement prepare;
	    Connection con = Connecte();
	    if(con != null)
	    try {
			 prepare = con.prepareStatement(query);
			prepare.setString(1,profil.userName);
			prepare.setString(2,profil.country);
			prepare.setInt(3, profil.eloRank);
			prepare.setString(4,profil.password);
			ResultSet res = prepare.executeQuery();
			if(res.next())
			{
			profil.profileId = res.getInt(1);	
			System.out.println(res.getInt(1));
			return true;
			}
			prepare.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
				
		}
		return false;
	}
}
