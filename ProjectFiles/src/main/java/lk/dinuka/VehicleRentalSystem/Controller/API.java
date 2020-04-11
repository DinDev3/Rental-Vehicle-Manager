package lk.dinuka.VehicleRentalSystem.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lk.dinuka.VehicleRentalSystem.Model.Schedule;
import spark.Spark;

import java.time.LocalDate;

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

        //get plateNo of vehicle and days, book vehicle if available and let the front end know it's availability

        //POST - used to book
        post("/books","application/json", (request, response) -> {

            String responsePrettyJson;

            String plateNo = request.queryParams("plateNo");

            int yearPickUp =  Integer.parseInt(request.queryParams("yearPickUp"));
            int monthPickUp =  Integer.parseInt(request.queryParams("monthPickUp"));
            int dayPickUp =  Integer.parseInt(request.queryParams("dayPickUp"));

            int yearDropOff =  Integer.parseInt(request.queryParams("yearDropOff"));
            int monthDropOff =  Integer.parseInt(request.queryParams("monthDropOff"));
            int dayDropOff =  Integer.parseInt(request.queryParams("dayDropOff"));

            LocalDate pickUpDate = LocalDate.of(yearPickUp,monthPickUp,dayPickUp);
            LocalDate dropOffDate = LocalDate.of(yearDropOff,monthDropOff,dayDropOff);

            Schedule newBooking = new Schedule(pickUpDate,dropOffDate);

//            System.out.println(newBooking);

            boolean created = GUIController.createBooking(plateNo,newBooking);

            response.status(201); // 201 Created

            if (created) {      //if booking was created
                Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                responsePrettyJson = prettyGson.toJson("successful");

                // adding new booking to the database
                DatabaseController.addToBookedDB(plateNo, yearPickUp, monthPickUp, dayPickUp,
                        yearDropOff, monthDropOff, dayDropOff);

            }else{      //if booking wasn't created (already booked)
                Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                responsePrettyJson = prettyGson.toJson("unsuccessful");
            }

//            return created;         //true if successful
            return responsePrettyJson;
        });

    }
    public static void postAvailabilityFromFront() {

        //get plateNo of vehicle and days. check whether vehicle is available and let the front end know it's availability

        //POST - used to book
        post("/checks","application/json", (request, response) -> {

            String responsePrettyJson;

            String plateNo = request.queryParams("plateNo");

            int yearPickUp =  Integer.parseInt(request.queryParams("yearPickUp"));
            int monthPickUp =  Integer.parseInt(request.queryParams("monthPickUp"));
            int dayPickUp =  Integer.parseInt(request.queryParams("dayPickUp"));

            int yearDropOff =  Integer.parseInt(request.queryParams("yearDropOff"));
            int monthDropOff =  Integer.parseInt(request.queryParams("monthDropOff"));
            int dayDropOff =  Integer.parseInt(request.queryParams("dayDropOff"));

            LocalDate pickUpDate = LocalDate.of(yearPickUp,monthPickUp,dayPickUp);
            LocalDate dropOffDate = LocalDate.of(yearDropOff,monthDropOff,dayDropOff);

            Schedule newBooking = new Schedule(pickUpDate,dropOffDate);

//            System.out.println(newBooking);

            boolean created = GUIController.checkAvailabilityOfVeh(plateNo,newBooking);

            response.status(201); // 201 Created

            if (created) {      //if booking was created
                Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                responsePrettyJson = prettyGson.toJson("successful");
            }else{      //if booking wasn't created (already booked)
                Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                responsePrettyJson = prettyGson.toJson("unsuccessful");
            }

//            return created;         //true if successful
            return responsePrettyJson;
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


/*
References:

https://github.com/perwendel/spark
http://sparkjava.com/documentation#response-transformer

JSON and java objects
https://github.com/google/gson
https://www.baeldung.com/spark-framework-rest-api

https://gist.github.com/saeidzebardast/e375b7d17be3e0f4dddf#gistcomment-2704256

https://technology.finra.org/code/serialize-deserialize-interfaces-in-java.html

https://crunchify.com/in-java-how-to-convert-arraylist-to-jsonobject/

Make http requests from browser
https://github.com/axios/axios

https://gist.github.com/akexorcist/ea93ee47d39cf94e77802bc39c46589b

*/