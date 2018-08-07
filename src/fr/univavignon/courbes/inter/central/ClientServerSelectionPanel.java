package fr.univavignon.courbes.inter.central;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import fr.univavignon.courbes.inter.ClientConnectionHandler;
import fr.univavignon.courbes.inter.simpleimpl.AbstractConfigurationPanel;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow;
import fr.univavignon.courbes.inter.simpleimpl.SettingsManager;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow.PanelName;
import fr.univavignon.courbes.inter.simpleimpl.SettingsManager.NetEngineImpl;
import fr.univavignon.courbes.network.ClientCommunication;
import fr.univavignon.courbes.network.central.NetworkToCentral;
import fr.univavignon.courbes.network.kryonet.ClientCommunicationKryonetImpl;
import fr.univavignon.courbes.network.simpleimpl.client.ClientCommunicationImpl;
/**
 * Panel afficher lorsque le client est a la recherche d'un serveur disponible de maniere centralise
 * @author pc
 *
 */
public class ClientServerSelectionPanel extends AbstractConfigurationPanel implements ClientServeurSelection , ClientConnectionHandler 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Titre du panel
	 */
	private static final  String TITLE = "Recherche d'un Serveur" ;
	/**
	 * L'id de la partie choisie
	 */
	private int idPartie;
	/**
	 * L'ip du serveur
	 */
	private String ipServer;
	/**
	 * Le port du serveur
	 */
	private String portServer;
	/**
	 * Thread permettant de rechercher les serveur disponible
	 */
	private Thread whatserver;
	/**
	 * Boolean nous permettant d'arreter la recherche du serveur :
	 * true si on n'a pas encore trouve 
	 * false si on a trouve
	 */
	public boolean continu =true;
	/**
	 * 
	 * @param mainWindow fentre principale
	 */
	public ClientServerSelectionPanel(MainWindow mainWindow) 
	{
		super(mainWindow, TITLE);
		nextButton.setEnabled(false);
		// TODO Auto-generated constructor stub
	whatserver= new Thread(new WhatServerConnect(this));
	whatserver.start();
	}
	/**
	 * 
	 * @return true si on est arrive a nous connecte 
	 * et false si ce n'est pas le cas
	 */
	private boolean connect()
	{	// on initialise le Moteur Réseau
		ClientCommunication clientCom = null;
		NetEngineImpl netEngineImpl = SettingsManager.getNetEngineImpl();
		switch(netEngineImpl)
		{	case KRYONET:
				clientCom = new ClientCommunicationKryonetImpl();
				break;
			case SOCKET:
				clientCom = new ClientCommunicationImpl();
				break;
		}
		
		mainWindow.clientCom = clientCom;
		clientCom.setErrorHandler(mainWindow);
		clientCom.setConnectionHandler(this);
		
		String ipStr = this.getIpServer();
		clientCom.setIp(ipStr);
		SettingsManager.setLastServerIp(ipStr);
		
		String portStr = this.getPortServer();
		int port = Integer.parseInt(portStr);
		clientCom.setPort(port);
		SettingsManager.setLastServerPort(port);
		
		// puis on se connecte
		boolean result = clientCom.launchClient();
		return result;
	}
	@Override
	protected void initContent() {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void nextStep() {
boolean connected = connect();
		
		if(connected)
		{	// on désactive les boutons le temps de l'attente
			backButton.setEnabled(false);
			nextButton.setEnabled(false);
		
			// puis on se contente d'attendre la réponse : acceptation ou rejet
			// la méthode correspondante du handler sera automatiquement invoquée
		}
		
		else
		{	
		NetworkToCentral.DeletePartie(getIdPartie());
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void previousStep() {
		// TODO Auto-generated method stub
		continu = false;
		mainWindow.clientCom = null;
		mainWindow.displayPanel(PanelName.CLIENT_GAME_PLAYER_SELECTION);
	}
	@Override
	public String getIpServer() {
		// TODO Auto-generated method stub
		return ipServer;
	}
	@Override
	public String getPortServer() {
		// TODO Auto-generated method stub
		return portServer;
	}
	@Override
	public void setPortServer(String portServer) {
		// TODO Auto-generated method stub
		this.portServer = portServer;
	}
	@Override
	public void setIpServer(String ipServer) {
		// TODO Auto-generated method stub
		this.ipServer = ipServer;
	}
	@Override
	public void gotRefused()
	{	SwingUtilities.invokeLater(new Runnable()
		{	@Override
			public void run()
			{	JOptionPane.showMessageDialog(mainWindow, 
					"<html>Le serveur a rejeté votre candidature, car il ne reste "
					+ "<br/>pas de place dans la partie en cours de configuration.</html>");
			//Suppresion d'un serveur qui n'est plus en ligne
			}
	    });
	}
	
	@Override
	public void gotAccepted()
	{	SwingUtilities.invokeLater(new Runnable()
		{	@Override
			public void run()
			{	continu = false;
				mainWindow.clientCom.setConnectionHandler(null);
				mainWindow.displayPanel(PanelName.CLIENT_GAME_WAIT);
			}
	    });
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

	
}
