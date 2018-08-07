package fr.univavignon.courbes.inter.central;

import fr.univavignon.courbes.network.central.ClientToCentral;

/**
 * Thread permettant au client de chercher de chercher des seveurs disponible
 * au niveau du serveur central
 * @author pc
 *
 */
public  class WhatServerConnect implements Runnable
{
	
	/**
	 * Temps de rafraichissement
	 */
	public final int TIMESLEEP=1000;
	/**
	 * 
	 */
	ClientServerSelectionPanel serverpanel;
	/**
	 * 
	 */
	ClientToCentral callcentral;
	/**
	 * 
	 * @param serverpanel le panel appelant le thread pour la recherche au niveau du serveur central
	 * des serveur disponiblie
	 */
	public  WhatServerConnect (ClientServerSelectionPanel serverpanel)
	{
	this.serverpanel = serverpanel;	
	callcentral = new ClientToCentral();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(serverpanel.continu )
		{
		
			if (callcentral.GetServerAvailable())
			{	
			serverpanel.setIpServer(callcentral.getIpServer());
			serverpanel.setPortServer(callcentral.getPortServer());
			serverpanel.setIdPartie(callcentral.getIdPartie());
			serverpanel.nextStep();;
			
			}
			try {
				Thread.sleep(TIMESLEEP);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
