/**
 * 
 */
package assignment04;

/**
 * @author Aditya Chendwankar and Aditya Dhikle
 *
 */
public class SumArea implements SpreadsheetFunctionArea 
{
	private Cell[][] cells;	

	public SumArea(Cell[][] arg) 
	{
		cells = arg;
	}
	
	@Override
	public double execute() {
		// TODO Auto-generated method stub
		double sum = 0.0;
		int count = 0;
		for(int i = 0; i < cells.length; i++) 
		{
			for(int j = 0; j < cells[0].length; j++) 
			{
				Cell temp = cells[i][j];
				if(temp.isValueGiven()) {
					count++;
					sum += temp.getValue();
				}
			}
		}
		return sum;
	}

}