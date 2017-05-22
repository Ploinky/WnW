package de.jjl.wnw.android.util.path;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;

import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.base.util.path.WnWPathSimple;
import de.jjl.wnw.base.util.path.WnWPoint;

public class WnWAndroidPath extends WnWPathSimple
{
	private final Paint paint;

	private final Path path;

	public WnWAndroidPath(int width, int height)
	{
		this(width, height, new Paint());

		paint.setColor(0xff000000 + (int)(Math.random() * 0xffffff));
		paint.setStrokeWidth(30f);
		paint.setStyle(Paint.Style.STROKE);
	}

	public WnWAndroidPath(int width, int height, Paint paint)
	{
		super(WnWDisplaySystem.getFor(width, height, true, false));
		this.paint = paint;
		this.path = new Path();
	}

	@Override
	public void addPoint(float x, float y)
	{
		super.getPoints().add(new WnWPoint(x, y));

		if(path.isEmpty())
		{
			path.moveTo(x, y);
		}
		else
		{
			path.lineTo(x, y);
		}
	}

	public void onDraw(Canvas canvas)
	{
		canvas.drawPath(toPath(), paint);
	}

	public Path toPath()
	{
		return path;
	}
}
