package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	
	private Set<BoardCell> cell_set;
	private LinkedList<BoardCell> adjacencies;

	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	// Constructor
	IntBoard() {
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		cell_set = new HashSet<BoardCell>();
		adjacencies = new LinkedList<BoardCell>();
		
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		int x = 0;
		int y = 0;
		for (int i = 0; i < 16; i++) {
			BoardCell b = new BoardCell(x, y);
			cell_set.add(b);
			if (((i + 1) % 4) != 0) x++;
			else {
				y++;
				x = 0;
			}
		}
		
	}
	 
	public void calcAdjacencies() {
		// TODO calculate the adjacency lists for each grid cell
		int x = 0;
		int y = 0;
		for (int i = 0; i < 16; i++) {
			LinkedList<BoardCell> adj = new LinkedList<BoardCell>();
			
			if ((x-1) >= 0) adj.add(getCell(x-1, y));
			if ((x+1) < 4) adj.add(getCell(x+1, y));
			if ((y-1) >= 0) adj.add(getCell(x, y-1));
			if ((y+1) < 4) adj.add(getCell(x, y+1));
			
			adjMtx.put(getCell(x,y), adj);
			
			if (((i + 1) % 4) != 0) x++;
			else {
				y++;
				x = 0;
			}
		}
		
	}
	
	public void calcTargets(BoardCell cell, int moves) {
		// TODO calculate the targets for the cell in the specified number of moves
		
		visited.add(cell);		
		LinkedList<BoardCell> possMove = adjMtx.get(cell);		
		
		//Base Case
		for (BoardCell b : possMove) {
			if (moves == 1 && !visited.contains(b)) {
				targets.add(b);
			}
		}
		
		if (moves > 1) {
			for (BoardCell b : possMove) {
				if (!visited.contains(b)) {
					calcTargets(b, moves - 1);
				}
			}
		}
		
		visited.remove(cell);
		
	}
	
	// Returns the list of targets
	public Set<BoardCell> getTargets() {
		for (BoardCell b : targets) {
			System.out.println("Target is " + b.toString());
		}

		return targets;
	}
	
	// Returns the adjacency list for the cell
	public LinkedList<BoardCell> getAdjList(BoardCell cell) {

		return adjMtx.get(cell);
	}
	
	// returns the cell with the given row and column
	public BoardCell getCell(int row, int col) {
		
		for (Iterator<BoardCell> it = cell_set.iterator(); it.hasNext();) {
			BoardCell b = it.next();
			if (b.getRow() == row && b.getColumn() == col) {
				return b;
			}
		}
		
		return new BoardCell(row, col);
	}
	
	public static void main(String[] args) {
		IntBoard testing = new IntBoard();
		testing.calcAdjacencies();
		testing.calcTargets(testing.getCell(1,1), 2);
		testing.getTargets();
	}
}
