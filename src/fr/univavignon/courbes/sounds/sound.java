package fr.univavignon.courbes.sounds;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;




/**
 * Classe qui implémente l'interface MS permettant d'intégrer
 * des méthodes du sons. 
 * 
 * @author	Azzouzi Rachid
 */

public class sound extends Thread implements MS
{
	/**
	 * 
	 */
	public static Mixer mixer;
	/**
	 * 
	 * 
	 */
	public static Clip clip;
	/**
	 * 
	 */
	private String name;

 /**
 * méthode qui gére le son d'apparition d'un itème
 * 
 * @author	Azzouzi Rachid
 */
	@Override public void apparition()
	{
		this.name = "res/sounds/apparitionitem.wav";

	}

	/**
	 * méthode qui gére le son du ramassage d'itème du type big
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void big()
	{
		this.name="res/sounds/big.wav";

	}
	/**
	 * méthode qui gére le son du ramassage d'itème du type fly
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void fly()
	{
		this.name="res/sounds/fly.wav";

	}
	/**
	 * méthode qui gére le son de fond de la partie en cours
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void game()
	{
		this.name="res/sounds/fresh-sparks.wav";

	}
	/**
	 * méthode qui gére le son du ramassage d'itème du type speed
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void speed()
	{
		this.name="res/sounds/speed.wav";

	}
	/**
	 * méthode qui gére le son du ramassage d'itème du type slow
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void slow()
	{
		this.name="res/sounds/speed.wav";

	}
	/**
	 * méthode qui gére les colisions des snakes
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void death()
	{
		this.name="res/sounds/colision-mur.wav";

	}
	/**
	 * méthode qui gére le son du ramassage d'itème du type pop_item
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void pop_item()
	{
		this.name="res/sounds/pop.wav";

	}
	/**
	 * méthode qui gére le son du ramassage d'itème du type upside_down
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void upside_down()
	{
		this.name="res/sounds/through_wall.wav";

	}
	
	/**
	 * méthode qui gére le son collision-snake
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void collisionsnake()
	{
		this.name="res/sounds/colision_snake.wav";

	}
	
	
	/**
	 * méthode qui gére le son collision-snake
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void collisionmur()
	{
		this.name="res/sounds/colision-mur.wav";

	}
	/**
	 * méthode qui gére le son du through_wall
	 * 
	 * @author	Azzouzi Rachid
	 */

	@Override public void through_wall()
	{
		this.name="res/sounds/through_wall.wav";

	}
	
	/**
	 * méthode qui joue le son en lancant le sans dans un thread crée 
	 * auparavant
	 * 
	 * @author	Azzouzi Rachid
	 */
	@Override public void run()
	{
		File file = new File(name);
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		for(Mixer.Info info : mixInfos)
		System.out.println(info.getName() + "--" + info.getDescription());
	
		mixer = AudioSystem.getMixer(mixInfos[0]);
		DataLine.Info datainfo = new DataLine.Info(Clip.class, null);
		try { clip = (Clip)mixer.getLine(datainfo);}
		catch ( LineUnavailableException lue ) { lue.printStackTrace();}
	
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(file);
			clip.stop();
			clip.open(audio);
		}
		catch( LineUnavailableException lue ) { lue.printStackTrace();}
		catch(UnsupportedAudioFileException uafe) { uafe.printStackTrace();}
		catch(IOException ioe) { ioe.printStackTrace(); }
	
		clip.start();
		do
		{
			try { Thread.sleep(50); }
			catch(InterruptedException ie ) {ie.printStackTrace();}
		}
		while(clip.isActive());
	}
	/**
	 * Met fin au son
	 */
	@SuppressWarnings("deprecation")
	public void end()
	{
		this.stop();
	}
}
