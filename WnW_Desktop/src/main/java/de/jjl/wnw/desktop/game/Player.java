package de.jjl.wnw.desktop.game;

import de.jjl.wnw.dev.game.GameObject;

public interface Player extends GameObject
{
	public void faceLeft();

	public int getLives();

	public boolean isFaceLeft();
}
