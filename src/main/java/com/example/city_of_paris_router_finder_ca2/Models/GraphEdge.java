package com.example.city_of_paris_router_finder_ca2.Models;

public class GraphEdge<T> {

    private GraphNodeAL<T> destinationNode;
    private int cost;

    public GraphEdge(GraphNodeAL<T> destinationNode, int cost){
        this.destinationNode = destinationNode;
        this.cost = cost;
    }

    public GraphNodeAL<T> getDestinationNode(){
        return destinationNode;
    }

    public void setDestinationNode(GraphNodeAL<T> destinationNode) {
        this.destinationNode = destinationNode;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        if (cost < 0){
            cost = 0;
        }
        this.cost = cost;
    }

    public String toString(){
        return destinationNode.getName() + "(" + cost + ")";
    }
}