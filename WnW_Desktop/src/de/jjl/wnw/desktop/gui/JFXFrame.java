package de.jjl.wnw.desktop.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class JFXFrame extends GridPane
{
	private int rowIndex;

	private int colIndex;

	private Node lastChild;

	private GUI parent;

	protected List<GuiListener> listeners;

	public JFXFrame()
	{
		rowIndex = 0;
		colIndex = 0;
		listeners = new ArrayList<>();
		setAlignment(Pos.CENTER);
	}

	public JFXFrame nextRow()
	{
		rowIndex++;
		colIndex = 0;
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
		setVGrow(Priority.ALWAYS);
		setHGrow(Priority.ALWAYS);
		GridPane.setHalignment(lastChild, HPos.CENTER);
		GridPane.setValignment(lastChild, VPos.CENTER);
		return this;
	}

	public JFXFrame setVGrow(Priority v)
	{
		GridPane.setVgrow(lastChild, v);
		return this;
	}

	public JFXFrame setHGrow(Priority v)
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

	public void setParentGUI(GUI parent)
	{
		this.parent = parent;
	}

	public GUI getParentGUI()
	{
		return parent;
	}

	public void addListener(GuiListener g)
	{
		listeners.add(g);
	}

	public void removeListener(GuiListener g)
	{
		listeners.remove(g);
	}

	protected void fireEvent(Consumer<GuiListener> c)
	{
		listeners.forEach(c::accept);
	}
}
