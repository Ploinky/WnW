package de.jjl.wnw.desktop.controls;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

public class DrawPanel extends Pane
{
	private int rows;

	private int cols;

	private Path rune;

	private List<DetSquare> squares;

	public DrawPanel(int x, int y)
	{
		squares = new ArrayList<>();

		cols = x;
		rows = y;

		drawRaster();

		rune = new Path();

		setOnMousePressed(e ->
		{
			Iterator<Node> it = getChildren().iterator();
			
			while(it.hasNext())
			{
				Node n = it.next();
				
				if(n instanceof Rectangle || n instanceof Path)
				{
					it.remove();
				}
			};
			
			getChildren().add(rune);
			
			squares.forEach(s -> s.setRuned(false));
			
			rune.getElements().clear();

			MoveTo moveTo = new MoveTo();
			moveTo.setX(e.getSceneX());
			moveTo.setY(e.getSceneY());

			rune.getElements().add(moveTo);
		});

		setOnMouseDragged(e ->
		{
			// Then start drawing a line
			LineTo lineTo = new LineTo();
			lineTo.setX(e.getSceneX());
			lineTo.setY(e.getSceneY());
			
			squares.stream().filter(s -> !s.isRuned()).filter(s -> s.isInSquare(e.getSceneX(), e.getSceneY())).forEach(s ->
			{
				Rectangle r = new Rectangle(s.getX() + 1, s.getY() + 1, s.getWidth() -2, s.getHeight() - 2);
				r.setFill(Paint.valueOf("Lightgrey"));
				s.setRuned(true);
				getChildren().add(r);
				rune.toFront();
			});
			
			rune.getElements().add(lineTo);
		});
	}

	public DrawPanel()
	{
		this(2, 3);
	}

	private void drawRaster()
	{
		setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		ChangeListener<Number> lis = (p, o, n) ->
		{
			getChildren().clear();
			squares.clear();

			for (int j = 0; j <= rows; j++)
			{
				for (int i = 0; i <= cols; i++)
				{
					DetSquare sq = new DetSquare();

					sq.setHeight(getHeight() / rows);
					sq.setWidth(getWidth() / cols);

					sq.setX(sq.getWidth() * i);
					sq.setY(sq.getHeight() * j);

					squares.add(sq);
				}
			}

			squares.forEach(sq ->
			{
				Line top = new Line(sq.getX(), sq.getY(), sq.getX() + sq.getWidth(), sq.getY());
				Line right = new Line(sq.getX() + sq.getWidth(), sq.getY(), sq.getX() + sq.getWidth(), sq.getY() + sq.getHeight());
				Line bottom = new Line(sq.getX(), sq.getY() + sq.getHeight(), sq.getX() + sq.getWidth(), sq.getY() + sq.getHeight());
				Line left = new Line(sq.getX(), sq.getY(), sq.getX(), sq.getY() + sq.getHeight());
				
				getChildren().addAll(top, right, bottom, left);
			});
		};

		heightProperty().addListener(lis);
		widthProperty().addListener(lis);
	}

	private class DetSquare
	{
		private boolean runed;
		
		private double x;

		private double y;

		private double width;

		private double height;

		public DetSquare()
		{
			this(0, 0, 0, 0);
		}

		public DetSquare(double x, double y, double width, double height)
		{
			runed = false;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public double getHeight()
		{
			return height;
		}

		public void setHeight(double height)
		{
			this.height = height;
		}

		public double getWidth()
		{
			return width;
		}

		public void setWidth(double width)
		{
			this.width = width;
		}

		public double getX()
		{
			return x;
		}

		public void setX(double x)
		{
			this.x = x;
		}

		public double getY()
		{
			return y;
		}

		public void setY(double y)
		{
			this.y = y;
		}
		
		public boolean isRuned()
		{
			return runed;
		}
		
		public void setRuned(boolean runed)
		{
			this.runed = runed;
		}
		
		public boolean isInSquare(double x, double y)
		{
			return x > getX() && x < getX() + getWidth()
				&& y > getY() && y < getY() + getHeight();
		}
	}
}
