package assignment04;

public class AverageArea implements SpreadsheetFunctionArea 
{
	private Cell[][] cells;	
	
	public AverageArea(Cell[][] arg) 
	{
		cells = arg;
	}
	
	public double execute() 
	{
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
		return count==0?0.0:sum/count;
	}
}
