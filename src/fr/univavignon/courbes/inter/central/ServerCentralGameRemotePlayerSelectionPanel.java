package fr.univavignon.courbes.inter.central;

import javax.swing.JOptionPane;

import fr.univavignon.courbes.common.Round;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow.PanelName;
import fr.univavignon.courbes.inter.simpleimpl.remote.server.ServerGameRemotePlayerSelectionPanel;
/**
 * Panel permettant a la selection des joueurs a une partie reseau relie au serveur
 * @author pc
 *
 */
public class ServerCentralGameRemotePlayerSelectionPanel extends ServerGameRemotePlayerSelectionPanel {
/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

/**
 * initialisation de la fenetre principale
 *  * @param mainWindow fenetre principale
 *  
 */
	public ServerCentralGameRemotePlayerSelectionPanel(MainWindow mainWindow) {
		super(mainWindow);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected synchronized void nextStep()
	{	if(checkConfiguration())
		{	mainWindow.serverCom.setProfileHandler(null);
			Round round = initRound();
			mainWindow.currentRound = round;
			mainWindow.serverCom.sendRound(round);
			// on laisse un peu de temps aux clients
			// (pas très propre, vaudrait mieux faire une vraie synchro) 
			try
			{	Thread.sleep(500);
			}
			catch (InterruptedException e)
			{	e.printStackTrace();
			}
			mainWindow.displayPanel(PanelName.SERVER_CENTRAL_GAME_PLAY);
//			mainWindow.serverCom.sendRound(round);
		}
		else
		{	JOptionPane.showMessageDialog(mainWindow, 
				"<html>Tous les joueurs distants n'ont pas été sélectionnés.<br/>"
				+ "Ajoutez de nouveaux joueurs ou diminuez le nombre de joueurs distants.</html>");
		}
	}
}
