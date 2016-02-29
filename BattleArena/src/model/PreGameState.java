package model;

import java.util.Observable;
import java.util.Observer;

public class PreGameState implements Observer{
	private boolean ready;
	public PreGameState(){
		ready=false;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Boolean){
			ready=(Boolean)arg1;
		}
	}
}
