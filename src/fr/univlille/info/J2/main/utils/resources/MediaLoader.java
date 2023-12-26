package fr.univlille.info.J2.main.utils.resources;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MediaLoader {
	private static final String SOUND_DIRECTORY_PATH = "./res/sound/";
	public static MediaLoader media_loader;
	private static MediaPlayer media_player;
	
	public static void playSound(String name) {
		System.out.println(SOUND_DIRECTORY_PATH+name);

		System.out.println(new File(".").getAbsolutePath());
		Media media = new Media(new File(SOUND_DIRECTORY_PATH+name).toURI().toString());
		media_player = new MediaPlayer(media);
		media_player.play();
	}
	
	public static MediaLoader getMediaLoader() {
		if(MediaLoader.media_loader==null) {
			media_loader = new MediaLoader();
		}
		return media_loader;
	}
}
