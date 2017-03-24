/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.player.Player;
import de.jjl.wnw.desktop.gui.ServerGui;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class FxServerGui implements ServerGui
{
	private ListView<Player> playerList;
	
	private Stage stage;
	
	public FxServerGui(Stage stage)
	{
		playerList = new ListView<>();
		this.stage = stage;
		stage.setScene(new Scene(playerList));
	}
	
	@Override
	public void addClient(Player p)
	{
		Platform.runLater(() -> playerList.getItems().add(p));
		playerList.refresh();
	}

	@Override
	public void removeClient(Player p)
	{
		Platform.runLater(() -> playerList.getItems().remove(p));
	}

	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
		{
			stage.show();
		}
		else
		{
			stage.hide();
		}
	}

}
