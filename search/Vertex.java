package search;
import java.util.ArrayList;
import java.util.List;

public class Vertex {

	
	State state;
	List<Edge> neighbours;
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public List<Edge> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(List<Edge> neighbours) {
		this.neighbours = neighbours;
	}
	
	public Vertex(){
		this.neighbours = new ArrayList<Edge>();
	}
	
}
