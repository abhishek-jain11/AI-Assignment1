
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;


public class SearchAlgo {
	//No Loop Detection yet!!! I don't think so its required. But need to check if any case possible
	public static List<String> runBFS(Map<String,Vertex> graph, String startNodeName, String goalStateName){	
		//Search graph
		Vertex startVertex = graph.get(startNodeName);
		boolean goalFound = false;
		Node startNode = new Node();
		startNode.state = startVertex.getState();
		startNode.parent = null;
		startNode.cost = 0;
		startNode.depth = 0;
		Node goalNode = null;
		LinkedList<Node> frontier = new LinkedList<Node>();
		Map<String,State> explored = new HashMap<String,State>();
		
		frontier.add(startNode);
		
		if(startNode.getState().getName().equals(goalStateName)){
			frontier.poll();
			goalFound = true;
			goalNode = startNode;
		}
				
		while(!frontier.isEmpty()){
		//	System.out.println(frontier.peek().depth+" "+frontier.peek().getState().name);
		Node n = frontier.poll();
		//Explore Node and add children here
		Vertex v =  graph.get(n.getState().getName());
		if(v!=null){
		for(Edge e: v.getNeighbours()){
			Node child = new Node();
			child.state = e.destState;
			child.parent = n;
			child.cost = n.cost + 1;
			child.depth = n.depth  + 1;
			n.children.add(child);
			if(child.getState().getName().equals(goalStateName)){
				//Goal ELement found;
				goalNode = child;
				goalFound =true;
				break;
			}
		}
		if(goalFound){
			break;
		}

		explored.put(n.getState().getName(), n.getState());
		for(Node child: n.getChildren()){
			if(!explored.containsKey(child.getState().getName()) && !frontier.contains(child)){				
				frontier.add(child);
					}
				}
			}
		}
		
		//Print goalNodePath
		List<String> path = new ArrayList<String>();
		while(goalNode!=null){
			System.out.println(goalNode.getState().getName()+" "+goalNode.depth);
			path.add(goalNode.getState().getName()+" "+goalNode.depth);
			goalNode = goalNode.parent;
		}
		
		
		return path;
	}
	
	//No Loop Detection yet!!!
	public static List<String> runUCS(Map<String,Vertex> graph, String startNodeName, String goalStateName){
		//Search graph
		Vertex startVertex = graph.get(startNodeName);
		Node startNode = new Node();
		startNode.state = startVertex.getState();
		startNode.parent = null;
		startNode.setCost(0);
		startNode.depth = 0;
		startNode.setOrder(0);
		Node goalNode = null;
		
		Queue<Node> frontier = new PriorityQueue<Node>();
		Map<String,CostOrder> frontierCostMap = new HashMap<String,CostOrder>();
		
		frontier.add(startNode);
		frontierCostMap.put(startNode.getState().getName(), new CostOrder(startNode.getCost(),startNode.getOrder()));
		Map<String,State> explored = new HashMap<String,State>();
		
		
		while(!frontier.isEmpty()){
		//	System.out.println(frontier.peek().depth+" "+frontier.peek().getState().name);
		Node n = frontier.poll();
		frontierCostMap.remove(n.getState().getName());
		//Explore Node and add children here
		Vertex v =  graph.get(n.getState().getName());
		if(v!=null){
		for(Edge e: v.getNeighbours()){
			Node child = new Node();
			child.state = e.destState;
			child.parent = n;
			child.cost = n.cost + e.getCost();
			child.depth = n.depth  + 1;
			child.setOrder(e.order);
			n.children.add(child);
		}
		
		if(n.getState().getName().equals(goalStateName)){
			//Goal Element found;
			goalNode = n;
			break;
		}

		explored.put(n.getState().getName(), n.getState());
		for(Node child: n.getChildren()){
			if(!explored.containsKey(child.getState().getName()) && !frontier.contains(child)){	
				System.out.println("Adding to frontier ");
				System.out.println(child);
				
				frontier.add(child);
				frontierCostMap.put(child.getState().getName(), new CostOrder(child.getCost(),child.getOrder()));
				System.out.println("");
				System.out.println("frontier");
				System.out.println(frontier.peek().getCost()+" Name:"+frontier.peek().getState().getName()+" Parent:"+frontier.peek().getParent().getState().getName()+ " Order:"+frontier.peek().getOrder());
			//	System.out.println(frontier.peek().children);
					}
			else//For updating existing cost
				if(frontier.contains(child) && frontierCostMap.get(child.getState().getName()).compareTo(new CostOrder(child.getCost(),child.getOrder())) > 0 ){
					System.out.println("Updating existing cost");
					System.out.println("Removing node from frontier named "+child.getState().getName());
					frontier.remove(child);
					CostOrder removedCostMapNode = frontierCostMap.remove(child.getState().getName());
					System.out.println("Removing node from frontier Cost Map with values "+removedCostMapNode);
					System.out.println("Adding new node to frontier "+child);
					frontier.add(child);
					frontierCostMap.put(child.getState().getName(), new CostOrder(child.getCost(),child.getOrder()));
					}
				}
		System.out.println();
			}
		}

		
		List<String> path = new ArrayList<String>();
		while(goalNode!=null){
			System.out.println(goalNode.getState().getName()+" "+goalNode.cost);
			path.add(goalNode.getState().getName()+" "+goalNode.cost);
			goalNode = goalNode.parent;
		}
		return path;
	}
	
	public static List<String> runDFS(Map<String,Vertex> graph, String startNodeName, String goalStateName){	
		Vertex startVertex = graph.get(startNodeName);
		boolean goalFound = false;
		Node startNode = new Node();
		startNode.state = startVertex.getState();
		startNode.parent = null;
		startNode.cost = 0;
		startNode.depth = 0;
		Node goalNode = null;
		LinkedList<Node> frontier = new LinkedList<Node>();
		Map<String,State> explored = new HashMap<String,State>();
		
		frontier.addFirst(startNode);
		
		if(startNode.getState().getName().equals(goalStateName)){
			frontier.poll();
			goalFound = true;
			goalNode = startNode;
		}
				
		while(!frontier.isEmpty()){
// Now how to generate and traverse in DFS???
		}
		
		//Print goalNodePath
		List<String> path = new ArrayList<String>();
		while(goalNode!=null){
			System.out.println(goalNode.getState().getName()+" "+goalNode.depth);
			path.add(goalNode.getState().getName()+" "+goalNode.depth);
			goalNode = goalNode.parent;
		}
		
		
		return path;

	}
}
