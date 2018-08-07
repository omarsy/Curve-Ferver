package fr.univavignon.courbes.inter.central;

import fr.univavignon.courbes.inter.simpleimpl.MainWindow;
import fr.univavignon.courbes.inter.simpleimpl.remote.server.ServerGameRoundPanel;
import fr.univavignon.courbes.network.central.NetworkToCentral;

/**
 * Correspond au round  lorsque l'utilisateur est connecte au central
 * @see ServerGameRoundPanel
 * @author pc
 *
 */
public class ServerCentralGameRoundPanel  extends ServerGameRoundPanel
{

	/**
	 * Initialisation du panel
	 * @param mainWindow correspond au fenetre Principale
	 */
	public ServerCentralGameRoundPanel(MainWindow mainWindow) {
		super(mainWindow);
		// TODO Auto-generated constructor stub
		// Informer le central que la partie vient de commencer
		NetworkToCentral.putComplet(mainWindow.idPartie);
	}

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	@Override
public void run()
{
	super.run();
	NetworkToCentral.PartieEnd(super.mainWindow.idPartie, round.players, super.partieliminated);
	// Envoie  au central les informations correpondant des informations
}
}
