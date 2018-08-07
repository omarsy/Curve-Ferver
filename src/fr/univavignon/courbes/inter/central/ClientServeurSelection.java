package fr.univavignon.courbes.inter.central;

/**
 * 
 * 
 * @author pc
 *
 */
public interface ClientServeurSelection 
{
	/**
	 * 
	 * @return L'id de la partie courante
	 */
	public int getIdPartie();
	/**
	 * 
	 * @param Idpartie le nouveau id de la parties
	 */
	public void setIdPartie(int Idpartie);
	/**
	 * 
	 * @return l'ip du serveur
	 */
	public String getIpServer();
	/**
	 * 
	 * @return le port du serveur
	 */
	public String getPortServer();
	/**
	 * 
	 * @param portServer le port du serveur
	 */
	public void setPortServer( String portServer);
	/**
	 * 
	 * @param ipServer l'ip du serveur
	 */
	public void setIpServer(String ipServer);

}
