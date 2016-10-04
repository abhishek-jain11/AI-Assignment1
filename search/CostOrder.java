package search;
public class CostOrder implements Comparable<CostOrder>{
Integer cost;
Integer order;

public CostOrder(Integer cost, Integer order){
	this.cost = cost;
	this.order = order;
}
public Integer getCost() {
	return cost;
}
public void setCost(Integer cost) {
	this.cost = cost;
}
public Integer getOrder() {
	return order;
}
public void setOrder(Integer order) {
	this.order = order;
}




@Override
public String toString(){
	return "Cost: "+this.cost+" Order:"+this.order;
}

@Override
public int compareTo(CostOrder object) {
		if(this.cost>object.cost){
			return 1;
		}else if(this.cost<object.cost){
			return -1; //Tie breaker in UCS for same cost?
		}else if(this.cost.equals(object.cost)){
			return this.order.compareTo(object.order);
		}
		return 0;
}

}
