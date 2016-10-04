package search;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputObjectFactory {
	
	//define contents of File
static String FILE_SEPARATOR=" ";
	
 public static Hw1VO readFile(String path) throws IOException{
	 	Date startTime = Calendar.getInstance().getTime();
	 	Date endTime = null;
	 	FileReader inpFile = new FileReader(path);
	 	BufferedReader bufferedReader = 
                new BufferedReader(inpFile);
	 	String algo = bufferedReader.readLine();
	 	String startState = bufferedReader.readLine();
	 	String goalState = bufferedReader.readLine();
	 	
	 	
	 	System.out.println("Reading file Started ");
	 	Integer noOfLiveTrafficLines = Integer.valueOf(bufferedReader.readLine());
	 	String[] liveTrafficLines = new String[noOfLiveTrafficLines.intValue()];
	 	
	 	for(int i=0;i<noOfLiveTrafficLines;i++){
	 		liveTrafficLines[i] = bufferedReader.readLine();
	 	}
	 	
	 	Integer noOfSundayTrafficLines = Integer.valueOf(bufferedReader.readLine());
	 	String[] sundayTrafficLines = new String[noOfSundayTrafficLines.intValue()];
	 	for(int i=0;i<noOfSundayTrafficLines;i++){
	 		sundayTrafficLines[i] = bufferedReader.readLine();
	 	}
	 	endTime = Calendar.getInstance().getTime();
		System.out.println("Reading file Completed "+ (endTime.getTime() - startTime.getTime()));
	 	
	 	bufferedReader.close();
	 	
	 	Hw1VO hw1 = new Hw1VO();
	 	hw1.setAlgo(algo);
	 	hw1.setStartState(startState);
	 	hw1.setGoalState(goalState);
	 	hw1.setNoLiveTrafficLines(noOfLiveTrafficLines.intValue());
	 	hw1.setLiveTrafficLines(liveTrafficLines);
	 	startTime = Calendar.getInstance().getTime();
	 	List<Edge> edges = processLiveTrafficLine(liveTrafficLines);
	 	hw1.setEdges(edges);
	 	Map<String,Integer> heuristicMap = processSundayTrafficLines(sundayTrafficLines);
	 	hw1.setHeuresticMap(heuristicMap);
	 	endTime =Calendar.getInstance().getTime();
	 	System.out.println("TIme to create edges "+(endTime.getTime() - startTime.getTime()));
	 	
	 	
	 	
	 	//Creating graph from edges
	 	startTime = Calendar.getInstance().getTime();
	 	Map<String,Vertex> graph = new HashMap<String,Vertex>();
	 	for(Edge e: edges){
	 		if(graph.get(e.startState.getName())!=null){
	 			Vertex v = graph.get(e.startState.getName());
	 			v.neighbours.add(e);
	 		
	 		if(graph.get(e.destState.getName())==null){
	 				Vertex v2 = new Vertex();
		 			v2.setState(e.destState);
		 			graph.put(v2.getState().name, v2);
	 			}
	 		}else{
	 			if(graph.get(e.startState.getName())==null){
	 			Vertex v = new Vertex();
	 			v.setState(e.startState);
	 			v.neighbours.add(e);
	 			graph.put(v.getState().name, v);
	 			}
	 			
	 			if(graph.get(e.destState.getName())==null){
	 				Vertex v = new Vertex();
		 			v.setState(e.destState);
		 			graph.put(v.getState().name, v);
	 			}
	 			
	 		}
	 		
	 	}
	 	endTime =Calendar.getInstance().getTime();
	 	System.out.println("TIme to create graph "+(endTime.getTime() - startTime.getTime()));
	 	hw1.setGraph(graph);
	 	
	 	//Printing the map
			File file = new File("graph.txt");
		try{
		// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
				
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write("digraph{");
	 	for(Map.Entry<String,Vertex> entry: graph.entrySet()){
		//	bw.write(entry.getValue().state.name +  " "+entry.getValue().neighbours.size()+"\n");
	 	//	bw.write("Neighbours of "+entry.getValue().getState().name+"\n");
	 		bw.write(entry.getValue().state.name +" -> {");
	 		for(Edge e: entry.getValue().neighbours){
	 			bw.write(e.getDestState().getName()+" ");
	 	//		bw.write(e.getStartState().getName() +" "+e.getDestState().getName()+ " "+e.getCost());
	 		}
	 		bw.write("}\n");
	 	//	bw.write("\n");
	 		
	 	}
		bw.write("}");
		bw.close();
		
}catch(IOException e){
	System.out.println("Error while writing the outpit file "+e);
}

	 	hw1.setNoSundayTrafficLines(noOfSundayTrafficLines);
	 	hw1.setSundayTrafficLines(sundayTrafficLines);
	 	return hw1;	
	}

 public static  Map<String,Integer> processSundayTrafficLines(String[] sundayTrafficLines){
	 Map<String,Integer> heuristicMap = new HashMap<String,Integer>();
	 for(int i=0;i<sundayTrafficLines.length;i++){
		 String[] line = sundayTrafficLines[i].split(FILE_SEPARATOR);
		 String state = line[0];
		 
		 //Default heuristic is 0
		 Integer heurestic = 0;
		 
		 //Override cost from the input file
		 if(line.length>1){
			 heurestic = (Integer.valueOf(line[1]));
		 }
			 
		 heuristicMap.put(state, heurestic);
	 }
	 return heuristicMap;
 }
 
 public static List<Edge> processLiveTrafficLine(String[] liveTrafficLines){
	 List<Edge> table = new ArrayList<Edge>();
	 
	 for(int i=0;i<liveTrafficLines.length;i++){
		// System.out.println(liveTrafficLines[i]);
		 String[] line = liveTrafficLines[i].split(FILE_SEPARATOR);
		 State startState = new State(line[0]);
		 State destState = new State(line[1]);
		 
		 //Default Cost is 1
		 Integer cost = 1;
		 
		 //Override cost from the input file
		 if(line.length>2){
		 cost = (Integer.valueOf(line[2]));
		 }
			 
		Edge e = new Edge();
		e.setStartState(startState);
		e.setDestState(destState);
		e.setCost(cost);
		e.setOrder(i+1);
		table.add(e);
	 }
	 
	 return table;
 }
 	
 
 /*public static List<StateNode> processLiveTrafficLine(String[] liveTrafficLines){
	 List<StateNode> table = new ArrayList<StateNode>();
	 
	 for(int i=0;i<liveTrafficLines.length;i++){
		 String[] line = liveTrafficLines[i].split(" ");
		 String startNode = line[0];
		 String destNode = line[1];
		 Integer cost = (Integer.valueOf(line[3]));
		
		 StateNode st = new StateNode();
		 st.setCost(cost);
		 st.setDestName(destNode);
		 st.setSourceName(startNode);
		 table.add(st);
	 }
	 
	 return table;
 }*/
 
 
 
}
