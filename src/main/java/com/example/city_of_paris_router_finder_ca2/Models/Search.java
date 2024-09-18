package com.example.city_of_paris_router_finder_ca2.Models;

import javafx.scene.paint.Color;

import java.util.*;

public class Search <T> {

    // Starting Node
    private GraphNodeAL<T> start;

    //Ending node
    private GraphNodeAL<T> end;

    //The Route from Start to End
    private List<GraphNodeAL<T>> route;

    public Search(GraphNodeAL<T> start, GraphNodeAL<T> end){
        this.start = start;
        this.end = end;
    }

    //Getters and Setters
    public GraphNodeAL<T> getStart() {
        return start;
    }

    public void setStart(GraphNodeAL<T> start) {
        this.start = start;
    }

    public GraphNodeAL<T> getEnd() {
        return end;
    }

    public void setEnd(GraphNodeAL<T> end) {
        this.end = end;
    }

    public List<GraphNodeAL<T>> getRoute() {
        return route;
    }

    public void setRoute(List<GraphNodeAL<T>> route) {
        this.route = route;
    }

    //BFS Algorithm from start to end
    public void BFS(){

        //if there isn't a start or end stop
        if (start == null || end == null){
            return;
        }
        //Generating the Queue, Visited and Previous
        Queue<GraphNodeAL<T>> queue = new LinkedList<>();

        Set<GraphNodeAL<T>> visited = new HashSet<>();

        Map<GraphNodeAL<T>, GraphNodeAL<T>> previous = new HashMap<>();

        queue.add(start);

        while (!queue.isEmpty()){

            GraphNodeAL<T> current = queue.poll();

            if (current.equals(end)){
                break;
            }
            //Go through all edges and add the node that is next to the queue and makes it visited and added to the Previous Map
            for (GraphEdge edge : current.getAdjList()){
                GraphNodeAL<T> next = (GraphNodeAL<T>) edge.getDestinationNode();
                if(!visited.contains(next)){
                    visited.add(next);
                    queue.add(next);
                    previous.put(next, current);
                }
            }
        }
        //Sets the route
        route = getNodeListFromMap(previous, start, end);
    }

    public void Dijkstra(GraphNodeAL<T>[] graph){


        //Generating the Queue, Visited and Previous
        Queue<GraphNodeAL<T>> queue = new LinkedList<>();

        Map<GraphNodeAL<T>, GraphNodeAL<T>> previous = new HashMap<>();

        Map<GraphNodeAL<T>, Integer> cost = new HashMap<>();

        for (GraphNodeAL<T> node : graph){
            cost.put(node, Integer.MAX_VALUE);
        }
        cost.put(start,0);

        queue.add(start);

        while (queue.size() != 0){

            GraphNodeAL<T> current = queue.poll();

            // Go through each edge
            for (GraphEdge edge : current.getAdjList()){

                GraphNodeAL next = edge.getDestinationNode();

                int nextCost = edge.getCost();

                //if the cost from the current + cost is less then the route we take then update
                if (cost.get(current) + nextCost < cost.get(next)){
                    cost.put(next, cost.get(current) + nextCost);

                    previous.put(next, current);

                    queue.add(next);
                }
            }
        }
        // Sets the route
        route = getNodeListFromMap(previous, start, end);
    }

    public void DFS(){

        //if there isn't a start or end stop
        if (start == null || end == null){
            return;
        }

        //Generating the Stack, Visited and Previous
        Stack<GraphNodeAL<T>> stack = new Stack<>();
        Set<GraphNodeAL<T>> visited = new HashSet<>();
        Map<GraphNodeAL<T>, GraphNodeAL<T>> previous = new HashMap<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            GraphNodeAL<T> current = stack.pop();

            if (current.equals(end)) {
                break;
            }

            if (!visited.contains(current)) {
                visited.add(current);

                //Go through each edge
                for (GraphEdge edge : current.getAdjList()){
                    GraphNodeAL next = edge.getDestinationNode();
                    //if haven't visited add it to being visited
                    if(!visited.contains(next)){
                        stack.push(next);
                        previous.put(next, current);
                    }
                }
            }
        }
        // Sets the route
        route = getNodeListFromMap(previous, start, end);
    }

    // Makes a list of nodes based of the start and end with the map of the bfs
    private List<GraphNodeAL<T>> getNodeListFromMap(Map<GraphNodeAL<T>, GraphNodeAL<T>> map, GraphNodeAL<T> start, GraphNodeAL<T> end){
        List<GraphNodeAL<T>> nodeList = new ArrayList<>();
        while (!end.equals(start)){
            nodeList.add(end);
            end = map.get(end);
        }
        return nodeList;
    }

    // Adds routes that are to be taken and puts them into a single route in order
    public static List<GraphNodeAL<?>> addNodePaths(List<List<GraphNodeAL<Color>>> routes){

        List<GraphNodeAL<?>> finalRoute = new ArrayList<>(routes.get(0));
        for (int i = 1; i < routes.size(); i++){
            finalRoute.addAll(routes.get(i));
        }
        return finalRoute;
    }

}
