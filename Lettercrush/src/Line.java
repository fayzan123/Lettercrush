
public class Line {
	private int[] start = new int[2];
	private int[] end = new int[2];
	public Line(int row, int col, boolean horizontal, int length) {
		start[0] = row;
		start[1] = col;
		//if hprizontal is true, then you would add the length value to the col for end[1], and keep end[0] the same as start[0]
		//if horizontal is false (vertical) you would add the length value to the row for end[0], and keep end[1] the same as start[1]
		if (horizontal) {
			end[0] = row;
			end[1] = col + length - 1; 
		} else {
			end[0] = row + length - 1;
			end[1] = col;
		}
	}
	public int[] getStart() {
		return new int[]{start[0], start[1]};
	}
	public int length() {
		//compute length using reverse formula for end[1] and end[0]
		if (start[0] == end[0]) {
			int len = end[1] - start[1] + 1;
			return len;
		} else {
			int len = end[0] - start[0] + 1;
			return len;
		}
	}
	public boolean isHorizontal() {
		if (start[0] == end[0]) {
			return true;
		} else {
			return false;
		}
	}
	public boolean inLine(int row, int col) {
	    int i;

	    if (start[0] == end[0]) { // if the line is horizontal
	        if (row != start[0]) { // if the row inputted is not the same as the line, automatically the coordinate is not on the line
	            return false;
	        } else {
	            for (i = start[1]; i <= end[1]; i++) { // iterate through the indexes on the line by column
	                if (i == col) { // if the inputted column value is on the line (if it equals i), then the given coordinates are on the line
	                    return true;
	                }
	            }
	            return false; // otherwise, coordinates given are not on line
	        }
	    } else { // if line is vertical
	        if (col != start[1]) { // if the starting column is not the same as the given column, coordinates are automatically not on the line
	            return false;
	        } else {
	            for (i = start[0]; i <= end[0]; i++) { // iterates through every index of the line by row, if row given is equal to value in for loop, coordinates are on line
	                if (i == row) {
	                    return true;
	                }
	            }
	            return false; // otherwise, coordinates given are not on line
	        }
	    }
	}
	@Override
	public String toString() {
		return "Line:[" + start[0] + "," + start[1] + "]->[" + end[0]+ "," + end[1] + "]";
	}
}
