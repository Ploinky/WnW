package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.util.InvalidationListener;
import de.jjl.wnw.base.util.Observable;
import de.jjl.wnw.desktop.controls.DrawPanel;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;

public class FXPractice extends JFXFrame implements InvalidationListener
{
	private Pane chain;

	public FXPractice(FrameListener listener)
	{
		super(listener);
		
		init();
	}

	private void init()
	{
		chain = new Pane();
		add(chain).vGrow(Priority.SOMETIMES).hGrow(Priority.ALWAYS);
		chain.maxHeightProperty().bind(heightProperty().divide(8));
		chain.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"),
				BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));;
		
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
		
		if(!p.isResizable())
		{
			throw new RuntimeException("Cannot resize!");
		}
		else
		{
			// TODO Remove if no longer needed
			System.out.println("resizing");
			p.resize(100, 100);
		}
		chain.getChildren().add(p.getPath().getFXPath());
		
	}

}
