/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.gui.frames;

import java.io.IOException;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import de.jjl.wnw.base.rune.parser.WnWPathInputParser;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser.Config;
import de.jjl.wnw.base.rune.parser.WnWPathInputParser.GridCorner;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.desktop.controls.DrawPanel;
import de.jjl.wnw.desktop.controls.ResultPanel;
import de.jjl.wnw.desktop.game.Game;
import de.jjl.wnw.desktop.gui.Frame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

public class PracticeFrame extends Frame
{
	@FXML
	private Spinner<Integer> spnGridWidth;
	@FXML
	private Spinner<Integer> spnGridHeight;
	@FXML
	private Spinner<Integer> spnMinHeight;
	@FXML
	private Spinner<Integer> spnMaxHeight;
	@FXML
	private Spinner<Integer> spnMinWidth;
	@FXML
	private Spinner<Integer> spnMaxWidth;
	@FXML
	private Spinner<Integer> spnTolerance;
	@FXML
	private CheckBox chkMoveTwice;
	@FXML
	private CheckBox chkMoveBack;
	@FXML
	private ComboBox<WnWPathInputParser.GridCorner> cmbCorner;
	@FXML
	private DrawPanel pnlDraw;
	@FXML
	private ResultPanel pnlRes;
	@FXML
	private AnchorPane drawBox;

	private Config config;

	public PracticeFrame(Game game)
	{
		super(game);
	}

	@Override
	public Parent getAsNode()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setController(this);

		try
		{
			return loader.load(getClass().getResourceAsStream("/xml/PRACTICE.fxml"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new RuntimeException("Error loading practice-frame");
	}

	@FXML
	private void initialize()
	{
		config = new Config();

		pnlDraw.minWidthProperty().bind(drawBox.widthProperty().divide(2));
		pnlDraw.maxWidthProperty().bind(drawBox.widthProperty().divide(2));

		pnlRes.minWidthProperty().bind(drawBox.widthProperty().divide(2));
		pnlRes.maxWidthProperty().bind(drawBox.widthProperty().divide(2));

		initSpinner(spnGridWidth, config::getGridWidth, config::setGridWidth, 0, 20);
		initSpinner(spnGridHeight, config::getGridHeight, config::setGridHeight, 0, 20);
		initSpinner(spnMinHeight, config::getMinFieldHeight, config::setMinFieldHeight, 10, 1000);
		initSpinner(spnMaxHeight, config::getMaxFieldHeight, config::setMaxFieldHeight, 10, 1000);
		initSpinner(spnMinWidth, config::getMinFieldWidth, config::setMinFieldWidth, 10, 1000);
		initSpinner(spnMaxWidth, config::getMaxFieldWidth, config::setMaxFieldWidth, 10, 1000);
		initSpinner(spnTolerance, config::getFieldTolerance, config::setFieldTolerance, 0, 150);

		chkMoveTwice.setSelected(config.getMoveFieldTwice());
		chkMoveTwice.selectedProperty().addListener((p, o, n) ->
		{
			config.setMoveFieldTwice(n);
			recalcPath();
		});

		chkMoveBack.setSelected(config.getMoveFieldBack());
		chkMoveBack.selectedProperty().addListener((p, o, n) ->
		{
			config.setMoveFieldBack(n);
			recalcPath();
		});

		cmbCorner.getItems().addAll(GridCorner.values());
		cmbCorner.setValue(config.getCorner());
		cmbCorner.valueProperty().addListener((p, o, n) ->
		{
			config.setCorner(n);
			recalcPath();
		});

		pnlDraw.setOnPathDrawn(e -> recalcPath(e.getPath()));
	}

	private void initSpinner(Spinner<Integer> spinner, IntSupplier supp, IntConsumer cons, int min, int max)
	{
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, supp.getAsInt()));
		spinner.valueProperty().addListener((p, o, n) ->
		{
			cons.accept(n);
			recalcPath();
		});

	}

	public void recalcPath(WnWPath path)
	{
		pnlRes.setPath(path);
		recalcPath();
	}

	public void recalcPath()
	{
		// TODO $ddd 12.09.2017
		// WnWPath path = pnlRes.getPath().trimmed();
		// pnlRes.setGrid(new WnWPathInputParser().buildGrid(path, config));
		// pnlRes.setRunePath(new WnWPathInputParser().filterRunePath(path,
		// config, pnlRes.getGrid()));
	}

}
