package de.jjl.wnw.desktop.controls;

import java.util.ArrayList;
import java.util.List;

import de.jjl.wnw.base.rune.parser.WnWPathInputParser;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser.Grid;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.base.util.path.WnWPoint;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class ResultPanel extends Pane 
{
	public static final PseudoClass rune = PseudoClass.getPseudoClass("rune");
	
	private ObjectProperty<WnWPath> path;
	private ObjectProperty<WnWPath> runePath;
	private ObjectProperty<Grid> grid;
	
	private WnWDesktopPath pPath;
	private WnWDesktopPath pRunePath;
	private List<Shape> sGrid;
	
	public ResultPanel()
	{
		sGrid = new ArrayList<>();
		path = new SimpleObjectProperty<WnWPath>(this, "path", null)
			{
				@Override
				protected void invalidated()
				{
					if(pPath != null)
					{
						getChildren().remove(pPath.getFXPath());
					}
					
					if(get() != null)
					{
						pPath = new WnWDesktopPath(get().getSystem());
						get().forEach(pPath::addPoint);
						getChildren().add(0, pPath.getFXPath());
						pPath.getFXPath().getStyleClass().add("path");
						pPath.getFXPath().setStroke(new Color(0.5, 0.5, 0.5, 0.5));
					}
				}
			};
		runePath = new SimpleObjectProperty<WnWPath>(this, "runePath", null)
			{
				@Override
				protected void invalidated()
				{
					if(pRunePath != null)
					{
						getChildren().remove(pRunePath.getFXPath());
					}
					
					if(get() != null && getGrid() != null && getPath() != null)
					{
						int pathX = getPath().getPathMinX();
						int pathY = getPath().getPathMinY();
						
						pRunePath = new WnWDesktopPath(get().getSystem());
						for(WnWPoint p : get())
						{
							pRunePath.addPoint(
									(int)(pathX + getGrid().getStartX() + ((p.getX() + 0.5) * getGrid().getFieldWidth())),
									(int)(pathY + getGrid().getStartY() + ((p.getY() + 0.5) * getGrid().getFieldHeight())));
						}
						getChildren().add(pRunePath.getFXPath());
						pRunePath.getFXPath().getStyleClass().add("path");
						pRunePath.getFXPath().pseudoClassStateChanged(rune, true);
					}
				}
			};
		grid = new SimpleObjectProperty<WnWPathInputParser.Grid>(this, "grid", null)
			{
				@Override
				protected void invalidated()
				{
					if(!sGrid.isEmpty())
					{
						getChildren().removeAll(sGrid);
					}
					
					sGrid.clear();
									
					if(get() != null && getPath() != null)
					{
						int pathX = getPath().getPathMinX();
						int pathY = getPath().getPathMinY();
						
						for(int col = 0; col < get().getCols() + 1; ++col)
						{
							Line line = new Line(
									pathX + get().getStartX() + (col * get().getFieldWidth()),
									pathY + get().getStartY(),
									pathX + get().getStartX() + (col * get().getFieldWidth()),
									pathY + get().getStartY() + (get().getRows() * get().getFieldHeight()));
							line.getStyleClass().add("grid-line");
							sGrid.add(line);
						}
						for(int row = 0; row < get().getRows() + 1; ++row)
						{
							Line line = new Line(
									pathX + get().getStartX(),
									pathY + get().getStartY() + (row * get().getFieldHeight()),
									pathX + get().getStartX() + (get().getCols() * get().getFieldWidth()),
									pathY + get().getStartY() + (row * get().getFieldHeight()));
							line.getStyleClass().add("grid-line");
							sGrid.add(line);
						}
						
						for(int col = 0; col < get().getCols(); ++col)
						{
							for(int row = 0; row < get().getRows(); ++row)
							{
								Ellipse center = new Ellipse(
										pathX + get().getStartX() + ((col + 0.5) * get().getFieldWidth()),
										pathY + get().getStartY() + ((row + 0.5) * get().getFieldHeight()),
										0.05 * get().getFieldWidth() * get().getTolerance() / 100,
										0.05 * get().getFieldHeight() * get().getTolerance() / 100);
								center.getStyleClass().add("grid-center");
								
								Ellipse fieldArea = new Ellipse(
										pathX + get().getStartX() + ((col + 0.5) * get().getFieldWidth()),
										pathY + get().getStartY() + ((row + 0.5) * get().getFieldHeight()),
										get().getFieldWidth() * 0.5 * get().getTolerance() / 100,
										get().getFieldHeight() * 0.5 * get().getTolerance() / 100);
								fieldArea.getStyleClass().add("grid-area");
								
								sGrid.add(center);
								sGrid.add(fieldArea);
							}
						}
						
						getChildren().addAll(sGrid);
					}
				}
			};
	}
	
	public final ObjectProperty<WnWPath> pathProperty() { return path; }
	public final WnWPath getPath() { return path.get(); }
	public final void setPath(WnWPath value) { path.set(value); }
	
	public final ObjectProperty<WnWPath> runePathProperty() { return runePath; }
	public final WnWPath getRunePath() { return runePath.get(); }
	public final void setRunePath(WnWPath value) { runePath.set(value); }
	
	public final ObjectProperty<Grid> gridProperty() { return grid; }
	public final Grid getGrid() { return grid.get(); }
	public final void setGrid(Grid value) { grid.set(value); }
}
