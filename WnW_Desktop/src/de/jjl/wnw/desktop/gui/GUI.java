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

	/**
	 * Returns to the main menu of the @{code GUI}.
	 */
	public void goToMain();
}
