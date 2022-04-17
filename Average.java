package assignment04;

public class Average implements SpreadsheetFunction 
{
	private Cell[] cells;	
	public Average(Cell... args) 
	{
		cells = args;
	}
	
	public double execute() 
	{
		double sum = 0.0;
		int count = 0;
		for(var arg : cells) 
		{
			if(arg.isValueGiven()) 
			{
				count++;
				sum += arg.getValue();
			}
		}
		return count==0?0.0:sum/count;
	}
}
