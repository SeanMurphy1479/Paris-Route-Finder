module com.example.city_of_paris_router_finder_ca2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.city_of_paris_router_finder_ca2 to javafx.fxml;
    exports com.example.city_of_paris_router_finder_ca2;
    exports com.example.city_of_paris_router_finder_ca2.Models;
    opens com.example.city_of_paris_router_finder_ca2.Models to javafx.fxml;
}