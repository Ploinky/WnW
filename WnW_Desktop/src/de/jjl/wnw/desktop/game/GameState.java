package de.jjl.wnw.desktop.game;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameState {
	private GameState instance;
	private StringProperty currentLanguage;
	// TODO Create player Object and replace the ObjectType of the List
	private SimpleListProperty<Object> connectedPlayers;

	private GameState() {
		currentLanguage.set("en");
		ObservableList<Object> observableList = FXCollections.observableArrayList();
		this.connectedPlayers = new SimpleListProperty<Object>(observableList);
	}

	public GameState getInstance() {
		if (instance == null) {
			instance = new GameState();
		}

		return instance;
	}

	public String getCurrentLanguage() {
		if (this.currentLanguage != null)
			return currentLanguage.get();
		return null;
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage.set(currentLanguage);
	}

	public boolean addConnectedPlayer(Object connectedPlayer) {
		if (connectedPlayer == null || connectedPlayers == null || connectedPlayers.get() == null
				|| connectedPlayers.get().contains(connectedPlayer))
			return false;
		connectedPlayers.get().add(connectedPlayer);
		return true;
	}

	public boolean removeConnectedPlayer(Object disconnectedPlayer) {
		if (disconnectedPlayer == null || connectedPlayers == null || connectedPlayers.get() == null
				|| !connectedPlayers.get().contains(disconnectedPlayer))
			return false;
		connectedPlayers.get().remove(disconnectedPlayer);
		return true;
	}
}
