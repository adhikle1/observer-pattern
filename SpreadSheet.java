package assignment04;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

//import assignment02.ScreenManager;

public class SpreadSheet 
{
	private JFrame frame = new JFrame("Spreadsheet");
	private JTextField[][] fields = new JTextField[100][3*26];
	private Cell[][] cells = new Cell[100][3*26];
	
	public Cell getCell(int row, int column) 
	{
		return cells[row][column];
	}
	
	public Cell getCell(Location arg) 
	{
		return getCell(arg.row(), arg.column());
	}

	public void update(Location loc) 
	{
		fields[loc.row()][loc.column()].setText(String.format("%.3f", getCell(loc).getValue()));
	}
	
	public void drawSpreadSheet () 
	{
//		for(int i = 0; i < fields.length; i++)
//			for(int j = 0; j < fields[0].length; j++) 
//			{
//				fields[i][j] = new JTextField(10);
//				cells[i][j] = new Cell(new Location(i,j));
//			}
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(fields.length+1, fields[0].length+1));
		panel.add(new JLabel(""));
		
		for(int j = 0; j < 26; j++) 
		{
			panel.add(new JLabel("" + (char)('A' + j), JLabel.CENTER));
		}
		
		for(int j = 0; j < 26; j++) 
		{
			panel.add(new JLabel("A" + (char)('A' + j), JLabel.CENTER));
		}
		
		for(int j = 0; j < 26; j++) 
		{
			panel.add(new JLabel("B" + (char)('A' + j), JLabel.CENTER));
		}
		
		for(int i = 0; i < fields.length; i++) 
		{
			panel.add(new JLabel("" + (i + 1), JLabel.CENTER));
			for(int j = 0; j < fields[0].length; j++) 
			{
				cells[i][j] = new Cell(new Location(i, j), this);
				fields[i][j] = new JTextField(10);
				fields[i][j].setEditable(false);
				fields[i][j].setBackground(Color.WHITE);
				fields[i][j].setForeground(Color.BLACK);;
				panel.add(fields[i][j]);
			}
		}
		
		JPanel bottom = new JPanel();
		bottom.add(new JLabel("Location, e.g. AH25: "));
		var cell = new JTextField(10);
		bottom.add(cell);		
		var val = new JTextField(10);
		bottom.add(new JLabel("Value: "));
		bottom.add(val);
		JPanel bottom1 = new JPanel();
		JButton show = new JButton("Show Text");
		bottom1.add(show);
		JButton update = new JButton("UPDATE");
		bottom1.add(update);
		JTextField errorMsg = new JTextField(30);
		errorMsg.setEditable(false);
		errorMsg.setBackground(Color.WHITE);
		errorMsg.setForeground(Color.BLACK);;
		bottom1.add(errorMsg); // for error message
		
		update.addActionListener(e -> 
		{
			try 
			{
				Location loc = Location.from(cell.getText());
				Cell c = cells[loc.row()][loc.column()];
				if(c instanceof FunctionCell) 
				{
					fields[loc.row()][loc.column()].setText(String.format("%.3f",c.getValue()));
				} 
				else 
				{
					String cellText = val.getText();
					double value = c.getValue(); 
					if(cellText.length() > 0) 
					{
						value = Double.parseDouble(cellText);
						c.updateCell(cellText, value);
					}
					fields[loc.row()][loc.column()].setText(String.format("%.3f", value));
				}
			} 
			catch (Exception ex) 
			{
				errorMsg.setText(ex.getMessage());
			}
		});
		
