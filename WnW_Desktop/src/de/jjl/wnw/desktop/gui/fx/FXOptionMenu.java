package de.jjl.wnw.desktop.gui.fx;

import java.util.Arrays;

import de.jjl.wnw.base.cfg.Settings;
import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Language;
import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import de.jjl.wnw.desktop.gui.fx.comp.FXButton;
import de.jjl.wnw.desktop.gui.fx.comp.FXLabel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class FXOptionMenu extends JFXFrame
{

	public FXOptionMenu(FrameListener listener)
	{
		super(listener);
		init();
	}

	private void init()
	{
		FXLabel lblSettings = new FXLabel(Const.TITLE_SETTINGS);
		lblSettings.setFont(Const.FONT_TITLE);
		add(lblSettings).vGrow(Priority.ALWAYS);

		nextRow();

		JFXFrame frameLang = addFrame();
		setVgrow(frameLang, Priority.SOMETIMES);
		frameLang.setHgap(10);

		FXLabel lblLang = new FXLabel("LblLang");
		lblLang.setFont(Const.FONT_DEFAULT);
		frameLang.add(lblLang).vGrow(Priority.SOMETIMES).setAligment(HPos.RIGHT, VPos.CENTER);

		ComboBox<Language> combLang = new ComboBox<>();
		combLang.setStyle("-fx-font: 18 'Comic Sans MS' ");
		frameLang.add(combLang).vGrow(Priority.SOMETIMES).setAligment(HPos.LEFT, VPos.CENTER);
		Arrays.asList(Language.values()).forEach(l ->
		{
			combLang.getItems().add(l);
		});
		combLang.getSelectionModel().select(Settings.get().getLanguage());
		combLang.getSelectionModel().selectedItemProperty().addListener((p, o, n) ->
		{
			Settings.get().setLanguage(n);
			Translator.get().changeLocale(n.getLocale());
		});

		nextRow();

		add(new Pane()).vGrow(Priority.ALWAYS);

		nextRow();

		FXButton btnBack = new FXButton("BtnBack");
		add(btnBack).vGrow(Priority.SOMETIMES);
		btnBack.setOnAction(a ->
		{
			listener.requestSceneChange(Const.MENU_MAIN);
		});

		nextRow();

		add(new Pane()).vGrow(Priority.ALWAYS);
	}
}
