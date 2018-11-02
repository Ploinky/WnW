package de.jjl.wnw.base.rune.parser;

import de.jjl.wnw.base.input.WnWInput;
import de.jjl.wnw.base.input.WnWPathInput;
import de.jjl.wnw.base.rune.WnWInputParser;
import de.jjl.wnw.base.rune.WnWRune;
import de.jjl.wnw.base.util.path.WnWDisplaySystem;
import de.jjl.wnw.base.util.path.WnWPath;
import de.jjl.wnw.base.util.path.WnWPathSimple;
import de.jjl.wnw.base.util.path.WnWPoint;
import de.jjl.wnw.dev.rune.Runes;

public class WnWPathInputParser implements WnWInputParser
{
	@Override
	public WnWRune parseInput(WnWInput input)
	{
		if (input instanceof WnWPathInput)
		{
			return parsePath(((WnWPathInput)input).getPath(), null);
		}

		return null;
	}

	public WnWRune parsePath(WnWPath path, Config config) // TODO $ddd use default config from wherever
	{
		if (config == null)
		{
			config = new Config();
		}

		WnWPath runePath = filterRunePath(path.trimmed(), config);
		return lookupRune(runePath, config);
	}

	/**
	 * parse the given path using the given configuration and return a simple-path
	 * that may or may not can be directly mapped to rune
	 * 
	 * @param path
	 *            the path to parse
	 * @param config
	 *            to configuration for the parsing
	 * @return a parsed, simplified version of the path
	 */
	public WnWPath filterRunePath(WnWPath path, Config config)
	{
		Grid grid = buildGrid(path, config);
		return filterRunePath(path, config, grid);
	}

	/**
	 * parse the given path using the given configuration and return a simple-path that may
	 * or may not can be directly mapped to rune
	 * 
	 * @param path the path to parse
	 * @param config to configuration for the parsing
	 * @param grid The grid to use to parse the path
	 * @return a parsed, simplified version of the path
	 */
	public WnWPath filterRunePath(WnWPath path, Config config, Grid grid)
	{
		WnWPathSimple ret = new WnWPathSimple(new WnWDisplaySystem(config.gridWidth, config.gridHeight,
				path.getSystem().getXAxis(), path.getSystem().getYAxis()));
		
		WnWPoint preLastPoint = null;
		WnWPoint lastPoint = null;
		
		for(WnWPoint point : path)
		{
			WnWPoint nextPoint = grid.parse(point);
			
			if(nextPoint != null
				 && nextPoint != lastPoint)
			{
				if(config.moveFieldBack || nextPoint != preLastPoint)
				{
					if(config.moveFieldTwice || ret.contains(nextPoint))
					{
						ret.addPoint(nextPoint);
						preLastPoint = lastPoint;
						lastPoint = nextPoint;
					}
				}
			}
		}
		
		return ret;
	}

	/**
	 * build a grid base on the given configuration to parse the given path
	 * 
	 * @param path
	 *            the path to build a grid for
	 * @param config
	 *            the configuration for building the grid
	 * @return a grid to parse the given path
	 */
	public Grid buildGrid(WnWPath path, Config config)
	{
		// get the pathSize
		// if given use the system-Size, else the pathSize.
		// if both or 0 use 1 to avoid exception (div by 0)
		//
		int pathWidth = path.getSystem().getWidth() != 0 ? path.getSystem().getWidth()
				: (path.getPathWidth() != 0 ? path.getPathWidth() : 1);

		int pathHeight = path.getSystem().getHeight() != 0 ? path.getSystem().getHeight()
				: (path.getPathHeight() != 0 ? path.getPathHeight() : 1);

		return buildGrid(
				path.getSystem().getXAxis() ? path.getSystem().getZeroX()
						: path.getSystem().getZeroX() - path.getSystem().getWidth(),
				path.getSystem().getYAxis() ? path.getSystem().getZeroY()
						: path.getSystem().getZeroY() - path.getSystem().getHeight(),
				pathWidth, pathHeight, config);
	}

	/**
	 * build a grid base on the given configuration to parse a path placed in the field
	 * defined by the given start-position and size
	 *
	 * @param pathStartX The start-x of the field that shall be parsed by the grid
	 * @param pathStartY The start-y of the field that shall be parsed by the grid
	 * @param pathWidth The width of the field that shall be parsed by the grid
	 * @param pathHeight The height of the field that shall be parsed by the grid
	 * @param config The configuration for building the grid
	 * @return a grid to parse a path placed within the given field
	 */
	public Grid buildGrid(int pathStartX, int pathStartY, int pathWidth, int pathHeight, Config config)
	{
		int width = Math.max(pathWidth, 1);
		int height = Math.max(pathHeight, 1);
		
		int gridWidth = 0;
		int gridHeight = 0;
		
		switch(config.corner)
		{
			case Center:
				gridWidth = width / (config.gridWidth - 1);
				gridHeight = height / (config.gridHeight - 1);
				break;
			case Corner:
				gridWidth = width / config.gridWidth;
				gridHeight = height / config.gridHeight;
				break;
		}
		
		if(gridWidth * config.gridWidth < width)
		{
			gridWidth += 1;
		}
		
	if(gridHeight * config.gridHeight < height)
	{
		gridHeight += 1;
	}
		
		gridWidth = Math.max(gridWidth, config.minFieldWidth);
		gridWidth = Math.min(gridWidth, config.maxFieldWidth);
		
		gridHeight = Math.max(gridHeight, config.minFieldHeight);
		gridHeight = Math.min(gridHeight, config.maxFieldHeight);
		
		int startX = pathStartX + (width - (gridWidth * config.gridWidth)) / 2;
		int startY = pathStartY + (height - (gridHeight * config.gridHeight)) / 2;
		
		return new Grid(gridWidth, gridHeight, startX, startY, config.gridHeight, config.gridWidth, config.fieldTolerance);
	}

	public WnWRune lookupRune(WnWPath path, Config config)
	{
		long runeLong = 0L;
		
		int i = 1;
		
		for(WnWPoint point : path)
		{
			runeLong += ((config.gridHeight * point.getY() + (point.getX() + 1)) * i);
			
			i *= 10;
		}
		long reversedNumber = 0L;
		
		while(runeLong > 0)
		{
			long temp = runeLong % 10;
			reversedNumber = reversedNumber * 10 + temp;
			runeLong = runeLong/10;
		}
		
		return Runes.getRuneForLong(reversedNumber);
	}
}