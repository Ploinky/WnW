package de.jjl.wnw.desktop.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.jjl.wnw.base.cfg.Settings;
import de.jjl.wnw.base.msg.MsgChatMessage;
import de.jjl.wnw.dev.game.Drawable;
import de.jjl.wnw.dev.game.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Chat implements GameObject, Drawable
{
	private int x;

	private int y;

	private int width;

	private int height;

	private List<MsgChatMessage> chatHistory;

	private long showingSince;

	private boolean enabled;

	private boolean showing;

	private String input;

	public void addInput(String input)
	{
		this.input += input;
	}

	public Chat()
	{
		x = 0;
		y = 0;
		width = 300;
		height = 200;
		enabled = false;
		showing = false;
		input = "";
		chatHistory = new ArrayList<>();
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

	@Override
	public void setX(int x)
	{
		this.x = x;
	}

	@Override
	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public void update(float frameTime)
	{
		if (enabled)
		{
			showingSince = System.currentTimeMillis();
		}

		if (!enabled && System.currentTimeMillis() - showingSince > TimeUnit.SECONDS
				.toMillis(Settings.getChatShowDuration()))
		{
			showing = false;
		}
	}

	public void addChatMessage(MsgChatMessage msg)
	{
		chatHistory.add(msg);
	}

	@Override
	public void drawOn(GraphicsContext graphics)
	{
		if (!showing)
		{
			return;
		}

		graphics.setFill(enabled ? new Color(0.75, 0.75, 0.75, 1) : new Color(0.75, 0.75, 0.75, 0.2));
		graphics.fillRect(x, y, width, height - 20);
		graphics.setFill(enabled ? Color.LIGHTBLUE : Color.LIGHTBLUE.desaturate());
		graphics.fillRect(x, y + height - 20, width, 20);

		graphics.setFill(Color.BLACK);
		graphics.getFont();
		graphics.setFont(Font.font(16));
		graphics.fillText(input, x, y + height - 5);

		List<MsgChatMessage> reverseMsgs = new ArrayList<>(chatHistory);
		Collections.reverse(reverseMsgs);

		int line = 0;

		for (MsgChatMessage msg : reverseMsgs)
		{
			String msgTxt = msg.toChatString();

			List<String> msgParts = new ArrayList<>();

			Text t = new Text("");
			t.setFont(graphics.getFont());

			while (msgTxt.length() > 0)
			{
				String tempTxt = "";
				t.setText("");

				while (msgTxt.length() > 0)
				{
					t.setText(tempTxt + msgTxt.substring(0, 1));

					if (t.getLayoutBounds().getWidth() > getWidth())
					{
						break;
					}

					tempTxt += msgTxt.substring(0, 1);
					msgTxt = msgTxt.substring(1);
				}

				msgParts.add(tempTxt);
			}

			Collections.reverse(msgParts);

			for (String txtPart : msgParts)
			{
				graphics.fillText(txtPart, x, y + height - 5 - (20 * (line++ + 1)));
			}
		}
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;

		if (enabled)
		{
			showing = true;
		}
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public String getInput()
	{
		return input;
	}

	public void clear()
	{
		input = "";
	}

	public void delChar()
	{
		input = input.substring(0, input.length() - 1);
	}

	public void show()
	{
		showing = true;
		showingSince = System.currentTimeMillis();
	}
}
