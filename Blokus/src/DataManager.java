/*

  @author Ricky

 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("EmptyMethod")
class DataManager {
	private static final File boardSaveData = new File("session_data.json");
	// --Commented out by Inspection (11/10/2019 1:03 PM):private static File gameSaveData = new File("settings_data.json");
	private static final String[][][] data = new String[20][20][1];

	private static void parseGameData(JSONObject obj) {
		//System.out.println((JSONObject) obj.get("Data"));
		JSONObject loadObj = (JSONObject) obj.get("Data"); //Grab the outer JSONObject
		//int size = Integer.valueOf((String) loadObj.get("Size")); //Grab the value of size that was stored in save()
		// System.out.println(size);
		int size = 20;
		//String[][][] returnArray = new String[size][size][1]; //Initialize board-state array to return
		//Grab the corresponding JSON map from the JSONObject for each index of the return array:

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				data[i][j][0] = (String) loadObj.get(i + "," + j);

			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public String save(String[][][] shapes) {

		// when saving a file we need to see if the file already exists or not and the status of save
		String status;
		//Takes array from getBoard()
		int size = shapes.length; //Get the size of the nxn board

		//Convert each index into json map:
		JSONObject boardObj = new JSONObject(); //Create JSONObject to represent board state
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				boardObj.put((i+","+j), (shapes[i][j][0]));
			}
		}


		//Nest the previous JSONObject into an outer JSONObject (for easy finding):
		JSONObject saveObj = new JSONObject(); //Create outer JSONObject
		saveObj.put("Data", boardObj); //Add previous JSONObject to outer Object
		saveObj.put("Size", String.valueOf(size)); //Store the size of the board as well, needed for load

		//Add outer JSONObject to a JSONArray (the JSONArray gets stored in the file):
		JSONArray saveData = new JSONArray(); //Create JSONArray
		saveData.add(saveObj); //Add outer JSONObject to JSONArray

		//Try to write JSONArray to file:
		try {
			FileWriter file = new FileWriter(boardSaveData); //Create FileWriter
			file.write(saveData.toJSONString()); //Write the JSON array to file
			//Take care of file-resource management:
			file.flush();
			file.close();
			//Catch exception (not important):
			status="sucess";
		} catch (IOException e) {
			e.printStackTrace();
			status="failed";
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public String[][][] load() {
		JSONParser parser = new JSONParser(); //Create a JSONParser
		@SuppressWarnings("unused") String status = "";

		//Try to read data file:
		try {
			FileReader reader = new FileReader(boardSaveData); //Open file
			Object obj = parser.parse(reader); //Gather JSON data into Object
			JSONArray blockList = (JSONArray) obj; //Convert Object into JSONObject
			//Iterate over JSON object:
			blockList.forEach(shape -> parseGameData((JSONObject) shape)); //There's only one JSONObject, see parse function
			//Catch exceptions (not important):
			//System.out.println(obj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

// --Commented out by Inspection START (11/10/2019 1:08 PM):
//  public static void updateGameSettings(String[] settings) {
//	  //Will update the save file for game settings
//  }
// --Commented out by Inspection STOP (11/10/2019 1:08 PM)

// --Commented out by Inspection START (11/10/2019 1:08 PM):
//  @SuppressWarnings("unused")
//  public String[] getGameSettings() {
//	  //Will get saved game settings
//	  return new String[3];
//  }
// --Commented out by Inspection STOP (11/10/2019 1:08 PM)

// --Commented out by Inspection START (11/10/2019 1:03 PM):
//  @SuppressWarnings("unused")
//  public static void autoSave(@SuppressWarnings("unused") String[][] boardState) {
//	  //Might be needed for autosave feature, but probably won't be needed
//  }
// --Commented out by Inspection STOP (11/10/2019 1:03 PM)
}
