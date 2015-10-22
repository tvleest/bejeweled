package bejeweled.board;

import java.util.ArrayList;

public class CombinationIterator implements Iterator{
	Combination combi;
	int position = 0;
	
	public CombinationIterator(Combination combi){
		this.combi=combi;
	}
	
	public Object next(){
		ArrayList<Gem> g = combi.getGems();
		Gem gem = g.get(position);
		position = position + 1;
		return gem;
		
	}
	
	public boolean hasNext(){
		if(position>= combi.getSize() || combi.getGems() == null){
			return false;
		}else{
			return true;
		}		
	}
	

}
