package assignment04;
public record Location(int row, int column) 
{
	public static Location from(String str) 
	{
		str = str.toUpperCase();
		if (str==null || str.length()==0)
		{
			throw new IllegalArgumentException("Format error, use ABC123");
		}
		
		long y=0;
		char firstChar = str.charAt(0);
		while(firstChar>='A' && firstChar<='Z')
		{	
			y = y*26 + (firstChar - 'A');
			str = str.substring(1);
			firstChar = str.charAt(0);
			
			if (y > Integer.MAX_VALUE)
			{
				throw new IllegalArgumentException("Number of columns too large");
			}
		}
		
		if (y<0)
		{
			throw new IllegalArgumentException("Format error, use ABC123");
		}
		
		if (str.length()==0)
		{
			throw new IllegalArgumentException("Format error, use ABC123");
		}
		
		try
		{
			long x = Long.parseLong(str);
			
			if (x > 1_999_999_997)
			{
				throw new IllegalArgumentException("Number of rows too large");
			}
            
			return new Location((int)x - 1, (int)y);
		}
		catch(NumberFormatException e)
		{
			throw new IllegalArgumentException("Numeric error");
		}
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (obj == null || !(obj instanceof Location)) return false;
		return row == ((Location)obj).row && column == ((Location)obj).column;  
	}
	
	@Override
	public int hashCode() 
	{
		return 31*row + column;
	}
	
	@Override
	public String toString() 
	{
		return "(" + row + ", " + column + ")";
	}
}
