package de.jjl.wnw.base.rune.parser;

public class Config
{
		public int gridWidth = 3;
		public int gridHeight = 3;
		public int minFieldHeight = 10;
		public int maxFieldHeight = 1000;
		public int minFieldWidth = 10;
		public int maxFieldWidth = 1000;
		public int fieldTolerance = 100;
		public boolean moveFieldTwice = true;
		public boolean moveFieldBack = false;
		public GridCorner corner = GridCorner.Center;

		public Config(int gridWidth, int gridHeight, int minFieldHeight, int maxFieldHeight, int minFieldWidth,
				int maxFieldWidth, int fieldTolerance, boolean moveFieldTwice, boolean moveFieldBack, GridCorner corner)
		{
			this.gridWidth = gridWidth;
			this.gridHeight = gridHeight;
			this.minFieldHeight = minFieldHeight;
			this.maxFieldHeight = maxFieldHeight;
			this.minFieldWidth = minFieldWidth;
			this.maxFieldWidth = maxFieldWidth;
			this.fieldTolerance = fieldTolerance;
			this.moveFieldTwice = moveFieldTwice;
			this.moveFieldBack = moveFieldBack;
			this.corner = corner;
		}

		public Config()
		{
		}

		public int getGridHeight()
		{
			return gridHeight;
		}

		public int getGridWidth()
		{
			return gridWidth;
		}
		
		public GridCorner getCorner()
		{
			return corner;
		}
		
		public int getFieldTolerance()
		{
			return fieldTolerance;
		}

		public int getMaxFieldHeight()
		{
			return maxFieldHeight;
		}
		
		public int getMaxFieldWidth()
		{
			return maxFieldWidth;
		}
		
		public int getMinFieldHeight()
		{
			return minFieldHeight;
		}
		
		public int getMinFieldWidth()
		{
			return minFieldWidth;
		}
		
		public void setMoveFieldTwice(Boolean n)
		{
			moveFieldTwice = true;
		}
		
		public void setCorner(GridCorner corner)
		{
			this.corner = corner;
		}
		
		public void setFieldTolerance(int fieldTolerance)
		{
			this.fieldTolerance = fieldTolerance;
		}
		
		public void setGridHeight(int gridHeight)
		{
			this.gridHeight = gridHeight;
		}
		
		public void setGridWidth(int gridWidth)
		{
			this.gridWidth = gridWidth;
		}
		
		public void setMaxFieldHeight(int maxFieldHeight)
		{
			this.maxFieldHeight = maxFieldHeight;
		}
		
		public void setMaxFieldWidth(int maxFieldWidth)
		{
			this.maxFieldWidth = maxFieldWidth;
		}
		
		public void setMinFieldHeight(int minFieldHeight)
		{
			this.minFieldHeight = minFieldHeight;
		}
		
		public void setMinFieldWidth(int minFieldWidth)
		{
			this.minFieldWidth = minFieldWidth;
		}
		
		public void setMoveFieldBack(boolean moveFieldBack)
		{
			this.moveFieldBack = moveFieldBack;
		}

		public boolean getMoveFieldTwice()
		{
			return moveFieldTwice;
		}

		public boolean getMoveFieldBack()
		{
			return moveFieldBack;
		}
		
}
