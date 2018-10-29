package de.jjl.wnw.base.rune.parser;

import de.jjl.wnw.base.util.path.WnWPoint;

/**
 * Helper class to define a Grid within a field.<br>
 * The grid itself starts at a given position (startX, startY) and has cols * rows fields.
 * each field has a size of (fieldWidth, fieldHeight), so the full size of the grid is
 * (cols * fieldWidth, rows * fieldHeight).<br>
 * Using these information together with the tolerance the grid can determin the {override fun equals(other: Any?): Boolean{
return super.equals(other)
}
override fun hashCode(): Int{
return super.hashCode()
}
override fun toString(): String{
return super.toString()
}
#parse(WnWPoint) gridPosition}
 * any point given.
 * 
 * @Property fieldWidth the width of a single field of the grid
 * @Property fieldHeight the height of a single field of the grid
 * @Property startY The lowest x-coordinate of the grid - the zero-X
 * @Property startY The lowest y-coordinate of the grid - the zero-Y
 * @Property rows number of rows of the grid
 * @Property cols number of columns of the grid
 * @Property tolerance the tolerance to detect if point should be considered to agiven field in full %.<br>
 *                     a value of 0% means the point must lie directly in the center of the field while 141 (sqrt(2))
 *                     means, any point within the field will be considered.
 */
public class Grid
{
	private int fieldWidth;
	private int fieldHeight;
	private int startX;
	private int startY;
	private int rows;
	private int cols;
	private int tolerance;
	private WnWPoint start = new WnWPoint(startX, startY);
	private WnWPoint fieldSize = new WnWPoint(fieldWidth, fieldHeight);
	private WnWPoint fieldSizePerSqu = new WnWPoint(
		(int) Math.pow(fieldWidth * tolerance / 2 / 100.0, 2.0),
		(int)Math.pow(fieldHeight * tolerance / 2 / 100.0, 2.0));
	private WnWPoint gridSize = new WnWPoint(cols, rows);
	double radX = fieldWidth * 0.5 * tolerance / 100;
	double radY = fieldHeight * 0.5 * tolerance / 100;
	
	public Grid(int fieldWidth, int fieldHeight, int startX, int startY, int gridHeight, int gridWidth, 
			int fieldTolerance)
	{
		this.fieldHeight = fieldWidth;
		this.fieldHeight = fieldHeight;
		this.startX = startX;
		this.startY = startY;
		this.rows = gridHeight;
		this.cols = gridWidth;
		this.tolerance = fieldTolerance;
	}
	
	/**
	 * Determine and return the gridposition of the given point.<br>
	 * The gridposition is the (x, y) position of the field of this grid, in which the point lies.
	 * This calculation considered the tolerance. If the point lies outside the grid or it does
	 * not fit into any field, null is returned.<br>
	 * I. e. considering a grid of size(1, 1) with a fieldSize of (100, 100) and a start-postion
	 * of (0, 0) if the tolerance is 100 all points with a distance <= 50 to the center (50, 50)
	 * would be considered, This would mean the points (0, 50), (25, 25) or (70, 53) would return
	 * return the gridposition (0, 0), while (95, 3), (60 100) or (0, 0) would return null.<br>
	 * on a tolerance of > 141 (sqrt(2)) all points within the grid [(0, 0) to(00, 100)] would return
	 * the gridposition (0, 0).
	 * On the other hand on a tolerance of 0 the only the point (50, 50) [the center] would be considered
	 * 
	 * @param point the point to determin the gridposition of
	 * @return the gridposition of the given point
	 */
	public WnWPoint parse(WnWPoint point)
	{
		WnWPoint temp = point.minus(start);
		temp = temp.div(fieldSize.length());
		
		if(temp.getX() < 0 || temp.getX() >= gridSize.getX() || temp.getY() < 0 || temp.getY() >= gridSize.getY())
		{
			return null;
		}
		
		WnWPoint fieldCenter = start.plus((temp.times(fieldSize.length())).plus((fieldSize.div(2))));
		
		if(point == fieldCenter)
		{
			return temp;
		}
		
		WnWPoint centerDist = new WnWPoint(
						Math.max(fieldCenter.getX(), point.getX()) - Math.min(fieldCenter.getX(), point.getX()),
						Math.max(fieldCenter.getY(), point.getY()) - Math.min(fieldCenter.getY(), point.getY()));
		
		// the form:
		// a point lies within an ellipse if
		// (point.x - center.x)² / radius.x²
		// + (point.y - center.y)² / radius.y²
		// <= 1
		
        double normx = centerDist.getX() / radX;
        double normy = centerDist.getY() / radY;
        if((normx * normx + normy * normy) < 1.00000001)
        	{
        	return temp;
        	}
        else
        	{
        	return null;
        	}
	}
	
	@Override
	public String toString()
	{
		return "Grid [" +
			"zero($startX,$startY)" +
			", fieldSize($fieldWidth,$fieldHeight)" +
			", gridSize($cols,$rows)" +
			", tolerance($tolerance)" +
			"]";
	}
	
	@Override
	public int hashCode()
	{
		int PRIME = 131;
		
		return ((start.hashCode() * PRIME + fieldSize.hashCode()) * PRIME + gridSize.hashCode()) * PRIME;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Grid)
		{
			if(start == ((Grid) other).start
				&& fieldSize == ((Grid) other).fieldSize
				&& gridSize == ((Grid) other).gridSize
				&& tolerance == ((Grid) other).tolerance)
			{
				return true;
			}
		}

		return false;
	}

	public int getStartX()
	{
		return startX;
	}

	public int getStartY()
	{
		return startY;
	}

	public double getFieldWidth()
	{
		return fieldWidth;
	}
	
	public int getFieldHeight()
	{
		return fieldHeight;
	}
	
	public double getRows()
	{
		return radX;
	}
	
	public int getCols()
	{
		return cols;
	}

	public double getTolerance()
	{
		// TODO Auto-generated method stub
		return tolerance;
	}
}