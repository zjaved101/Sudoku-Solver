package sudoku;

import java.util.*;

//Models a sudoku grid
public class Grid {
	
	private int[][] values;

	// See TestGridSupplier for examples of input.
	// Dots in input strings become 0s in values[][].
	// Constructs a grid with an array of rows 
	public Grid(String[] rows) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++) {
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i = 0; i < 9; i++) {
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	//Copies grid into a new grid
	public Grid(Grid src){
		values = new int[src.values.length][src.values[0].length];
        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < values[0].length; j++){
                values[i][j] = src.values[i][j];
            }
        }
	}

	public String toString() {
		String s = "";
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char) ('0' + n);
			}
			s += "\n";
		}
		return s;
	}

	//Gets the next empty cell in grid
	private int[] getIndicesOfNextEmptyCell(){
		int[] yx = new int[2];
		
		for(yx[0] = 0; yx[0] < values.length; yx[0]++)
			for(yx[1] = 0; yx[1] < values.length; yx[1]++)
				if(values[yx[0]][yx[1]] == 0)
					return yx;
				
			return null;
	}

	// Finds an empty member of values[][]. Returns an array list of 9 grids
	// that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the
	// current grid is full.
	public ArrayList<Grid> next9Grids()
	{		
		int[]  indicesOfNext = getIndicesOfNextEmptyCell();
		ArrayList<Grid> nextGrids = new ArrayList<>();
		
		for(int n = 1; n <= values.length; n++){
		Grid grid = new Grid(this);
		grid.values[indicesOfNext[0]][indicesOfNext[1]] = n;
        nextGrids.add(grid);
		}
		
		return nextGrids;
	}

	// Returns true if the input list contains a repeated value that isn't zero.
    private boolean containsNoRepeats(ArrayList<Integer> ints)
    {
        ArrayList<Integer> dup = new ArrayList<Integer>();
        dup.add(0);
        ints.removeAll(dup);
        int size = ints.size();
        HashSet<Integer> list = new HashSet<Integer>(ints);
        return list.size() == size;
    }
    
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// zone contains
	// a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	public boolean isLegal() {
		
		//checks the rows
        for(int i = 0; i < values.length; i++){
            ArrayList<Integer> row = new ArrayList<Integer>();
            for(int j = 0; j < values[0].length; j++){
                row.add(values[i][j]);
                if(row.size() == values[0].length){
                	if(!containsNoRepeats(row)){
                		return false;
                	}
                }
            }
        }
        
        //Checks the columns
        for(int j = 0; j < values[0].length; j++){
            ArrayList<Integer> column = new ArrayList<Integer>();
            for(int i = 0; i < values.length; i++){
                column.add(values[i][j]);
                if(column.size() == values.length){
                	if(!containsNoRepeats(column)){
                		return false;
                	}
                }
            }
        }
        
        //Checks the zones
        for(int z = 0; z < 9; z++){
        	int rowStart = 3*(z/3);
        	int rowEnd = rowStart + 2;
        	int colStart = 3*(z%3);
        	int colEnd = colStart + 2;
        	
        	ArrayList<Integer> zones = new ArrayList<>();
        	for(int j = colStart; j <= colEnd; j++){
        		for(int i = rowStart; i <= rowEnd; i++){
        			zones.add(values[j][i]);
        		}
        	}
        	if(!containsNoRepeats(zones)){
        		return false;
        	}
        }
        return true;
	}

	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull() {
		for(int i = 0; i < values.length; i++){
			for(int j = 0; j < values[0].length; j++){
				if(values[i][j] < 1 || values[i][j] > 9){
					return false;
				}
			}
		}
		return true;
	}

	// Returns true if x is a Grid and, for every (i,j),
	// x.values[i][j] == this.values[i][j].
	public boolean equals(Object x) {
		Grid g = (Grid)x;
		boolean equal = true;
		for(int i = 0; i < values.length; i++){
			for(int j = 0; j < values[0].length; j++){
				if(g.values[i][j] != this.values[i][j]){
					equal = false;
				}
			}
		}
		return equal;
	}
}