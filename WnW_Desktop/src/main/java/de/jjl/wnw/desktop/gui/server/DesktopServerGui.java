package de.jjl.wnw.desktop.gui.server;

import de.jjl.wnw.dev.game.ServerGameInstance;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DesktopServerGui extends Application
{	
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setScene(new Scene(new BorderPane()));
		primaryStage.show();
		
		ServerGameInstance server = ServerGameInstance.getInstance();
	}
}
