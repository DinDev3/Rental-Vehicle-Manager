package lk.dinuka.VehicleRentalSystem.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.dinuka.VehicleRentalSystem.Controller.WestminsterRentalVehicleManager;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;

public class GUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("List of vehicles in system");

        TableView tableOfVehicles = new TableView();

        //Creating columns to be added to the table
        TableColumn<String, Vehicle> plateNoColumn = new TableColumn<>("Plate No");
        plateNoColumn.setCellValueFactory(new PropertyValueFactory<>("plateNo"));

        TableColumn<String, Vehicle> makeColumn = new TableColumn<>("Make");
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));

        TableColumn<String, Vehicle> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<String, Vehicle> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        availabilityColumn.setMinWidth(100);

        TableColumn<String, Vehicle> engineCapacityColumn = new TableColumn<>("Engine Capacity");
        engineCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        engineCapacityColumn.setMinWidth(130);

        TableColumn<String, Vehicle> dailyCostColumn = new TableColumn<>("Daily Cost");
        dailyCostColumn.setCellValueFactory(new PropertyValueFactory<>("dailyCost"));
        dailyCostColumn.setMinWidth(110);

        TableColumn<String, Vehicle> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<String, Vehicle> transmissionColumn = new TableColumn<>("Transmission");
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        transmissionColumn.setMinWidth(130);

        TableColumn<String, Vehicle> hasAirConColumn = new TableColumn<>("Has Air Conditioning");
        hasAirConColumn.setCellValueFactory(new PropertyValueFactory<>("hasAirCon"));
        hasAirConColumn.setMinWidth(180);

        TableColumn<String, Vehicle> startTypeColumn = new TableColumn<>("Start Type");
        startTypeColumn.setCellValueFactory(new PropertyValueFactory<>("startType"));
        startTypeColumn.setMinWidth(120);

        TableColumn<String, Vehicle> wheelSizeColumn = new TableColumn<>("Wheel Size");
        wheelSizeColumn.setCellValueFactory(new PropertyValueFactory<>("wheelSize"));
        wheelSizeColumn.setMinWidth(130);




        tableOfVehicles.getColumns().addAll(plateNoColumn, makeColumn, modelColumn, availabilityColumn, engineCapacityColumn, dailyCostColumn,
                typeColumn, transmissionColumn, hasAirConColumn, startTypeColumn, wheelSizeColumn);             //adding all the columns to the table

        tableOfVehicles.getItems().addAll(WestminsterRentalVehicleManager.getVehiclesInSystem());           //adding all the vehicles in the available
                                                                                                                // in the vehiclesInSystem ArrayList


        //---------------------------------------------

        VBox parent = new VBox(tableOfVehicles);
        Scene newScene = new Scene(parent);
        primaryStage.setScene(newScene);
        primaryStage.show();


    }
}
