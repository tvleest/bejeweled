package bejeweled.board;

import java.util.ArrayList;

public class Combination {
	
	GemType type;
	ArrayList<Gem> gems;
	
	public Combination(ArrayList<Gem> gems) {
		this.gems = gems;
		type = gems.get(0).type;
	}
	
	public GemType getType() {
		return type;
	}
	
	public int getSize() {
		return gems.size();
	}
	
	public ArrayList<Gem> getGems() {
		return gems;
	}
	
	public boolean isCombination() {
		for(int i = 0; i < gems.size(); i++) {
			if(gems.get(i).type != type) {
				return false;
			}
		}

		int row = gems.get(0).row;
		int col = gems.get(0).col;
		boolean rowbool = true;
		boolean colbool = true;
		
		for(int i = 1; i < gems.size(); i++) {
			if(gems.get(i).row != row) {
				rowbool = false;
			}
		}
		for(int i = 1; i < gems.size(); i++) {
			if(gems.get(i).col != col) {
				colbool = false;
			}
		}
		
		if((!rowbool && !colbool) || (rowbool && colbool)) {
			return false;
		}
		
		if(colbool) {
			ArrayList<Gem> temp = sortOnCol(gems);
			int previous = temp.get(0).col;
			for(int i = 1; i < temp.size(); i++) {
				if(previous != temp.get(i).col-1) {
					return false;
				}
			}
		}
		else {
			ArrayList<Gem> temp = sortOnRow(gems);
			int previous = temp.get(0).row;
			for(int i = 1; i < temp.size(); i++) {
				if(previous != temp.get(i).row-1) {
					return false;
				}
			}
		}
		return true;
	}
	
	public ArrayList<Gem> sortOnCol(ArrayList<Gem> g) {
		for(int j = 0; j < g.size(); j++) {
			for(int i = 0; i < g.size()-1; i++) {
				if(g.get(i+1).col < g.get(i).col) {
					Gem temp = g.get(i);
					g.set(i, g.get(i+1));
					g.set(i+1, temp);
				}
			}
		}
		return g;
	}
	
	public ArrayList<Gem> sortOnRow(ArrayList<Gem> g) {
		for(int j = 0; j < g.size(); j++) {
			for(int i = 0; i < g.size()-1; i++) {
				if(g.get(i+1).row < g.get(i).row) {
					Gem temp = g.get(i);
					g.set(i, g.get(i+1));
					g.set(i+1, temp);
				}
			}
		}
		return g;
	}
	
}
