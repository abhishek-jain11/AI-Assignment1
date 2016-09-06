import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Node implements Comparable<Node> {
	State state;
	Node parent;
	List<Node> children;
	Integer cost;
	int depth;
	Integer order;
	public Node(){
	 this.children = new ArrayList<Node>();				
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public int getCost() {
		return cost;
	}
	
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(Node n){
		 if(this.cost.compareTo(n.cost)!=0) {
			 return this.cost.compareTo(n.cost);
		 }else{
			 return this.order.compareTo(n.order);
		 }
	}
	
	@Override
	public boolean equals(Object n){
		if(n==null){
			return false;
		}
		
		if(n instanceof Node){
			Node node = (Node)n;
			if(this.getState().getName().equals(node.getState().getName())){
				return true;
				}
			}
		return false;
	}
	
	@Override
	public String toString(){
		return "Name: "+this.state.getName()+" Parent: "+this.getParent().getState().getName()+" Cost: "+this.getCost()+" Order: "+this.getOrder();
	}
	
}
