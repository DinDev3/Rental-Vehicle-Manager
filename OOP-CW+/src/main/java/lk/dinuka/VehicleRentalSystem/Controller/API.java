package lk.dinuka.VehicleRentalSystem.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.dinuka.VehicleRentalSystem.Model.Schedule;
import spark.Spark;

import java.time.LocalDate;

import static lk.dinuka.VehicleRentalSystem.Controller.GUIController.createBooking;
import static spark.Spark.*;

public class API {


    public static void getAllVehiclesToFront() {

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String vehiclesPrettyJson = prettyGson.toJson(WestminsterRentalVehicleManager.getVehiclesInSystem());

//        System.out.println("Cars in Json Format: " + vehiclesPrettyJson);

        //GET - used to load data into GUI
        get("/hello", "application/json", (request, response) -> {
            return vehiclesPrettyJson;
        });

    }


    public static void postBookingsFromFront() {

        //get plateNo of vehicle and days. check whether vehicle is available and let the front end know it's availability

        //POST - used to book
        post("/books", (request, response) -> {
            String plateNo = request.queryParams("plateNo");

            //if this doesn't work, get year, month, day separately and create the local date objects here
            LocalDate pickUpDate = LocalDate.parse(request.queryParams("pickUp"));
            LocalDate dropOffDate = LocalDate.parse(request.queryParams("dropOff"));

            Schedule newBooking = new Schedule(pickUpDate,dropOffDate);


            boolean created = GUIController.createBooking(plateNo,newBooking);

            response.status(201); // 201 Created

//            return created;         //true if successful
            return "successful";
        });

    }


    public static void allowHeaders() {
        Spark.staticFiles.location("/assets");
        Spark.staticFiles.header("Access-Control-Allow-Origin", "*");

        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
            res.type("application/json");
        });

    }
}