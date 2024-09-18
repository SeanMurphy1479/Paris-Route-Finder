package com.example.city_of_paris_router_finder_ca2.Models;

import java.util.ArrayList;
import java.util.List;

public class GraphNodeAL<T> {
    public T data;
    private int x,y;
    private String name;

    public List<GraphEdge> adjList = new ArrayList<>();

    public GraphNodeAL(String nodeName, int x, int y){
        setName(nodeName);
        setX(x);
        setY(y);
    }

    public GraphNodeAL(T data, String nodeName, int x, int y){
        this.data = data;
        setName(nodeName);
        setX(x);
        setY(y);
    }

    public void connectToNodeDirected(GraphNodeAL<T> destNode, int cost){
        adjList.add(new GraphEdge(destNode,cost));
    }

    public void connectToNodeUndirected(GraphNodeAL<T> destNode, int cost){
        adjList.add(new GraphEdge(destNode, cost));
        destNode.adjList.add(new GraphEdge(this, cost));
    }

    public T getData() {
        return data;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public List<GraphEdge> getAdjList() {
        return adjList;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setX(int x) {
        if(x < 0) {
            return;
        }
        this.x = x;
    }

    public void setY(int y) {
        if(y < 0){
            return;
        }
        this.y = y;
    }

    public void setName(String name) {
        if(name.length() > 30){
            return;
        }
        this.name = name;
    }

    @Override
    public String toString() {
        String connected = "";
        for (GraphEdge edge : adjList){
            connected += edge.toString() + "\n";
        }

        return "Name: " + name + ", Coords: (" + x + ", " + y + ") \n" +
                "Connected to: \n" +
                connected;
    }
}
