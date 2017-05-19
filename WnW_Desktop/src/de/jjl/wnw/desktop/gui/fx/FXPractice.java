package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.util.*;
import de.jjl.wnw.desktop.controls.DrawPanel;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;

/**
 * Frame that allows players to practice drawing runes without any actual battle going on.
 *
 * @author johannes.litger
 */
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
		ScrollPane scroll = new ScrollPane();
		scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		add(scroll).vGrow(Priority.SOMETIMES).hGrow(Priority.SOMETIMES).setAlignment(Pos.BASELINE_RIGHT);
		
		chain = new GridPane();
		chain.setHgap(10);
		chain.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
		chain.setMinHeight(112);
		chain.setPadding(new Insets(5, 5, 5, 5));
		chain.setBackground(new Background(new BackgroundFill(Paint.valueOf("white"), CornerRadii.EMPTY, Insets.EMPTY)));
		
		scroll.setContent(chain);
		scroll.hvalueProperty().bind(chain.widthProperty());
		
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
		
		Path path = p.getPath().getFXPathScaled(100, 100);
		
		if(path.getElements().size() < 3)
		{
			// Avoiding 1 pixel paths, mostly....
			return;
		}
		
		Pane pane = new Pane();
		pane.getChildren().add(path);
		
		pane.setMaxSize(100, 100);
		pane.setMinSize(100, 100);
		
		pane.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID,
				CornerRadii.EMPTY, BorderWidths.DEFAULT, new Insets(-5, -5, -5, -5))));
		pane.setPadding(new Insets(500, 5, 5, 5));
		
		chain.add(pane, chain.getChildren().size(), 0);
	}

}
