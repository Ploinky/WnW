package de.jjl.wnw.desktop.gui.def;

import java.util.Arrays;

import de.jjl.wnw.base.cfg.Options;
import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.base.lang.Language;
import de.jjl.wnw.base.lang.Translator;
import de.jjl.wnw.desktop.gui.JFXFrame;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Priority;

public class DefaultOptionsMenu extends JFXFrame
{
	public DefaultOptionsMenu()
	{
		init();
	}

	private void init()
	{
		setPrefSize(800, 600);
		setVgap(10);

		DefaultLabel lblTitle = new DefaultLabel(Const.TITLE_OPTIONS);
		lblTitle.setFont(Const.FONT_TITLE);
		add(lblTitle).vGrow(Priority.SOMETIMES);

		nextRow();

		JFXFrame frmLang = addFrame();
		frmLang.setVgap(10);
		frmLang.setHgap(10);
		vGrow(Priority.SOMETIMES);

		DefaultLabel lblLang = new DefaultLabel("LblLang");
		lblLang.setFont(Const.FONT_DEFAULT);
		frmLang.add(lblLang).vGrow(Priority.SOMETIMES).setAligment(HPos.RIGHT, VPos.CENTER);

		ComboBox<Language> combLang = new ComboBox<Language>();
		Arrays.asList(Language.values()).forEach(l -> combLang.getItems().add(l));
		combLang.setStyle("-fx-font: 18 \"Comic Sans MS\";");
		frmLang.add(combLang).vGrow(Priority.SOMETIMES).setAligment(HPos.LEFT, VPos.CENTER);

		combLang.getSelectionModel().selectedItemProperty().addListener((p, o, n) ->
		{
			Options.get().setLanguage(n);
			Translator.get().changeLocale(n.getLocale());
		});

		combLang.getSelectionModel().select(Options.get().getLanguage());

		nextRow();

		DefaultButton btnBack = new DefaultButton("BtnBack");
		add(btnBack).vGrow(Priority.SOMETIMES);
		btnBack.setOnAction(e -> fireEvent(l -> l.requestMain()));

	}
}
