package fr.univavignon.courbes.inter.central;

import java.util.List;

import javax.swing.JOptionPane;

import fr.univavignon.courbes.common.Round;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow;
import fr.univavignon.courbes.inter.simpleimpl.SettingsManager;
import fr.univavignon.courbes.inter.simpleimpl.local.LocalPlayerConfigPanel;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow.PanelName;
import fr.univavignon.courbes.inter.simpleimpl.remote.server.ServerGameLocalPlayerSelectionPanel;
import fr.univavignon.courbes.network.central.BDD;
import fr.univavignon.courbes.network.central.NetworkToCentral;

/**
 * Panel permettant au selection des player pour un jeu centralise cote serveur
 * @author pc
 *
 */
public class ServerCentralGameLocalPlayerSelectionPanel  extends ServerGameLocalPlayerSelectionPanel {

	/**
	 * @param mainWindow fenetre pricipale
	 */
	public ServerCentralGameLocalPlayerSelectionPanel(MainWindow mainWindow) {
		super(mainWindow);
		// TODO Auto-generated constructor stub
		System.out.println("Modification de la JCheckBox: "+SettingsManager.getLastPort()+mainWindow.serverCom.getIp());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void nextStep()
	{	if(checkConfiguration())
		{
		boolean authentifie=true;
			List<LocalPlayerConfigPanel> p =super.selectedProfiles;
			for(LocalPlayerConfigPanel pro:p)
			{
				if(!BDD.PlayerConnecte(pro.player.profile))
				{
					JOptionPane.showMessageDialog(mainWindow, 
							"<html>Erreur Authentification du player :" +
							"<br/>-" +pro.player.profile+
							"<br/>-Verifier que le mot de pass et le login correspond et que "
							+ "<br/>-votre reseau est bien connecte sur internet</html>");	
					authentifie=false;
					break;
				}
			}
			if(authentifie == true)
			{
			int publicc;
			Integer port =SettingsManager.getLastPort();
			//Relier le serveur au central
			publicc=NetworkToCentral.MakeServerPublic(p.get(0).player.profile,mainWindow.serverCom.getIp(), port.toString());
			if (publicc != -1)
			{
			Round round = initRound();
			mainWindow.idPartie = publicc;
			mainWindow.currentRound = round;
			mainWindow.displayPanel(PanelName.SERVER_CENTRAL_GAME_REMOTE_PLAYER_SELECTION);
			}
			else
			{
				JOptionPane.showMessageDialog(mainWindow, 
						"<html>Impossible de creer une partie au central" 
						+ "<br/>-Contacter l'Administrateur</html>");	
			}
			}
			}
		else
		{	JOptionPane.showMessageDialog(mainWindow, 
				"<html>Les données des joueurs locaux ne sont pas correctement remplies. Vérifiez que :" +
				"<br/>- tous les profils sont définis et différents, et que" +
				"<br/>- toutes les commandes sont définies et différentes.</html>");
		}
	}

}
