package de.jjl.wnw.dev.game;

public interface ServerListener
{
	public void playerConnected(GamePlayer player);
	
	public void playerDisconnected(GamePlayer player);
	
	public void refreshPlayers();
}
