package search;
public class Edge {

	State startState;
	State destState;
	int cost;
	int order;
	public State getStartState() {
		return startState;
	}
	public void setStartState(State startState) {
		this.startState = startState;
	}
	public State getDestState() {
		return destState;
	}
	public void setDestState(State destState) {
		this.destState = destState;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
	
	}
