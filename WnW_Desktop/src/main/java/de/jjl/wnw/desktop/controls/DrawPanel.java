package de.jjl.wnw.desktop.controls;

import java.util.ArrayList;
import java.util.List;

import de.jjl.wnw.base.util.InvalidationListener;
import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.base.util.path.WnWPoint;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.scene.layout.Pane;

/**
 * This pane can be drawn on by pressing and dragging the mouse. It is used by
 * players to drawn runes.
 *
 * @author johannes.litger
 */
public class DrawPanel extends Pane implements Control
{
	private final List<InvalidationListener> listeners;

	private WnWDesktopPath path;

	private WnWDisplaySystem disSys;

	public DrawPanel()
	{
		listeners = new ArrayList<>();

		disSys = new WnWDisplaySystem(new WnWPoint(0, 0), new WnWPoint((int) getWidth(), (int) getHeight()), true,
				false);

		setOnMousePressed(e ->
		{
			if (path != null)
			{
				getChildren().remove(path.getFXPath());
			}

			path = new WnWDesktopPath(disSys);
			getChildren().add(path.getFXPath());
			path.addPoint(e);
		});

		setOnMouseDragged(e ->
		{
			if (e.getX() < 0 || e.getX() > getWidth() || e.getY() < 0 || e.getY() > getHeight())
			{
				return;
			}
			path.addPoint(e);
		});

		setOnMouseReleased(e -> listeners.forEach(l -> l.invalidated(this)));
	}

	public WnWDesktopPath getPath()
	{
		return path;
	}

	@Override
	public void addListener(InvalidationListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener)
	{
		listeners.remove(listener);
	}
}
