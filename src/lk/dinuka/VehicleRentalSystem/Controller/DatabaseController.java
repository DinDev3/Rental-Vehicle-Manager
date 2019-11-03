package lk.dinuka.VehicleRentalSystem.Controller;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.math.BigDecimal;

public class DatabaseController {

    //have another add method to add booked vehicles!!!!!!!!! (If not done through Angular)

    public static void addToSystemDB(String plateNo, String make, String model, boolean availability, String engineCapacity, double dailyCost, String type, String startType, double wheelSize) {
        //Adding a Motorbike to the Collection

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");

        //Access collection
        MongoCollection<Document> collection = database.getCollection("VehiclesInSystem");

        //create a document
        Document newItem = new Document("Plate No", plateNo)
                .append("Make", make)
                .append("Model", model)
                .append("Availability", availability)
                .append("Engine Capacity", engineCapacity)
                .append("Daily Cost", dailyCost)
                .append("Type", type)
                .append("Start Type", startType)
                .append("Wheel Size", wheelSize);

        //insert the document
        collection.insertOne(newItem);
    }


    public static void addToSystemDB(String plateNo, String make, String model, boolean availability, String engineCapacity, double dailyCost, String type, String transmission, boolean hasAirCon) {
        //Adding a car to the Collection

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");

        //Access collection
        MongoCollection<Document> collection = database.getCollection("VehiclesInSystem");

        //create a document
        Document newItem = new Document("Plate No", plateNo)
                .append("Make", make)
                .append("Model", model)
                .append("Availability", availability)
                .append("Engine Capacity", engineCapacity)
                .append("Daily Cost", dailyCost)
                .append("Type", type)
                .append("Transmission", transmission)
                .append("Air Con", hasAirCon);

        //insert the document
        collection.insertOne(newItem);
    }


    public static void deleteFromSystemDB(String plateNo) {
        //Deleting an item from the Collection

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");

        //Access collection
        MongoCollection<Document> collection = database.getCollection("VehiclesInSystem");

        collection.deleteOne(Filters.eq("Plate No", plateNo));
    }

    public static void importSystemDB() {
        //Importing stored data in db to application (allVehicles hashMap)


        //import data from both collections at once!!!




        //convert daily cost back to BigDecimal when importing data!!!!

    }


    public static void addToBookedDB(String plateNo, int yearUp, int monthUp, int dayUp, int yearDown, int monthDown, int dayDown) {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");

        //Access collection
        MongoCollection<Document> collection = database.getCollection("BookedVehicles");

        //create a document
        Document newItem = new Document("Plate No", plateNo)
                .append("pick up", new Document("year", yearUp)            //document inside document
                        .append("month", monthUp)
                        .append("day", dayUp))
                .append("drop off", new Document("year", yearDown)            //document inside document
                        .append("month", monthDown)
                        .append("day", dayDown));

        //insert the document
        collection.insertOne(newItem);

    }
}
