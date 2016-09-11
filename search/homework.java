package search;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class homework {

	public static void main(String[] args) {
	
		try{
			  System.out.println("Working Directory = " +
		              System.getProperty("user.dir"));
		Hw1VO hw1 = InputObjectFactory.readFile("input.txt");
		Date startTime = Calendar.getInstance().getTime();
		List<String> path = null;
		Date endTime = null;
		switch (hw1.getAlgo()){
		case "BFS": path = SearchAlgo.runBFS(hw1.graph,hw1.startState,hw1.goalState);
					break;
		case "DFS": path = SearchAlgo.runDFS(hw1.graph,hw1.startState,hw1.goalState);
					break;
		case "UCS":path =  SearchAlgo.runUCS(hw1.graph,hw1.startState,hw1.goalState);
					break;
		case "A*":path = SearchAlgo.runAstar(hw1.graph,hw1.getHeuristicMap(), hw1.startState,hw1.goalState);
					break;
		}
		endTime = Calendar.getInstance().getTime();
		
		
		//Create output.txt
		File file = new File("output.txt");
		try{
		// if file doesn't exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
				
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					for(int i=path.size()-1;i>=0;i--){
					bw.write(path.get(i)+"\n");
					}
					bw.close();			
		}catch(IOException e){
			System.out.println("Error while writing the outpit file "+e);
		}	

		System.out.println("Time to perform search "+(endTime.getTime() - startTime.getTime()));
		}catch(IOException e){
			System.out.println("IO Error " + e);
		}
		
		
		
		
	}

}
