package fr.univavignon.courbes.inter.stats;


import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Classe permettant de dessinner les graphes
 * @author pc
 *
 */
public class DrawGraph extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * VAraible qui va contenir le chart Panel
	 */
	ChartPanel chartPanel;//le panel qui sert à afficher le graphe
	/**
	 * Le nom des courbes qu'on dessine
	 */
	String courbename[] = null;
	/**
	 * les x qui correspond aux mois
	 */
	int x[]={1,2,3,4,5,6,7,8,9,10,11,12} ;
	    /**
	     * les y qui represente les points correspondant au x
	     */
	    int y[][]=null ; 
		/**
		 * generique de type XYSeries ,ce type est propre à jfreechart
		 */
		private XYSeries generique;
		/**
		 * Titre du graphe
		 */
		private String title;
		/**
		 * intitule de l'axe X
		 */
		private String axeX;
		/**
		 * inititule de l'axe Y
		 */
		private String axeY;
	 /**
	  *la fonction principal 
	 * @param title : le titre du graphe
	 * @param axeX : le nom de l'axe x
	 * @param axeY : le nom de l'axe y
	 */
	public void reinit(String title, String axeX,String axeY )
	 {
		 this.title = title;
		 this.axeX = axeX;
		 this.axeY = axeY;
		 XYDataset dataset = createDataset(x,y);//appel de la fonction qui crée la base de données
		  JFreeChart chart = createChart(dataset);//appel de fonction qui crée le graph
	      ChartPanel chartPanel = new ChartPanel(chart);
	     chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	     this.removeAll();//on efface le panel pour mettre le nouveau chartpanel
		 y=null;
	     add(chartPanel); //on ajoute le charpanel sur le panel
	 }
	 /**
	  * la fonction qui crée le graph 
	 * @param dataset : la base de données
	 * @return le graph 
	 */
	private JFreeChart createChart(final XYDataset dataset) {
	        
	        // create the chart...
	        final JFreeChart chart = ChartFactory.createXYLineChart(
	            title,      // le titre su panel
	            axeX,                      // l'axe x
	            axeY,                      // l'axe y
	            dataset,                  // base de données
	            PlotOrientation.VERTICAL,
	            true,                     // on inclue les legendes 
	            true,                     // tooltips
	            false                     // urls
	        );

	   
	        chart.setBackgroundPaint(Color.white);


	       final XYPlot plot = chart.getXYPlot();
	        plot.setBackgroundPaint(Color.black);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setRangeGridlinePaint(Color.white);
	        return chart;
	        
	    }
	 /**
	  * fonction qui crée la base de données a partir des points x et y 
	 * @param x les donnees de l axe x
	 * @param y les donnees de l axe y
	 * @return XYDataset
	 */
	private XYDataset createDataset(int []x,int [][]y )
	   {
		  XYSeriesCollection dataset = new XYSeriesCollection();
		 	
		  	for(int i=0;i<y.length;i++)//pour lister tout les joueurs
	     	{
		 		generique = new XYSeries(this.courbename[i]);//initialiser la serie XY generique avec les valeur de x et de y
		 		for(int j=0;j<x.length;j++)//pour lister les points de chaque joueurs
		 		{
		 			generique.add(x[j],y[i][j]);
		 			 System.out.println(i+" "+y[i][j]);
		 		}
		 		dataset.addSeries(generique);//rajouter la serie XY à la collection de série
		 		
	     	}

	        return dataset;
	   }
/**
 * Permet d'ajouter les donnees
 * @param x correspond a l'axe x
 * @param y correpond a l'axe y
 * @param name le nom du courbe
 */
	 public void addXY(int x[], int y[],String name)
	 {
		 
		 int ny[][]= new int[(this.y != null)?this.y.length+1:1][12];
		 String newname[] = new String[(this.y != null)?this.y.length+1:1];
		 int size = (x != null)?x.length:0;
		 int ysize = (this.y != null)?this.y.length:0;
		 for(int i = 0; i<ysize;i++)
		 {
			 ny[i] = this.y[i]; 
			 newname[i] = this.courbename[i];
		 }
		 for(int i=0 ; i<12;i++)
		 {
			 ny[(this.y != null)?this.y.length:0][i]=0;
		 }
		 for(int i=0 ; i<size;i++)
		 {
			 ny[(this.y != null)?this.y.length:0][x[i] - 1]=y[i];
			
		 }
		 newname[(this.y != null)?this.y.length:0] = name;
		 this.y=ny;
		 this.courbename = newname;
	}

}