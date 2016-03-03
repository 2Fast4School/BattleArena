package arenaFighter;

import view.Meny;


public class Main{
	
	public static void main(String[] args){
		Meny frame = new Meny("BattleArena");
	}
}


/*
//Serialize map
try
  {
     FileOutputStream fileOut =
     new FileOutputStream("serializedMap.ser");
     ObjectOutputStream out = new ObjectOutputStream(fileOut);
     out.writeObject(map);
     out.close();
     fileOut.close();
     System.out.printf("Serialized data is saved in serializedMap.ser \n");
  }catch(IOException i)
  {
      i.printStackTrace();
  }

map = null;
//Unserialize map
  try
  {
     FileInputStream fileIn = new FileInputStream("serializedMap.ser");
     ObjectInputStream in = new ObjectInputStream(fileIn);
     map = (Map) in.readObject();
     in.close();
     fileIn.close();
  }catch(IOException i)
  {
     i.printStackTrace();
     return;
  }catch(ClassNotFoundException c)
  {
     System.out.println("Map class not found");
     c.printStackTrace();
     return;
  }
  System.out.println("Deserialized serializedMap...");
*/
