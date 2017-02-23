package de.jjl.wnw.desktop.gui;

/**
 * Represents the view of the game.
 *
 * @author johannes.litger
 */
public interface GUI
{
	public void show();

	public void hide();

	public void setTitle(String title);

	public void setScene(JFXFrame root);
	
	public void addListener(GuiListener g);

	public void removeListener(GuiListener g);
}
