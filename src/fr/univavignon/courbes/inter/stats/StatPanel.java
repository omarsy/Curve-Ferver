package fr.univavignon.courbes.inter.stats;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.inter.simpleimpl.AbstractConfigurationPanel;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow.PanelName;
import fr.univavignon.courbes.inter.simpleimpl.profiles.ProfileManager;
import fr.univavignon.courbes.stats.NumberDefaitesMois;
import fr.univavignon.courbes.stats.NumberMatchMois;
import fr.univavignon.courbes.stats.NumberOf;
import fr.univavignon.courbes.stats.NumberPartiesMois;
import fr.univavignon.courbes.stats.NumberWinMois;
import fr.univavignon.courbes.stats.StatManager;


import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.DefaultCellEditor;

/**
 * @author matrouf
 *
 */
public class StatPanel extends AbstractConfigurationPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * bouton qui affiche la representation en graphe des nombre de parties
	 */
	private JButton bouton_nbre_partie = new JButton("nombre de partie");
	/**
	 * bouton qui affiche la representation en graphe des nombre de matchs
	 */
	private JButton bouton_nbre_matchs = new JButton("nombre de matchs");
	/**
	 * bouton qui affiche la representation en graphe des nombre de défaites
	 */
	private JButton bouton_nb_defaite = new JButton("nombre de de défaite");
	/**
	 * bouton qui affiche la representation en graphe des nombre de victoires
	 */
	private JButton bouton_nb_victoire = new JButton("nombre de victoires");
	/**
	 * Panel ou on va dessine les graphes
	 */
	private  JPanel panel=new JPanel();
	/**
	 * le graph
	 */
	private final DrawGraph graph = new DrawGraph();
	/**
	 * les stats en tableaux des joueurs 
	 */
	private final JTable table = new JTable();
	/**
	 * table des joueurs qu'on peut choisir à interpretter dans le graphe de stat 
	 */
	private JTable table_1;


	/**
	 * Create le panel qui s'affiche en appuyant sur statistiques.
	 * @param mainWindow  fenetre principale
	 */
	public StatPanel(MainWindow mainWindow) {
		super(mainWindow,"Statistique");
		bouton_nbre_partie.setBounds(23, 23, 179, 23);//placer le bouton qui nous permet de tracer le nombre de parties
		bouton_nbre_partie.addActionListener(new NmbParties(this));//activer le bouton , en appuyant dessus ca nous emmene à stat(1)
		bouton_nbre_matchs.setBounds(212, 23, 209, 23);
		bouton_nbre_matchs.addActionListener(new NmbMatchs(this));
		bouton_nb_defaite.setBounds(431, 23, 209, 23);
		bouton_nb_defaite.addActionListener(new Nmbdefaite(this));
		bouton_nb_victoire.setBounds(650, 23, 249, 23);
		bouton_nb_victoire.addActionListener(new NmbVictoire(this));
		//remplir le tableau (pour afficher les stats sous forme de stats
		table.setModel(new DefaultTableModel(
			profiles(),//cette fonction retourne toutes les informations des joueurs (nom_joueur,id,classement...) 
			new String[] {
				"nom_joueur ","id_joueur","classement_elo", "nb_parties_jouées", "victoire sur l'adversaire", "pourcentage_victoire",
				"General Cause defaite","Match Perdu"
			}//les titres de la table 
		));
        JScrollPane scrollPane = new JScrollPane(table);//on a utilisé JScrollpane pour afficher les titres de table
        scrollPane.setBounds(38, 73, 807, 172);
		//table.getTableHeader().setVisible(true);
		table.setBounds(42, 177, 149, 265);
		setLayout(null);
		add(bouton_nbre_matchs);//ajouter les boutons sur le panel
		add(bouton_nbre_partie);
		add(bouton_nb_defaite);
		add(bouton_nb_victoire);
		add(scrollPane);//ajouter le tableau
		panel.setBounds(38, 389, 807, 358);
		add(panel);
		table_1 = new JTable();//créer une nouvelle table qui nous permet de selectionner les utilisateurs qu'on veut afficher
		table_1.setModel(new DefaultTableModel(
				 profilesSelection(),//cette fonction nous renvoi les nom des joueurs et leurs id 
				new String[] {
					"nom_joueur ","id_joueur","Choisir"
				}
			));
		table_1.setBounds(38, 284, 807, 69);
		JScrollPane scrollPane1 = new JScrollPane(table_1);//pour afficher les titres
		scrollPane1.setBounds(28, 284, 807, 69);
		table_1.getTableHeader().setVisible(true);
		table_1.getColumnModel().getColumn(2).setCellRenderer(
                new MyCellRenderer());
		table_1.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JCheckBox()));//lorsque je 
		add(scrollPane1);
		
		JButton btnNewButton = new JButton("UPDATE DATA");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StatManager.update();//mettre à jour les stats 
				}
		});
		btnNewButton.setBounds(881, 112, 168, 23);
		add(btnNewButton);
		this.backButton.setVisible(true);
		this.backButton.setBounds(this.getWidth(), 787, 168, 23);
		add(this.backButton);
		validate();

	}
	/**
	 * @return les Joueurs sélectionnés
	 */
	public String[] getNameSelected()
	{
		String name[] = new String[table_1.getRowCount()];
		int j=0;
		for(int i = 0; i<table_1.getRowCount();i++)
			if((boolean) table_1.getValueAt(i, 2))//si le joueur i est coché
			name[j++] =(String)table_1.getValueAt(i, 0);//on rajoute le nom du joueur dans la table name
		return name;//on retourne name
	}
	/**
	 * la fonction qui renvoi les utilisateurs
	 * @return utilisateurs
	 */
	public Object[][] profiles() 
	{
		int n = ProfileManager.getProfiles().size();
		Object [][] ob = new Object[n][8]  ;
		int i = 0;
		for(Profile profile : ProfileManager.getProfiles())
		{
			ob[i][0] = profile.userName;//recuperer les noms des joueurs 
			ob[i][1] = profile.eloRank; 
			ob[i][2] = profile.profileId;
			NumberOf b = StatManager.loadNumberOf(profile.userName);//recuperer les données de la classe numberof
			if(b == null)
			{
				b = new NumberOf(profile.userName);//
			}
			ob[i][3] =b.getNumberparties(); 
			ob[i][4] =b.getNumbrematchwin();
			ob[i][5] =b.getAvgpartiewin(); 
			ob[i][6] =b.getMostcausedefeat(); 
			ob[i][7] =b.getNumberpartieslost(); 
			i++;
		}
	return ob;
	}	
	/**
	 * la fonction qui renvoi les profils qu'on peut selectionner
	 * @return utilisateurs
	 */
	public Object[][] profilesSelection()//
	{
		int n = ProfileManager.getProfiles().size();
		Object [][] ob = new Object[n][3]  ;
		int i = 0;
		for(Profile profile : ProfileManager.getProfiles())
		{
			ob[i][0] = profile.userName;
			ob[i][1] = profile.profileId;
			ob[i][2] = new Boolean(true);
			i++;
		}
	return ob;
	}	
