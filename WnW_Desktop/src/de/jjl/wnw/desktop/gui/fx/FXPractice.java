package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.util.*;
import de.jjl.wnw.desktop.controls.DrawPanel;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

public class FXPractice extends JFXFrame implements InvalidationListener
{
	private GridPane chain;

	public FXPractice(FrameListener listener)
	{
		super(listener);
		
		init();
	}

	private void init()
	{
		chain = new GridPane();
		add(chain).vGrow(Priority.NEVER).hGrow(Priority.SOMETIMES).setAlignment(Pos.BASELINE_RIGHT);
		chain.setHgap(10);
		chain.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
		chain.setMinHeight(102);
		chain.setBackground(new Background(new BackgroundFill(Paint.valueOf("white"), CornerRadii.EMPTY, Insets.EMPTY)));
		
		nextRow();
		
		DrawPanel p = new DrawPanel();
		p.addListener(this);
		add(p).vGrow(Priority.ALWAYS).hGrow(Priority.ALWAYS);
	}

	@Override
	public void invalidated(Observable observable)
	{
		DrawPanel p = null;
		
		if(observable instanceof DrawPanel)
		{
			p = (DrawPanel) observable;
		}
		else
		{
			return;
		}
		
		Pane pane = new Pane();
		pane.getChildren().add(p.getPath().getFXPath(100, 100));
		pane.setMaxSize(110, 110);
		pane.setMinSize(110, 110);
		pane.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		chain.add(pane, chain.getChildren().size(), 0);
	}

}
