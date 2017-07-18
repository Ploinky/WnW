package de.jjl.wnw.desktop.gui.fx;

import de.jjl.wnw.base.consts.Const;
import de.jjl.wnw.desktop.consts.DesktopConsts;
import de.jjl.wnw.desktop.game.FrameListener;
import de.jjl.wnw.desktop.gui.JFXFrame;
import de.jjl.wnw.desktop.gui.fx.comp.FXButton;
import de.jjl.wnw.desktop.gui.fx.comp.FXLabel;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class FXConnectMenu extends JFXFrame
{

	public FXConnectMenu(FrameListener listener)
	{
		super(listener);

		init();
	}

	private void init()
	{
		setVgap(10);

		FXLabel lblTitle = new FXLabel(Const.TITLE_CONNECT);
		lblTitle.setFont(DesktopConsts.FONT_TITLE);
		add(lblTitle);

		nextRow();

		JFXFrame subFrame = addFrame();
		subFrame.setPadding(new Insets(0, 100, 0, 100));
		vGrow(Priority.SOMETIMES);
		subFrame.setHgap(10);
		subFrame.setVgap(10);

		FXLabel lblName = new FXLabel("LblName");
		subFrame.add(lblName).setAligment(HPos.RIGHT, VPos.CENTER).vGrow(Priority.SOMETIMES).hGrow(Priority.SOMETIMES);

		TextField txtName = new TextField();
		subFrame.add(txtName).vGrow(Priority.SOMETIMES);

		subFrame.nextRow();

		FXLabel lblHost = new FXLabel("LblHost");
		subFrame.add(lblHost).setAligment(HPos.RIGHT, VPos.CENTER).vGrow(Priority.SOMETIMES).hGrow(Priority.SOMETIMES);

		TextField txtHost = new TextField();
		subFrame.add(txtHost).vGrow(Priority.SOMETIMES);
		// TODO $Li Mar 10, 2017 Add input validation for ip!

		nextRow();

		add(new Pane());

		nextRow();

		FXButton btnConnect = new FXButton("BtnConnect");
		add(btnConnect).vGrow(Priority.SOMETIMES);
		btnConnect.setOnAction(e ->
		{
			listener.requestConnect(txtHost.getText(), txtName.getText());
		});

		nextRow();

		FXButton btnBack = new FXButton("BtnBack");
		add(btnBack).vGrow(Priority.SOMETIMES);
		btnBack.setOnAction(e ->
		{
			listener.requestSceneChange(Const.MENU_MAIN);
		});

		nextRow();

		add(new Pane());
	}

}
