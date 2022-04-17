/**
 * 
 */
package assignment04;

/**
 * @author Aditya Chendwankar and Aditya Dhikle
 *
 */
public class MaxArea implements SpreadsheetFunctionArea 
{
	private Cell[][] cells;	

	public MaxArea(Cell[][] arg) 
	{
		cells = arg;
	}
	
	@Override
	public double execute() {
		// TODO Auto-generated method stub
		double max = cells[0][0].getValue();
		int count = 0;
		for(int i = 0; i < cells.length; i++) 
		{
			for(int j = 0; j < cells[0].length; j++) 
			{
				Cell temp = cells[i][j];
				if(temp.isValueGiven()) {
					count++;
					if (max < temp.getValue())
					{
						max = temp.getValue();
					}
				}
			}
		}
		return max;
	}

}