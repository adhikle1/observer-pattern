package assignment04;

import java.util.HashSet;
import java.util.Set;

public class FunctionCell extends Cell 
{
//	private FunctionCell(Location location) {
//		super(location);
//	}
	
	private FunctionCell(Location location, SpreadSheet spreadSheet) 
	{
		super(location, spreadSheet);
	}
	
	private Set<Cell> dependencies;
	private SpreadsheetFunction basicFunction;
	private SpreadsheetFunctionArea areaFunction;
	
	public Set<Cell> getDependencies() 
	{
		return dependencies;
	}
	
	public void update() 
	{
		if(basicFunction != null)
			value = basicFunction.execute();
		if(areaFunction != null)
			value = areaFunction.execute();
			// similar code if areaFunction is not null
		sheet.update(location); // this is why we needed the SpreadSheet as a field	
	}
	
	public SpreadsheetFunction getBasicFunction() {
		return basicFunction;
	}
	public SpreadsheetFunctionArea getAreaFunction() {
		return areaFunction;
	}
	public static class Builder 
	{
		private Set<Cell> dependencies = new HashSet<>();
		private SpreadsheetFunction basicFunction;
		private SpreadsheetFunctionArea areaFunction;
		
		public Builder withDependencies(Cell... cells) 
		{
			for(var cell : cells) 
			{
				dependencies.add(cell);
				// we cannot call cell.registerCell here, the argument is not ready
			}
			return this;
		}
	
//		public Builder withDependencies(Cell... cells) {
//			for(var cell : cells) {
//				dependencies.add(cell);
//				cell.registerObserver(this);
//			}
//			return this;
//		}
		
		public Builder withSpreadsheetFunction(SpreadsheetFunction function) {
			basicFunction = function;
			return this;
		}
		public Builder withSpreadsheetFunctionArea(SpreadsheetFunctionArea function) {
			areaFunction = function;
			return this;
		}

  		public FunctionCell build(String displayTx,	Location location, SpreadSheet spreadSheet ) 
  		{
  				FunctionCell cell = new FunctionCell(location, spreadSheet);
	//			FunctionCell cell = new FunctionCell(location);
				/* YOU CAN IGNORE THIS COMMENTED SECTION
				It explores discovering circular dependencies if we ever allowed formulas to reference
				other formulas, like real spreadsheets do. If you look at it, it looks like the
				epsilon closure of assignment 3.
							boolean valid = true;
							List<Cell> list = new ArrayList<>(dependencies);
							for(int i = 0; i < list.size(); i++) {
								Cell c = list.get(i);
								Set<Cell> depCells = c.getDependencies();
								if(depCells != null) {
									for(Cell temp : depCells) {
										if(cell.equals(temp)) {
											valid = false;
											break;
										}
										if(!list.contains(temp)) list.add(temp);
									}
								}
							}
							if(!valid)
								throw new IllegalArgumentException("Circularity of dependencies");
				*/
				cell.dependencies = dependencies;
				if(basicFunction != null) 
				{
					cell.basicFunction = basicFunction;
					cell.updateCell(displayTx, basicFunction.execute());
				}
				
				if(areaFunction != null) 
				{ 
					cell.areaFunction = areaFunction;
					cell.updateCell(displayTx, areaFunction.execute());
				}
	//			return cell;
				
				for(var dep : dependencies) 
				{
				    dep.registerCell(cell);
				}
				return cell;
  		}
	}
}