		show.addActionListener(e -> 
		{
			try 
			{
				Location loc = Location.from(cell.getText());
				Cell c = cells[loc.row()][loc.column()];
				fields[loc.row()][loc.column()].setText(c.getDisplayText());				
			} 
			catch (Exception ex) 
			{
				errorMsg.setText(ex.getMessage());
			}			
		});
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2,0));
		p.add(bottom);
		p.add(bottom1);
		frame.add(new JScrollPane(panel));
		frame.add(p, BorderLayout.PAGE_END);
		frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);
	}

	void putInFunctions1() 
	{
		Random rand = new Random();
		Cell[][] area = new Cell[3][4];
		Cell[] dependencies = new Cell[12];
		
		for(int i = 0; i < 3; i++) 
		{ 
			for(int j = 0; j < 4; j++) 
			{
				area[i][j] = cells[i+4][j+2];
				dependencies[4*i + j] = cells[i+4][j+2];
				int k = rand.nextInt(50);
				cells[i+4][j+2].updateCell("" + k, k);
				fields[i+4][j+2].setText(String.format("%.3f", (double)k));
			}
		}
		
		SpreadsheetFunctionArea function = new AverageArea(area);
		FunctionCell.Builder builder = new FunctionCell.Builder();
		builder = builder.withDependencies(dependencies);
		builder = builder.withSpreadsheetFunctionArea(function);
		FunctionCell fc = builder.build("=average(C5:F7)", new Location(12,3), this);
		cells[12][3] = fc;

		fields[11][3].setText(String.format("Average"));
		fields[12][3].setText(String.format("%.3f", fc.getValue()));
	}
	
	void putInFunctions2() 
	{
		Random rand = new Random();
		Cell[][] area = new Cell[3][4];
		Cell[] dependencies = new Cell[12];
		
		for(int i = 0; i < 3; i++) 
		{ 
			for(int j = 0; j < 4; j++) 
			{
				area[i][j] = cells[i+4][j+2];
				dependencies[4*i + j] = cells[i+4][j+2];
			}
		}
		
		SpreadsheetFunctionArea function = new MinArea(area);
		FunctionCell.Builder builder = new FunctionCell.Builder();
		builder = builder.withDependencies(dependencies);
		builder = builder.withSpreadsheetFunctionArea(function);
		FunctionCell fc = builder.build("=min(C5:F7)", new Location(12,4), this);
		cells[12][4] = fc;
		fields[11][4].setText(String.format("Minimum"));
		fields[12][4].setText(String.format("%.3f", fc.getValue()));
	}
	
	void putInFunctions3() 
	{
		Random rand = new Random();
		Cell[][] area = new Cell[3][4];
		Cell[] dependencies = new Cell[12];
		
		for(int i = 0; i < 3; i++) 
		{ 
			for(int j = 0; j < 4; j++) 
			{
				area[i][j] = cells[i+4][j+2];
				dependencies[4*i + j] = cells[i+4][j+2];
			}
		}
		
		SpreadsheetFunctionArea function = new MaxArea(area);
		FunctionCell.Builder builder = new FunctionCell.Builder();
		builder = builder.withDependencies(dependencies);
		builder = builder.withSpreadsheetFunctionArea(function);
		FunctionCell fc = builder.build("=max(C5:F7)", new Location(12,5), this);
		cells[12][5] = fc;
		fields[11][5].setText(String.format("Maximum"));
		fields[12][5].setText(String.format("%.3f", fc.getValue()));
	}
	
	void putInFunctions4() 
	{
		Random rand = new Random();
		Cell[][] area = new Cell[3][4];
		Cell[] dependencies = new Cell[12];
		
		for(int i = 0; i < 3; i++) 
		{ 
			for(int j = 0; j < 4; j++) 
			{
				area[i][j] = cells[i+4][j+2];
				dependencies[4*i + j] = cells[i+4][j+2];
			}
		}
		
		SpreadsheetFunctionArea function = new SumArea(area);
		FunctionCell.Builder builder = new FunctionCell.Builder();
		builder = builder.withDependencies(dependencies);
		builder = builder.withSpreadsheetFunctionArea(function);
		FunctionCell fc = builder.build("=sum(C5:F7)", new Location(12,2), this);
		cells[12][2] = fc;
		fields[11][2].setText(String.format("Sum"));
		fields[12][2].setText(String.format("%.3f", fc.getValue()));
	}

	public static void main(String[] args) 
	{
		var sheet = new SpreadSheet();
		
		try 
		{
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
			javax.swing.SwingUtilities.invokeLater(
					() -> 
					{
						sheet.drawSpreadSheet();
						sheet.putInFunctions1();
						sheet.putInFunctions2();
						sheet.putInFunctions3();
						sheet.putInFunctions4();
					});
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
	}
}
