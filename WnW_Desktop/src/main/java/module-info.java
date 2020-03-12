module WnW_Desktop
{
	exports de.jjl.wnw.desktop.game;
	exports de.jjl.wnw.desktop.gui;
	exports de.jjl.wnw.desktop.consts;
	exports de.jjl.wnw.desktop.controls;
	exports de.jjl.wnw.desktop.util;
	exports de.jjl.wnw.desktop.gui.frames;
	exports de.jjl.wnw.desktop.client;

	requires java.desktop;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.swing;
	requires WnW_Base;
	
	opens de.jjl.wnw.desktop.gui.frames;
}