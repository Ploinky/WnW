package de.jjl.wnw.desktop.game;

public interface FrameListener
{
	public void requestSceneChange(String newFrame);

	public void requestConnect(String host, String name);
}
