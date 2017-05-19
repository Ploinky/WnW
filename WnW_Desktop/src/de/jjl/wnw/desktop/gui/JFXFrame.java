package de.jjl.wnw.desktop.gui;

import de.jjl.wnw.desktop.game.FrameListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class JFXFrame extends GridPane
{
	private int rowIndex;

	private int colIndex;

	private Node lastChild;

	protected FrameListener listener;

	public JFXFrame(FrameListener listener)
	{
		setPrefSize(1650, 1080);
		rowIndex = 0;
		colIndex = 0;
		this.listener = listener;
		setAlignment(Pos.CENTER);
		setPadding(new Insets(10, 10, 10, 10));
	}

	public JFXFrame nextRow()
	{
		rowIndex++;
		colIndex = 0;
		return this;
	}

	public JFXFrame colBuffer()
	{
		add(new Pane()).vGrow(Priority.NEVER).hGrow(Priority.ALWAYS);

		return this;
	}

	/**
	 * Adds a {@code Node} to the Frame, using the following default values:
	 *
	 * <ul>
	 * <li>Column Span: <b>Remaining</b>
	 * <li>Vertical Grow: <b>Always</b>
	 * <li>Horizontal Grow: <b>Always</b>
	 * <li>Vertical Alignment: <b>Center</b>
	 * <li>Horizontal Alignment: <b>Center</b>
	 * </ul>
	 */
	public JFXFrame add(Node n)
	{
		add(n, colIndex++, rowIndex, 1, 1);
		lastChild = n;
		vGrow(Priority.ALWAYS);
		hGrow(Priority.ALWAYS);
		GridPane.setHalignment(lastChild, HPos.CENTER);
		GridPane.setValignment(lastChild, VPos.CENTER);
		return this;
	}

	public JFXFrame addFrame()
	{
		JFXFrame newFrame = new JFXFrame(listener);
		newFrame.setPrefSize(0, 0);
		add(newFrame);
		return newFrame;
	}

	public JFXFrame rowBuffer()
	{
		nextRow();
		add(new Pane()).vGrow(Priority.ALWAYS).hGrow(Priority.NEVER);
		nextRow();
		return this;
	}

	public JFXFrame vGrow(Priority v)
	{
		GridPane.setVgrow(lastChild, v);
		return this;
	}

	public JFXFrame hGrow(Priority v)
	{
		GridPane.setHgrow(lastChild, v);
		return this;
	}

	public JFXFrame setAligment(HPos hAlignment, VPos vAlignment)
	{
		GridPane.setHalignment(lastChild, hAlignment);
		GridPane.setValignment(lastChild, vAlignment);
		return this;
	}

	public JFXFrame setColSpan(int colSpan)
	{
		GridPane.setColumnSpan(lastChild, colSpan);
		return this;
	}
}
