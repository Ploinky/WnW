/*
 * Copyright © 2017 Unitechnik Systems GmbH. All Rights Reserved.
 */
package de.jjl.wnw.desktop.start;

import javafx.application.Application;
import javafx.stage.Stage;

public class Starter extends Application
{
	public static void main(String[] args)
	{
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.show();
	}

}
