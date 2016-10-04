package search;
import java.util.ArrayList;
import java.util.Comparator;
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
		int frontierOrder = 1;
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
		//	child.setOrder(e.order);
			
			n.children.add(child);
		}
		System.out.println("Evalutaing Node "+n.getState().getName());
		System.out.println("Frontier "+frontier.toString() );
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
				child.setOrder(frontierOrder++);
				frontier.add(child);
				frontierCostMap.put(child.getState().getName(), new CostOrder(child.getCost(),child.getOrder()));
				System.out.println("");
				System.out.println("frontier");
				System.out.println(frontier.peek().getCost()+" Name:"+frontier.peek().getState().getName()+" Parent:"+frontier.peek().getParent().getState().getName()+ " Cost: "+frontier.peek().getCost() );
			//	System.out.println(frontier.peek().children);
					}
			else//For updating existing cost
				if(frontier.contains(child) && (frontierCostMap.get(child.getState().getName()).getCost() > child.getCost()) ){
					System.out.println("Updating existing cost");
					System.out.println("Removing node from frontier named "+child.getState().getName());
					frontier.remove(child);
					//TODO: remove in map is not required, can update the value right away. This is only for debugging purposes
					CostOrder removedCostMapNode = frontierCostMap.remove(child.getState().getName());
					System.out.println("Removing node from frontier Cost Map with values "+removedCostMapNode);
					System.out.println("Adding new node to frontier "+child);
					child.setOrder(frontierOrder++);
					frontier.add(child);
					frontierCostMap.put(child.getState().getName(), new CostOrder(child.getCost(),child.getOrder()));
					}
				}
		System.out.println();
			}
		}
		System.out.println(frontier);
		
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
			//	System.out.println(frontier.peek().depth+" "+frontier.peek().getState().name);
			Node n = frontier.poll();
			explored.put(n.getState().getName(), n.getState());
			System.out.println("Node visited "+n);
			if(n.getState().getName().equals(goalStateName)){
				//Goal ELement found;
				goalNode = n;
				goalFound =true;
				break;
			}
			//Explore Node and add children here
			Vertex v =  graph.get(n.getState().getName());
			if(v!=null){
				for(int i=v.getNeighbours().size()-1;i>=0;i--){
					Edge e = v.getNeighbours().get(i);
					Node child = new Node();
					child.state = e.destState;
					child.parent = n;
					child.cost = n.cost + e.getCost();
					child.depth = n.depth  + 1;
					child.setOrder(e.order);
					if(!explored.containsKey(child.getState().getName()) && !frontier.contains(child)){		
					//	System.out.println("Frontier has entry for "+child.getState().getName()+(frontier!=null?frontier.contains(child):"NULL "));
					//	System.out.print("ExploredMap for "+child.getState().getName()+explored.containsKey(child.getState().getName()));
						
						frontier.addFirst(child);
					//	System.out.println("Frontier element "+(frontier!=null?(frontier.peek()!=null?frontier.peek().getState().getName():"NULL"):"NULL"));

							}
					
				//	System.out.println(explored.toString());
					
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
	
	public static List<String> runAstar(Map<String,Vertex> graph, Map<String,Integer>heuristicMap, String startNodeName, String goalStateName){
		Vertex startVertex = graph.get(startNodeName);
		Node startNode = new Node();
		startNode.state = startVertex.getState();
		startNode.parent = null;
		startNode.setCost(0);
		startNode.depth = 0;
		startNode.setOrder(1);
		startNode.setHeuristic(heuristicMap.get(startVertex.getState().getName()));
		startNode.totalCost = startNode.getCost()+startNode.getHeuristic();
		Node goalNode = null;

		Comparator<Node> c = new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				 if(o1.getTotalCost().compareTo(o2.getTotalCost())!=0) {
					 return o1.getTotalCost().compareTo(o2.getTotalCost());
				 }else{
					 return o1.getOrder().compareTo(o2.getOrder());
				 }

			}
		};
		
		//Check when heuristic and cost sum are equal for nodes
		Queue<Node> frontier = new PriorityQueue<Node>(c);
		int frontierOrder =1;
		Map<String,CostOrder> frontierCostMap = new HashMap<String,CostOrder>();
		
		frontier.add(startNode);
		frontierCostMap.put(startNode.getState().getName(), new CostOrder(startNode.getTotalCost(),startNode.getOrder()));
		Map<String,State> explored = new HashMap<String,State>();
		
		
		while(!frontier.isEmpty()){
		//	System.out.println(frontier.peek().depth+" "+frontier.peek().getState().name);

		Node n = frontier.poll();
		System.out.println("Processing Node \n"+n);
		frontierCostMap.remove(n.getState().getName());
		//Explore Node and add children here
		Vertex v =  graph.get(n.getState().getName());
		if(v!=null){
		for(Edge e: v.getNeighbours()){
			Node child = new Node();
			child.state = e.destState;
			child.parent = n;
			child.cost = n.cost + e.getCost() ;
			child.setHeuristic(heuristicMap.get(e.destState.getName()));
			child.depth = n.depth  + 1;
			child.totalCost = child.cost+child.getHeuristic();
			//child.setOrder(e.order);
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

				child.setOrder(frontierOrder++);
				frontier.add(child);
				frontierCostMap.put(child.getState().getName(), new CostOrder(child.getTotalCost(),child.getOrder()));
				System.out.println(child);
				System.out.println("");
				System.out.println("frontier");
				System.out.println("Name:"+frontier.peek().getState().getName()+" Parent:"+frontier.peek().getParent().getState().getName()+ " Order:"+frontier.peek().getOrder()
						+" Cost:"+frontier.peek().getCost()+" Heuristic:"+frontier.peek().getHeuristic()+" TotalCost:"+frontier.peek().getTotalCost());
					}
			else//For updating existing cost
				if(frontier.contains(child) && (frontierCostMap.get(child.getState().getName()).getCost() > child.getTotalCost()) ){
					System.out.println("Updating existing cost");
					System.out.println("Removing node from frontier named "+child.getState().getName());
					frontier.remove(child);
					//TODO: remove in map is not required, can update the value right away. This is only for debugging purposes
					CostOrder removedCostMapNode = frontierCostMap.remove(child.getState().getName());
					System.out.println("Removing node from frontier Cost Map with values "+removedCostMapNode);
					child.setOrder(frontierOrder++);
					System.out.println("Adding new node to frontier "+child);
					
					frontier.add(child);
					frontierCostMap.put(child.getState().getName(), new CostOrder(child.getTotalCost(),child.getOrder()));
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
	
}
