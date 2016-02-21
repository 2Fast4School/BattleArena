package server;

import java.awt.event.ActionListener;

import javax.swing.text.View;

public class ServerController implements ActionListener {
	Server model;
	ServerGUI view;

	//invoked when a button is pressed
	public void actionPerformed(java.awt.event.ActionEvent e){
		//uncomment to see what action happened at view
		/*
		System.out.println ("Controller: The " + e.getActionCommand() 
			+ " button is clicked at " + new java.util.Date(e.getWhen())
			+ " with e.paramString " + e.paramString() );
		*/
		System.out.println("Controller: acting on Model");
		
	} //actionPerformed()

	public void addModel(Server m){
		System.out.println("Controller: adding model");
		this.model = m;
	} //addModel()

	public void addView(ServerGUI v){
		System.out.println("Controller: adding view");
		this.view = v;
	} //addView()

	public void initModel(int x){
		//model.setValue(x);
	} //initModel()
}
