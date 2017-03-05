package de.jjl.wnw.desktop.gui.fx;

import java.util.Arrays;

import de.jjl.wnw.base.cfg.Settings;
import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Language;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import de.jjl.wnw.desktop.gui.fx.comp.FXButton;
import de.jjl.wnw.desktop.gui.fx.comp.FXLabel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
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
		FXLabel lblSettings = new FXLabel("TitleOptions");
		lblSettings.setFont(Const.FONT_TITLE);
		add(lblSettings);

		nextRow();

		JFXFrame frameLang = addFrame();
		frameLang.setHgap(10);
		vGrow(Priority.SOMETIMES);

		FXLabel lblLang = new FXLabel("LblLang");
		lblLang.setFont(Const.FONT_DEFAULT);
		frameLang.add(lblLang).setAligment(HPos.RIGHT, VPos.CENTER);

		ComboBox<String> combLang = new ComboBox<>();
		combLang.setStyle("-fx-font: 18 'Comic Sans MS' ");
		frameLang.add(combLang).vGrow(Priority.SOMETIMES).setAligment(HPos.LEFT, VPos.CENTER);
		Arrays.asList(Language.values()).forEach(l ->
		{
			combLang.getItems().add(l.toString());
		});
		combLang.getSelectionModel().select(Settings.get().getLanguage().toString());

		nextRow();

		FXButton btnBack = new FXButton("BtnBack");
		add(btnBack).vGrow(Priority.SOMETIMES);

	}
}
