import java.util.List;
import java.util.Map;

public class Hw1VO {

	String algo;
	String startState;
	String goalState;
	String[] liveTrafficLines;
	int noLiveTrafficLines;
	String[] sundayTrafficLines;
	int noSundayTrafficLines;
	List<Edge> edges;
Map<String,Vertex> graph;
	public Map<String, Vertex> getGraph() {
	return graph;
}
public void setGraph(Map<String, Vertex> graph) {
	this.graph = graph;
}
	public String getAlgo() {
		return algo;
	}
	public void setAlgo(String algo) {
		this.algo = algo;
	}
	public String getStartState() {
		return startState;
	}
	public void setStartState(String startState) {
		this.startState = startState;
	}
	public String getGoalState() {
		return goalState;
	}
	public void setGoalState(String goalState) {
		this.goalState = goalState;
	}
	public String[] getLiveTrafficLines() {
		return liveTrafficLines;
	}
	public void setLiveTrafficLines(String[] liveTrafficLines) {
		this.liveTrafficLines = liveTrafficLines;
	}
	public double getNoLiveTrafficLines() {
		return noLiveTrafficLines;
	}
	public void setNoLiveTrafficLines(int noLiveTrafficLines) {
		this.noLiveTrafficLines = noLiveTrafficLines;
	}
	public String[] getSundayTrafficLines() {
		return sundayTrafficLines;
	}
	public void setSundayTrafficLines(String[] sundayTrafficLines) {
		this.sundayTrafficLines = sundayTrafficLines;
	}
	public double getNoSundayTrafficLines() {
		return noSundayTrafficLines;
	}
	public void setNoSundayTrafficLines(int noSundayTrafficLines) {
		this.noSundayTrafficLines = noSundayTrafficLines;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	
	
}
