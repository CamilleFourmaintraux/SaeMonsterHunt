package fr.univlille.info.J2.main.utils.resources;

import java.io.File;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MediaLoader {
	
	/**
     * Pour les logs
     */
	private static final Logger LOGGER = Logger.getLogger(Generators.class.getName());
	
	/**
     * Chemin du répertoire où sont stockées les images des thèmes.
     */
	private static final String SOUND_DIRECTORY_PATH = "./res/sound/";
	
	public static void playSound(String name) {
		try {
	        File soundFile = new File(SOUND_DIRECTORY_PATH+name);
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	        Clip clip = AudioSystem.getClip();
	
	        // Ouvrir le flux audio et charger le son dans le Clip
	        clip.open(audioInputStream);
	
	        // Lire le son
	        clip.start();
	
	        // Attendre la fin de la lecture
	        while (!clip.isRunning()) {
	            Thread.sleep(10);
	        }
	        while (clip.isRunning()) {
	            Thread.sleep(10);
	        }
	
	        // Fermer le Clip une fois la lecture terminée
	        clip.close();
	    } catch (Exception e) {
	        LOGGER.info(e.getMessage());
	    }
	}
		
}