/**
 * Evenement listener pour le b
 * @author matrouf
 *
 */
public class NmbParties implements ActionListener
{
	/**
	 * 
	 */
	StatPanel a;
	/**
	 * constructeur 
	 * @param a StatPanel
	 */
	public NmbParties(StatPanel a)
	{
		this.a=a;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String data[] = a.getNameSelected();
		for(int i=0;i<data.length && data[i]!=null;i++)
		{
			
			NumberPartiesMois p = StatManager.loadNumberPartiesMois(data[i]);//recuperer les données
			a.graph.addXY(p.getMois(), p.getNmbParties(),data[i]);//envoyer les données au graphs pour le dessin
		}
		a.graph.reinit("Nombre de parties par mois", "Mois","Nombre de Parties");
		a.graph.setBounds(0, 0, 477, 349);
		a.panel.removeAll();
		a.panel.setLayout(null);
		a.panel.add(a.graph);
		a.panel.updateUI();
		a.updateUI();
		a.panel.repaint();

	}
	
}
/**
 * @author matrouf
 *
 */
public class Nmbdefaite implements ActionListener
{
	/**
	 * 
	 */
	StatPanel a;
	/**
	 * @param a Statpanel
	 */
	
	public Nmbdefaite(StatPanel a)
	{
		this.a=a;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	
		String data[] = a.getNameSelected();
		for(int i=0;i<data.length && data[i]!=null;i++)
		{
			
			NumberDefaitesMois p = StatManager.loadNumberPartiesLostMois(data[i]);
			a.graph.addXY(p.getMois(), p.getNmbDefaites(),data[i]);
		}
		a.graph.reinit("Nombre de defaites par mois", "Mois","Nombre de defaites");
		a.graph.setBounds(0, 0, 477, 349);
		a.panel.removeAll();
		a.panel.setLayout(null);
		a.panel.add(a.graph);
		a.panel.updateUI();
		a.updateUI();
		a.panel.repaint();
		
	}

}

