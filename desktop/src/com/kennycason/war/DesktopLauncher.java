package com.kennycason.war;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.kennycason.war.util.ErrorLogger;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("軍略");
//		config.setWindowIcon(Files.FileType.Internal, "sprite/icon_32x32.png"); // add icon for windows app
        config.setIdleFPS(0);
        config.useVsync(true);

        final int width = (int) (Constants.WIDTH * Constants.SCALE);
        final int height = (int) (Constants.HEIGHT * Constants.SCALE);
        config.setWindowedMode(width + 320, height);

        final WarGdx game = new WarGdx();

        try {
            new Lwjgl3Application(game, config);
        } catch (final Exception e) {
            new ErrorLogger().log(e);
        }
    }
}
