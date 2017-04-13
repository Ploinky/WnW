package de.jjl.wnw.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

import de.jjl.wnw.android.util.path.WnWAndroidPath;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.base.util.path.WnWPoint;

/**
 * TODO: document your custom view class.
 */
public class WnWRuneView extends View
{
	private Deque<WnWAndroidPath>[] paths;

	public WnWRuneView(Context context)
	{
		super(context);
		init(null, 0);
	}

	public WnWRuneView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(attrs, 0);
	}

	public WnWRuneView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle)
	{
		paths = new Deque[5];
		for(int i = 0; i < paths.length; ++i)
		{
			paths[i] = new LinkedList<>();
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		for(Deque<WnWAndroidPath> deque : paths)
		{
			for(WnWAndroidPath path : deque)
			{
				path.onDraw(canvas);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(super.onTouchEvent(event))
		{
			return true;
		}

		switch(event.getActionMasked())
		{
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
			{
				final int index = event.getActionIndex();
				final int id = event.getPointerId(index);
				paths[id].add(new WnWAndroidPath(getWidth(), getHeight()));
			}
			case MotionEvent.ACTION_MOVE:
			{
				for(int i = 0; i < event.getPointerCount(); ++i)
				{
					final int id = event.getPointerId(i);
					final float x = event.getX(i);
					final float y = event.getY(i);

					paths[id].getLast().addPoint(x, y);
				}
				break;
			}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			{
				final int index = event.getActionIndex();
				final int id = event.getPointerId(index);
				//savePath(paths[id].getLast());
			}
		}

		invalidate();

		return true;
	}

	private void savePath(WnWAndroidPath path)
	{
		File f = getNextFile();

		Log.i("myTag", "FILE " + f.getAbsolutePath());

		try(BufferedWriter w = new BufferedWriter(new FileWriter(f)))
		{
			w.write(path.system.size.x + " " + path.system.size.y);
			w.newLine();
			w.write(path.system.zero.x + " " + path.system.zero.y);
			w.newLine();
			w.write(path.system.xAxis + " " + path.system.yAxis);
			w.newLine();

			for(WnWPoint point : path)
			{
				w.write(point.x + " " + point.y);
				w.newLine();
			}
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private File getNextFile()
	{
		Log.i("myTag", Environment.getExternalStorageState());
		File root = Environment.getExternalStoragePublicDirectory("rune");
		File runeRoot = root;

		if(!runeRoot.exists())
		{
			runeRoot.mkdirs();
		}

		int index = 1;

		File[] childs = runeRoot.listFiles(new FilenameFilter()
			{
				@Override
				public boolean accept(File dir, String name)
				{
					return name.matches("\\d+[.]rune");
				}
			});

		if(childs != null)
		{
			Arrays.sort(childs, new Comparator<File>()
			{
				@Override
				public int compare(File o1, File o2)
				{
					if(o1.getName().length() == o2.getName().length())
					{
						return o1.getName().compareTo(o2.getName());
					}

					return o1.getName().length() > o2.getName().length() ? 1 : -1;
				}
			});

			for(int i = 0; i < childs.length; ++i)
			{
				String name = childs[i].getName();

				if(!name.equals(index + ".rune"))
				{
					break;
				}

				++index;
			}
		}

		return new File(runeRoot, index + ".rune");
	}
}
