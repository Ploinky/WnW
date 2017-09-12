package de.jjl.wnw.desktop.controls;

import java.util.ArrayList;
import java.util.List;

import de.jjl.wnw.base.rune.parser.WnWPathInputParser;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser.Grid;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.desktop.util.WnWDesktopPath;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class ResultPanel extends Pane 
{
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
						pPath.getFXPath().setStroke(new Color(0.5, 0.5, 0.5, 0.5));
					}
				}
			};
		runePath = new SimpleObjectProperty<WnWPath>(this, "runePath", null)
			{
				@Override
				protected void invalidated()
				{
					
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
					
					if(get() != null)
					{
						for(int col = 0; col < get().getCols() + 1; ++col)
						{
							Line line = new Line(
									get().getStartX() + (col * get().getFieldWidth()),
									get().getStartY(),
									get().getStartX() + (col * get().getFieldWidth()),
									get().getStartY() + (get().getRows() * get().getFieldHeight()));
							line.setStroke(Color.BLACK);
							line.setStrokeWidth(2d);
							sGrid.add(line);
						}
						for(int row = 0; row < get().getRows() + 1; ++row)
						{
							Line line = new Line(
									get().getStartX(),
									get().getStartY() + (row * get().getFieldHeight()),
									get().getStartX() + (get().getCols() * get().getFieldWidth()),
									get().getStartY() + (row * get().getFieldHeight()));
							line.setStroke(Color.BLACK);
							line.setStrokeWidth(2d);
							sGrid.add(line);
						}
						
						for(int col = 0; col < get().getCols() + 1; ++col)
						{
							for(int row = 0; row < get().getRows() + 1; ++row)
							{
								Ellipse center = new Ellipse(
										get().getStartX() + ((col + 0.5) * get().getFieldWidth()),
										get().getStartY() + ((row + 0.5) * get().getFieldHeight()),
										10d,
										10d);
								center.setStroke(Color.BLUE);
								center.setStrokeWidth(1.5);
								
								Ellipse fieldArea = new Ellipse(
										get().getStartX() + ((col + 0.5) * get().getFieldWidth()),
										get().getStartY() + ((row + 0.5) * get().getFieldHeight()),
										get().getFieldWidth() * 0.5 * get().getTolerance() / 100,
										get().getFieldHeight() * 0.5 * get().getTolerance() / 100);
								fieldArea.setFill(new Color(1d, 0d, 0d, 0.25));
								
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
