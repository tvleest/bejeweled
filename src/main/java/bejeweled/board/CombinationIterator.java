package bejeweled.board;

import java.util.ArrayList;

public class CombinationIterator implements Iterator{
	Combination combi;
	int position = 0;
	ArrayList<Gem> g;
	
	public CombinationIterator(Combination combi){
		this.combi=combi;
		g = combi.getGems();
	}
	
	public Object next(){
		if(hasNext()) {
			Gem res = g.get(position);
			position++;
			return res;
		}
		else {
			return null;
		}
	}
	
	public boolean hasNext(){
		if(position>= combi.getSize() || combi.getGems() == null){
			return false;
		}else{
			return true;
		}		
	}
	

}
