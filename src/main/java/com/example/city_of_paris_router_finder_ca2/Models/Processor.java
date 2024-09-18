package com.example.city_of_paris_router_finder_ca2.Models;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.List;

public class Processor {

    //Makes a version of the image but in black and white where white is the "road" pixels that are bright enough to be the road
    public static Image convertToBW(Image image, double threshold) {
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage bw = new WritableImage(width, height);
        PixelWriter pixelWriter = bw.getPixelWriter();
        // Go through each pixel of the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //Get the colour of each pixel
                Color c = pixelReader.getColor(x, y);
                double sum = c.getRed() + c.getGreen() + c.getBlue();
                // if greater than the threshold it must be white or close to it and must be part of the road
                if (sum > threshold) {
                    pixelWriter.setColor(x, y, Color.WHITE);
                } else {
                    pixelWriter.setColor(x, y, Color.BLACK);
                }
            }
        }
        return bw;
    }

    //Creates an array of nodes from the bwImage
    public static GraphNodeAL<Color>[] createNodesFromBW(Image image) {

        //Every pixel gets a node, but we only want white nodes co black remain null;
        GraphNodeAL<Color>[] nodes = new GraphNodeAL[(int) (image.getWidth() * image.getHeight())];

        PixelReader pixelReader = image.getPixelReader();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (pixelReader.getColor(x, y).equals(Color.BLACK)) {
                    continue;
                }
                //Create node
                GraphNodeAL<Color> node = new GraphNodeAL<>("PATH@" + x + ":" + y, x, y);
                //give the node its colour
                node.setData(pixelReader.getColor(node.getX(), node.getY()));

                //setting the correct node in the array to the new node
                nodes[(int) ((y * image.getWidth() + x))] = node;
            }
        }
        return createNodesFromImage(image, nodes);
    }

    // Adds edges to each node based on their position
    private static GraphNodeAL[] createNodesFromImage(Image image, GraphNodeAL[] nodes) {
        for (int i = 0; i < nodes.length; i++) {

            // If the node is null / black ignore it
            if (nodes[i] == null) {
                continue;
            }

            //Check for a node to the right
            if ((i + 1) % (int) image.getWidth() != 0) {
                if (i + 1 < nodes.length) {
                    if (nodes[i + 1] != null) {
                        nodes[i].connectToNodeUndirected(nodes[i + 1], 1);
                    }
                }
            }
            //Check for a node below
            if (!(i + image.getWidth() >= nodes.length)) {
                if (nodes[i + (int) image.getWidth()] != null) {
                    nodes[i].connectToNodeUndirected(nodes[i + (int) image.getWidth()], 1);
                }
            }
        }
        return nodes;
    }


    //Return the node at the clicked location
    public static GraphNodeAL<Color> getNodesOnMouseClick(Image image, int x, int y, GraphNodeAL<Color>[] nodes) {

        return nodes[(int) ((y * image.getWidth() + x))];
    }


    //Draws the pixels along the route on the image int a multi colour
    public static Image drawRoute(Image image, List<GraphNodeAL<?>> nodeRoute) {
        WritableImage writableImage = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

            Color color = Color.RED;
            for (GraphNodeAL node : nodeRoute) {
                color = color.deriveColor(1, 1, 1, 1);
                pixelWriter.setColor(node.getX(), node.getY(),color );
            }

        return writableImage;
    }

    //Gets the cost of the route only needed for Dijkstra's algorithm if we wanted to find the cost
    public static int getCostOfRoute(List<GraphNodeAL<?>> route){
        if(route.size() <= 1){
            return 0;
        }
        int cost = 0;
        for (int i = 0; i < route.size() - 1; i++){
            GraphNodeAL<?> current = route.get(i);
            GraphNodeAL<?> next = route.get(i + 1);

            for (GraphEdge edge : current.getAdjList()){
                if(edge.getDestinationNode().equals(next));
                cost += edge.getCost();
            }
        }
        return cost;
    }
}



