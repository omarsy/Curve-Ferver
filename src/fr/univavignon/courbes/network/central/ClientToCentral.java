package fr.univavignon.courbes.network.central;

import java.sql.*;

import fr.univavignon.courbes.inter.central.ClientServeurSelection;
/**
 * Classe permettant au client de contacter le serveur central 
 * @author pc
 *
 */
public class ClientToCentral implements ClientServeurSelection
{

	/**
	 * l'adresse ip du client
	 */
	private String  ip  ;
	/**
	 * le port du client
	 */
	private String  port ;
	/**
	 * L'Id de la partie choisie
	 */
	private int idPartie;
	/**
	 * Cherche dans la base de donnees les serveurs disponible
	 * @return true si il a trouve et false si ce n'est pas le cas
	 */
	public boolean GetServerAvailable()
	{
		Connection con=BDD.Connecte();
		try {
			Statement state = con.createStatement();
			String query = "select ip_serveur,port,id_partie from partie where complet=false "; 
			ResultSet res = state.executeQuery(query);
		setIpServer(null);
		setPortServer(null);
		setIdPartie(-1);
		System.out.println(res);
		if(res.next())
		{
			setIpServer(res.getString(1));
			setPortServer(res.getString(2));
			setIdPartie(res.getInt(3));
			System.out.println(res.getString(2));
			return true;
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	}
	@Override
	public int getIdPartie() {
		// TODO Auto-generated method stub
		return idPartie;
	}
	@Override
	public void setIdPartie(int Idpartie) {
		// TODO Auto-generated method stub
		this.idPartie=Idpartie;
	}
	@Override
	public String getIpServer() {
		// TODO Auto-generated method stub
		return ip;
	}
	@Override
	public String getPortServer() {
		// TODO Auto-generated method stub
		return port;
	}
	@Override
	public void setPortServer(String portServer) {
		// TODO Auto-generated method stub
		this.port=portServer;
	}
	@Override
	public void setIpServer(String ipServer) {
		// TODO Auto-generated method stub
		this.ip = ipServer;
	}
}
