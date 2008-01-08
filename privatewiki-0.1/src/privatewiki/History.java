package privatewiki;

import java.util.Vector;

/**
 * This class encapsulates the history of navigation
 * @author denis
 */
public class History {
	
	private Vector history;
	private int index;
	
	/**
	 * Constructs a history object with a default starting page
	 * @param location
	 */
	public History(String location){
		this.history = new Vector();
		add(location);
		index = 0;
	}
	
	/**
	 * Adds a location after the current history position 
	 * @param location
	 */
	public void add(String location){
		
		// remove the elements after the current position
		for (int i=history.size()-1; i>index; i--){
			history.remove(i);
		}
		
		// if history contains elements and the new location is different than the current
		if (history.size()==0 || !location.equals((String)history.get(index))){
			history.add(location);
			index = history.size()-1;
			show();				
		}
		
	}
	
	public String back(){
		if (index>0){
			index--;
		}
		show();
		return (String)history.get(index);
	}
	
	public String forward(){
		if (index<history.size()-1){
			index++;
		}
		return (String)history.get(index);
	}
	
	public Vector getHistory(){
		return history;
	}
	
	public int getSelectedIndex(){
		return index;
	}
	
	private void show(){
		return;
		/*
		for (int i=0; i<history.size(); i++){
			System.out.println("History (" + i + ")" + (i==index?"*":"") +" = '" + (String) history.get(i) + "'");
		}
		*/
	}

}
