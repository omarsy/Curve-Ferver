package fr.univavignon.courbes.stats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Classe permettant de gerer les fichiers
 * @author pc
 *
 */
public class Fichier {

 

  /**
 * @param data l'objet qu'on veut sauvegarder
 * @param file_name le nom du fichier
 */
public static void Save(Object data,String file_name) {
 ObjectOutputStream oos;
	  try {
		  Fichier.Create(file_name);
	oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream( new File(file_name))));
System.out.println(data.toString());
	oos.writeObject(data);
	oos.close();
	System.out.println(data.toString());
  } catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  }
  /**
   * Supprimer le fichier
   * @param file_name
   * nom du fichier
   * @return
   * True si il est supprime
   * Et false s'il n'est pas supprime
 * @throws Exception en cas d'erreur
   */

  public static boolean Delete(String file_name) throws Exception {
	   File file = new File(file_name);
	 //Vérifier si le fichier existe
	         if (!file.exists()) {
	             throw new Exception("le fichier est introuvable !");
	         }
	 //Tester les propriétés et les droits sur le fichier
	         if (!file.canWrite()) {
	             throw new Exception("Droit insuffisant pour accéder au fichier");
	         }
	  
	         return file.delete();
  }
/**
 * Creer un fichier
 * @param file_name
 * nom du fichier
 * @return
 * true si il a cree le fichier 
 * Et false si le fichier n'a pas ete cree
 */
  public static boolean Create(String file_name) {
	  File fichier = new File(file_name);
      try {
		if (fichier.createNewFile())
		   return true;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;  
  }
/**
 * Charger un fichier
 * @param file_name
 * nom du fichier
 * @return
 * null s'il n' pas ete charge
 * Et un Objet si il a ete cree 
 */
  public static Object Charger(String file_name) {
	  try {
		@SuppressWarnings("resource")
		ObjectInputStream ois = new ObjectInputStream(
		          new BufferedInputStream(
		            new FileInputStream(
		              new File(file_name))));
		try {
			
		
			Object obj=ois.readObject();
			ois.close();
			return obj;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  return null;
  }

}