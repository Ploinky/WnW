package de.jjl.wnw.desktop.gui;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable
{
	public void drawOn(GraphicsContext graphics, long frameTime);
}
