package de.jjl.wnw.android.util.path;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;

import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.base.util.path.WnWPoint;

public class WnWAndroidPath extends WnWPath
{
	private final Paint paint;

	private Path path;

	public WnWAndroidPath(int width, int height)
	{
		this(width, height, new Paint());

		paint.setColor(0xff000000 + (int)(Math.random() * 0xffffff));
		paint.setStrokeWidth(30f);
	}

	public WnWAndroidPath(int width, int height, Paint paint)
	{
		super(WnWDisplaySystem.getFor(width, height, true, false));
		this.paint = paint;
	}

	@Override
	public void addPoint(float x, float y)
	{
		super.addPoint(x, y);

		path = null;
	}

	public void onDraw(Canvas canvas)
	{
		if(false)
		{
			canvas.drawPath(toPath(), paint);
			return;
		}
		WnWPoint point = null;
		for(WnWPoint pt : this)
		{
			if(point != null)
			{
				canvas.drawLine((float)point.x, (float)point.y, (float)pt.x, (float)pt.y, paint);
			}
			point = pt;
		}
	}

	public Path toPath()
	{
		if(path == null)
		{
			path = new Path();
			path.setFillType(Path.FillType.WINDING);
			for(WnWPoint point : this)
			{
				if(path.isEmpty())
				{
					path.moveTo((float)point.x, (float)point.y);
				}
				else
				{
					path.lineTo((float)point.x, (float)point.y);
				}
			}
		}
		return path;
	}
}
