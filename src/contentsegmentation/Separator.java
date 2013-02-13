package contentsegmentation;

import java.awt.Point;

/**
 * This class represents the visual separator
 * 
 * @author Rupert Diriye
 *
 */


public class Separator  implements Comparable<Separator>{

	public int startPoint = 0;
	public int endPoint = 0;
	public int weight = 3;
	public int normalizedWeight = 0;
	public Point topLeft;
	public Point bottomRight;
	
	public Separator(int start, int end, int weight) {
		this.startPoint = start;
		this.endPoint = end;
		this.weight = weight;
	}

	public Separator(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY)
	{
		this.topLeft = new Point(topLeftX, topLeftY);
		this.bottomRight = new Point(bottomRightX, bottomRightY);
		this.startPoint = topLeftX;
		this.endPoint = bottomRightY;
	}

	public void initTopLeftPoint(int topLeftX, int topLeftY)
	{
		this.topLeft = new Point(topLeftX, topLeftY);
	}

	public void initBottomLeftPoint(int bottomRightX, int bottomRightY)
	{
		this.bottomRight = new Point(bottomRightX, bottomRightY);
	}

	@Override
	public int compareTo(Separator separator)
	{
		return this.weight - separator.weight;
	}
	
}