/**
 * @author matrouf
 *
 */
public class NmbMatchs implements ActionListener
{
	/**
	 * 
	 */
	StatPanel a;
	/**
	 * @param a StatPanel
	 */
	public NmbMatchs(StatPanel a)
	{
		this.a=a;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String data[] = a.getNameSelected();
		for(int i=0;i<data.length && data[i]!=null;i++)
		{
			System.out.println(data[i]);
			NumberMatchMois p = StatManager.loadNumberMatchMois(data[i]);
			a.graph.addXY(p.getMois(), p.getNmbMatch(),data[i]);
		}
		a.graph.reinit("Nombre de Matches Par Mois", "Mois","Nombre de matches");
		graph.setBounds(0, 0, 477, 349);
		a.panel.removeAll();
		a.panel.setLayout(null);
		a.panel.add(graph);
		a.panel.updateUI();
		a.updateUI();
		a.panel.repaint();

	}
}
/**
 * 
 * @author pc
 *
 */
	public class NmbVictoire implements ActionListener
	{
		/**
		 * 
		 */
		StatPanel a;
		/**
		 * 
		 * @param a StatPanel
		 */
		public NmbVictoire(StatPanel a)
		{
			this.a=a;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String data[] = a.getNameSelected();
			for(int i=0;i<data.length && data[i]!=null;i++)
			{
				System.out.println(data[i]);
				NumberWinMois p = StatManager.loadNumberWinMois(data[i]);
				a.graph.addXY(p.getMois(), p.getWin(),data[i]);
			}
			a.graph.reinit("Nombre de Victoire Par Mois", "Mois","Nombre de victoire");
			graph.setBounds(0, 0, 477, 349);
			a.panel.removeAll();
			a.panel.setLayout(null);
			a.panel.add(graph);
			a.panel.updateUI();
			a.updateUI();
			a.panel.repaint();

			
		}
	
	}

	@Override
	protected void initContent() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void nextStep() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void previousStep() {
		// TODO Auto-generated method stub
		mainWindow.displayPanel(PanelName.MAIN_MENU);
	}
/**
	*Classe qui nous permet d'ajouter les checkbox dans la table de choix
	*/
	public class MyCellRenderer extends DefaultTableCellRenderer {
		 /**
		  * 
		  */
        private static final long serialVersionUID = 1L;
 
        @Override
		public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
 
            if (value instanceof JComboBox) {
                return (JComboBox<?>) value;
            }
            if (value instanceof Boolean) {
                JCheckBox cb = new JCheckBox();
                cb.setSelected(((Boolean) value).booleanValue());
                return cb;
            }
            if (value instanceof JCheckBox) {
                return (JCheckBox) value;
            }
            return new JTextField(value.toString());
        }
 
    }

}

