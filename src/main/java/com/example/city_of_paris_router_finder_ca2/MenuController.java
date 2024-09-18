package com.example.city_of_paris_router_finder_ca2;

import com.example.city_of_paris_router_finder_ca2.Models.GraphNodeAL;
import com.example.city_of_paris_router_finder_ca2.Models.Processor;
import com.example.city_of_paris_router_finder_ca2.Models.Search;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MenuController  {

    @FXML
    private ImageView imageView, bw;

    private Image  image, original;

    private ArrayList<Integer> points = new ArrayList<>(4);



    //    //the method initializes an array of objects containing strings
    @FXML private GraphNodeAL<String>[] landmarks;

    public GraphNodeAL<String>[] initialize(){
        try {
            landmarks = loadLandmarks();
            return landmarks;
        } catch (Exception e) {
            System.out.println(e);
        }
        return landmarks;
    }


    @FXML
    public void openImage() {
        // The fileChooser chooses a new image
        FileChooser fileChooser = new FileChooser();
        //sets up a file chooser dialog for the user to select a file with .png
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(null);
        //checks if the user selected a file
        if (selectedFile != null) {
            //creates the image object using the URI of the file
            image = new Image(selectedFile.toURI().toString());
            //sets the imageView to the selected image
            imageView.setImage(image);
            //stores the image for later
            original = imageView.getImage();
        }
    }

    @FXML
    public void imageClicked(MouseEvent e){
        //gets the coords of the mouse click
        double x = e.getX();
        double y = e.getY();

        //calcs the coords of the mouse click relative to the size of the image
        int roundedX = (int) (x / imageView.getBoundsInLocal().getWidth() * image.getWidth());
        int roundedY = (int) (y / imageView.getBoundsInLocal().getHeight() * image.getHeight());

        //checks if the clicked coords are in the bounds of the image
        if (roundedX >= 0 && roundedX < (int) image.getWidth() && roundedY >= 0 && roundedY < (int) image.getHeight()) {
            if (points.size() / 2 < 10) {
                //draws the circle at the coords of the clicked coords
                drawCircle((int) x, (int) y);

                //adds the coords to a list of points for later
                points.add(roundedX);
                points.add(roundedY);
            }
        }
    }

    //draws the red circle on the x,y coords
    public void drawCircle(int x, int y){
        Circle circle = new Circle();

        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(2.5);

        circle.setFill(Color.RED);

        ((Pane) imageView.getParent()).getChildren().add(circle);
    }

    //clears all drawings
    @FXML
    public void clear(){
        ((Pane) imageView.getParent()).getChildren().removeIf(c -> c instanceof Circle);
        points = new ArrayList<>(4);
        imageView.setImage(image);
    }
    private boolean useBFS = true;
    private boolean useDFS = false;
    //enables the usage of the BFS calls from the other method
    public void toggleBFS() {
        useBFS = true;
        useDFS = false;
        findPathBetweenPoints();

    }
    //enables the usage of the Dijkstra calls from the other method
    public void toggleDijkstra() {
        useBFS = false;
        useDFS = false;
        findPathBetweenPoints();
    }
    //enables the usage of the DFS calls from the other method
    public void toggleDFS(){
        useBFS = false;
        useDFS = true;
        findPathBetweenPoints();
    }

    //finds the paths between the certain points on the image
    public void findPathBetweenPoints() {
        //try catch for handling
        try {
            //converts the original image into Black and white
            Image bwImage = Processor.convertToBW(image, 2.6);
            //it then creates graph nodes from the balck and white image.
            GraphNodeAL<Color>[] nodes = Processor.createNodesFromBW(bwImage);

            //stores the paths from the algorithms
            List<List<GraphNodeAL<Color>>> bfsRoute = new ArrayList<>();
            //iterates throught the points lists to find the paths
            for (int i = 0; i < points.size(); i += 2) {
                if (i + 2 >= points.size()) {
                    continue;
                }
                //gets the start and end points
                GraphNodeAL<Color> start = Processor.getNodesOnMouseClick(bwImage, points.get(i), points.get(i + 1), nodes);
                GraphNodeAL<Color> end = Processor.getNodesOnMouseClick(bwImage, points.get(i + 2), points.get(i + 3), nodes);

                Search search = new Search(start, end);

                //it then draws the route using the draw route method and sets the route on the image
                if(useBFS) {
                search.BFS();
                } else if (useDFS) {
                    search.DFS();
                } else {
                  search.Dijkstra(nodes);
                  }

                bfsRoute.add(search.getRoute());
            }
            List<GraphNodeAL<?>> total = Search.addNodePaths(bfsRoute);

            imageView.setImage(Processor.drawRoute(image, total));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //creates an array of graph nodes with all the landmarks present on the image
    public static GraphNodeAL<String>[] loadLandmarks(){
        //all the landmarks are pre-stored
        GraphNodeAL<String> eiffel = new GraphNodeAL<>("Eiffel Tower",137,260);
        GraphNodeAL<String> musee = new GraphNodeAL<>("Musee du Louvre", 348,239);
        GraphNodeAL<String> notre = new GraphNodeAL<>("Notre-Dame de Paris", 418,296);
        GraphNodeAL<String> tromphe = new GraphNodeAL<>("Arc de Tromphe", 150,137);
        GraphNodeAL<String> pantheon = new GraphNodeAL<>("Pantheon", 412, 373);
        GraphNodeAL<String> sainte = new GraphNodeAL<>("Sainte-Chapelle", 407, 290);
        GraphNodeAL<String> garnier = new GraphNodeAL<>("Palais Garnier", 337, 162);
        GraphNodeAL<String> concorde = new GraphNodeAL<>("Place de la Concorde", 277, 203);
        GraphNodeAL<String> invalides = new GraphNodeAL<>("Hotel des Invalides", 237, 264);

        //creates the string objects with the landmarks
        GraphNodeAL<String>[] landmarks = new GraphNodeAL[9];

        //each landmark is assigned with an index
        landmarks[0] = eiffel;
        landmarks[1] = musee;
        landmarks[2] = notre;
        landmarks[3] = tromphe;
        landmarks[4] = pantheon;
        landmarks[5] = sainte;
        landmarks[6] = garnier;
        landmarks[7] = concorde;
        landmarks[8] = invalides;

        //connections are made with the landmarks
        eiffel.connectToNodeUndirected(tromphe, 10);
        musee.connectToNodeUndirected(garnier,10);
        notre.connectToNodeUndirected(sainte,10);
        pantheon.connectToNodeUndirected(sainte,10);
        sainte.connectToNodeUndirected(garnier,10);
        garnier.connectToNodeUndirected(invalides,10);
        concorde.connectToNodeUndirected(musee,10);
        invalides.connectToNodeUndirected(eiffel,10);

        //the array is returned with the landmark nodes
        return landmarks;
    }

    //redundant method not in use anymore but used to get the X and y of locations
    @FXML
    private void imageClickXY(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        int roundedX = (int) (x / imageView.getBoundsInLocal().getWidth() );
        int roundedY = (int) (y / imageView.getBoundsInLocal().getHeight());
        int imageWidth = (int) imageView.getBoundsInLocal().getWidth();
        int imageHeight = (int) imageView.getBoundsInLocal().getHeight();
        if(roundedX >=0 && roundedX < imageWidth && roundedY >=0 && roundedY < imageHeight ){
            System.out.println("Mouse clicked at: ( " + x + ", " + y + ")");
        }
    }

    //loads all the landmarks onto the image and puts a purple circle onto
    public void addLandmarks(){
        Circle[] landmarks = new Circle[loadLandmarks().length];

        for(int i = 0; i < loadLandmarks().length; i++){
            landmarks[i] = new Circle(loadLandmarks()[i].getX(), loadLandmarks()[i].getY(), 3,Color.PURPLE);
            ((Pane) imageView.getParent()).getChildren().add(landmarks[i]);
        }

    }
    // Exists the system
    @FXML
    private void exit(){
        System.exit(0);
    }


}