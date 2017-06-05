package seventh;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import seventh.client.ClientSeventhConfig;
import seventh.client.SeventhGame;
import seventh.client.gfx.VideoConfig;

public class AVfacadeClient {

	private VideoConfig vConfig;
	private DisplayMode displayMode;
	private ClientSeventhConfig config;

	public AVfacadeClient(ClientSeventhConfig config) {
		this.config = config;
	}

	public void avOption() throws Exception {
		this.vConfig = this.config.getVideo();
		this.displayMode = findBestDimensions(this.vConfig);

		LwjglApplicationConfiguration.disableAudio = true;
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.setFromDisplayMode(displayMode);
		cfg.fullscreen = vConfig.isFullscreen();
		cfg.title = "The Seventh " + SeventhGame.getVersion();
		cfg.forceExit = true;
		cfg.resizable = false;
		cfg.useGL30 = vConfig.useGL30();
		cfg.vSyncEnabled = vConfig.isVsync();

		if (!cfg.fullscreen) {
			cfg.width = 1024;
			cfg.height = 768;
		}

		new LwjglApplication(new SeventhGame(config), cfg);

	}

	/**
	 * Attempts to find the best display mode
	 * 
	 * @param config
	 * @return the best display mode
	 * @throws Exception
	 */
	private static DisplayMode findBestDimensions(VideoConfig config) throws Exception {

		if (config.isPresent()) {

			int width = config.getWidth();
			int height = config.getHeight();

			DisplayMode[] modes = LwjglApplicationConfiguration.getDisplayModes();
			for (DisplayMode mode : modes) {
				if (mode.width == width && mode.height == height) {
					return mode;
				}
			}
		}

		return LwjglApplicationConfiguration.getDesktopDisplayMode();
	}

}
