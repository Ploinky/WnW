package de.jjl.wnw.desktop.gui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class JFXFrame extends GridPane
{
	private int rowIndex;

	private int colIndex;

	private Node lastChild;

	public JFXFrame()
	{
		rowIndex = 0;
		colIndex = 0;
	}

	public JFXFrame nextCol()
	{
		colIndex++;
		return this;
	}

	public JFXFrame add(Node n)
	{
		add(n, colIndex, rowIndex++, 1, 1);
		lastChild = n;
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
}
