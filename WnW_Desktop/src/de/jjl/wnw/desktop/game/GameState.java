package de.jjl.wnw.desktop.game;

import de.jjl.wnw.base.player.Player;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameState
{
	private static GameState instance;
	private StringProperty currentLanguage;
	private SimpleListProperty<Player> connectedPlayers;

	private GameState()
	{
		currentLanguage.set("en");
		ObservableList<Player> observableList = FXCollections.observableArrayList();
		this.connectedPlayers = new SimpleListProperty<>(observableList);
	}

	public static GameState getInstance()
	{
		if (instance == null)
		{
			instance = new GameState();
		}

		return instance;
	}

	// Language
	public String getCurrentLanguage()
	{
		if (this.currentLanguage != null)
			return currentLanguage.get();
		return null;
	}

	public void setCurrentLanguage(String currentLanguage)
	{
		this.currentLanguage.set(currentLanguage);
	}

	public void addPropertyListenerLanguage(ChangeListener<String> listener)
	{
		if (listener == null || this.currentLanguage == null)
			return;
		this.currentLanguage.addListener(listener);
	}

	public void removePropertyListenerLanguage(ChangeListener<String> listener)
	{
		if (listener == null || this.currentLanguage == null)
			return;
		this.currentLanguage.removeListener(listener);
	}

	// ConnectedPlayers
	public boolean addConnectedPlayer(Player connectedPlayer)
	{
		if (connectedPlayer == null || connectedPlayers == null || connectedPlayers.get() == null
				|| connectedPlayers.get().contains(connectedPlayer))
			return false;
		connectedPlayers.get().add(connectedPlayer);
		return true;
	}

	public boolean removeConnectedPlayer(Player disconnectedPlayer)
	{
		if (disconnectedPlayer == null || connectedPlayers == null || connectedPlayers.get() == null
				|| !connectedPlayers.get().contains(disconnectedPlayer))
			return false;
		connectedPlayers.get().remove(disconnectedPlayer);
		return true;
	}

	public void addPropertyListenerPlayers(ChangeListener<ObservableList<Player>> listener)
	{
		if (this.connectedPlayers == null || listener == null)
			return;
		this.connectedPlayers.addListener(listener);
	}

	public void removePropertyListenerPlayers(ChangeListener<ObservableList<Player>> listener)
	{
		if (this.connectedPlayers == null || listener == null)
			return;
		this.connectedPlayers.removeListener(listener);
	}
}
