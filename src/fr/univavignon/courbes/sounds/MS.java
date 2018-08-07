package fr.univavignon.courbes.sounds;

/**
 * Moteur son
 * @author pc
 *
 */
public interface MS
{
	/**
	 * méthode qui gére le son du ramassage d'itème du type slow
	 */
	public void slow();
	/**
	 * méthode qui gére le son d'apparition d'un itème
	 */
	public void apparition();
	/**
	 * méthode qui gére le son de fond de la partie en cours
	 */
	public void game();
	/**
	 * méthode qui gére la mort des snakes
	 */
	public void death();
	/**
	 * méthode qui gére le son du ramassage d'itème du type pop_item
	 */
	public void pop_item();
	/**
	 * méthode qui gére le son du ramassage d'itème du type speed
	 */
	public void speed();
	/**
	 * méthode qui gére le son du ramassage d'itème du type fly
	 */
	public void fly();
	/**
	 * méthode qui gére le son du ramassage d'itème du type big
	 */
	public void big();
	/**
	 * méthode qui gére le son du ramassage d'itème du type upside_down
	 */
	public void upside_down();
	/**
	 * méthode qui gére le son du through_wall
	 */
	public void through_wall();
	/**
	 * méthode qui gére le son collision-snake
	 */
	public void collisionsnake();
	/**
	 * méthode qui gére le son collision-mur
	 */
	public void collisionmur();

}
