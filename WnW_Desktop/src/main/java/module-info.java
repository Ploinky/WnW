module WnW_Desktop {
	exports de.jjl.wnw.desktop.game;
	exports de.jjl.wnw.desktop.gui;
	exports de.jjl.wnw.desktop.consts;
	exports de.jjl.wnw.desktop.controls;
	exports de.jjl.wnw.desktop.util;
	exports de.jjl.wnw.desktop.gui.frames;
	exports de.jjl.wnw.desktop.client;
	exports de.jjl.wnw.desktop.gui.server;

	requires java.desktop;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.media;
	requires javafx.base;
	requires javafx.web;
	requires javafx.swing;
	requires javafx.fxml;
	requires WnW_Base;

	opens de.jjl.wnw.desktop.gui.frames;
}