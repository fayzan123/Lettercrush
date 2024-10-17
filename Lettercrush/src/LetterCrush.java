
public class LetterCrush {
	private char[][] grid;
	public static final char EMPTY = ' ';
	
	public LetterCrush(int width, int height, String inital) {
		//initialize counter
		int stringCounter = 0;
		//construct grid
		grid = new char[height][width];
		
		//fill grid with empty spaces
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = EMPTY;
			}
		}
		
		//fill in grid with letters from initial
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (stringCounter < inital.length()) {
					grid[i][j] = inital.charAt(stringCounter);
					stringCounter++;
				} else break;
			}
		}
	}
	@Override
	public String toString() {
		//set base string
		String strBuild = "LetterCrush\n";
		//initialize variables for counting, 
		int count = 0;
		int rowCounter = 0;
		int colCounter = 0;
		//add rows to the base string, containing |(row)|(rowNumber)
		for (int i = 0; i < grid.length; i++) {
			strBuild += "|";
			for (int j = 0; j < grid[i].length; j++) {
				strBuild += grid[i][j];
				count++;
				if (count == grid[i].length) {
					strBuild += "|" + rowCounter + "\n";
					rowCounter++;
					count = 0;
				}
			}
		}
		//add column counter to bottom of base string formatted +(columnCount)+
		strBuild += "+";
		for (int i = 0; i < grid[0].length; i++) {
			strBuild += colCounter;
			colCounter++;
		}
		strBuild += "+";
		
		return strBuild;
	}
	
	public boolean isStable() {
		//iterate from bottom to top, check if columns contain an empty entry below a non empty entry, return false
		for (int i = grid.length - 1; i > 0; i--) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == EMPTY && grid[i-1][j] != EMPTY) {
					return false;
				}
			}
			
		}
		//otherwise, return true
		return true;
	}
	
	public void applyGravity() {
		//iterate through rows bottom to top, if entry is empty, replace with above entry, and set above entry to empty
		for (int i = grid.length - 1; i > 0; i--) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == EMPTY && grid[i-1][j] != EMPTY) {
					grid[i][j] = grid[i-1][j];
					grid[i-1][j] = EMPTY;
				}
			}
		}
	}
	
	public boolean remove(Line theLine) {
		//initialize variables, arrays for start and end of line, and ints for number of rows and columns in grid
		int[] start = theLine.getStart();
		int[] end;
		int numRows = grid.length;
		int numCols = grid[0].length;
		if (theLine.isHorizontal()) { //if line is horizontal, check if starting row and ending column are in bounds
			end = new int[] {start[0], start[1] + theLine.length()-1};
			if (start[0] >= numRows || end[1] >= numCols) {
				return false;
			}
			
		}
		else { // if line is vertical, cehck if starting column and ending row are in bounds
			end = new int[] {start[0] + theLine.length() - 1, start[1]};
			if (start[1] >= numCols || end[0] >= numRows) {
				return false;

			}
		}
		
		for (int row = start[0]; row <= end[0]; row++) { //replaces grid entries corresponding to valid line with EMPTY
	        for (int col = start[1]; col <= end[1]; col++) {
	            grid[row][col] = EMPTY;
	        }
	    }
		
		return true;
	}
	
	public String toString(Line theLine) {
		//set base string
		String strBuild = "CrushLine\n";
		//initialize variables for counting, 
		int count = 0;
		int rowCounter = 0;
		int colCounter = 0;
		//add rows to the base string, containing |(row)|(rowNumber)
		for (int i = 0; i < grid.length; i++) {
			strBuild += "|";
			for (int j = 0; j < grid[i].length; j++) {
				//if character at i, j is in the line, append lowercase char
				if (theLine.inLine(i, j)) {
					strBuild += Character.toLowerCase(grid[i][j]);
				} 
				else {
					strBuild += grid[i][j];
				}
				count++;
				if (count == grid[i].length) {
					strBuild += "|" + rowCounter + "\n";
					rowCounter++;
					count = 0;
				}
			}
		}
		//add column counter to bottom of base string formatted +(columnCount)+
		strBuild += "+";
		for (int i = 0; i < grid[0].length; i++) {
			strBuild += colCounter;
			colCounter++;
		}
		strBuild += "+";
		
		return strBuild;
	}
	
	public Line longestLine() {
		// Scan first the rows of the grid from bottom to top
		Line longLine = new Line(0,0,true,1);
		int largest = 0;
		for (int i = grid.length-1; i>=0; i--) {
			char letter = grid[i][0];
			int adjacent = 1;
			for (int j = 1; j < grid[0].length; j++) {
				if (grid[i][j] == letter && letter != EMPTY) {
					adjacent++;
					if (adjacent > largest) {
						largest = adjacent;
						longLine = new Line(i,j-adjacent+1,true,adjacent);
					}
				}
				else {
					letter = grid[i][j];
					adjacent = 1;
				}
			}
		}
		
		// Now scan the columns from left to right; each column is scanned from the bottom to the top
		for (int j = 0; j < grid[0].length; j++) {
			char letter = grid[grid.length-1][j];
			int adjacent = 1;
			for (int i = grid.length-2; i>=0; i--) {
				if (grid[i][j] == letter && letter != EMPTY) {
					adjacent++;
					if (adjacent > largest) {
						largest = adjacent;
						longLine = new Line(i,j,false,adjacent);
					}
				}
				else {
					letter = grid[i][j];
					adjacent = 1;
				}
			}
		}
		
		if (longLine.length() > 2) {
			return longLine;
		}
		else {
			return null;
		}
	}
	public void cascade() {
		//while longest line is 3 or more, remove the line and apply gravity if needed
		while (longestLine() != null) {
			remove(longestLine());
			if (!isStable()) {
				applyGravity();
			}
		}
	}	
	
}
