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
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");




        //import data from both collections at once!!!

        //importing from VehiclesInSystem collection
        //Access collection
        MongoCollection<Document> savedCollection = database.getCollection("VehiclesInSystem");

        for(Document selectedDoc : savedCollection.find()){
            String plateNo = (String)selectedDoc.get("Plate No");
            String make = (String) selectedDoc.get("Make");
            String model = (String) selectedDoc.get("Model");
            boolean availability =  (boolean)selectedDoc.get("Availability");
            double dailyCostD = (double) selectedDoc.get("Daily Cost");
            String type = (String) selectedDoc.get("Type");
            String transmission = (String) selectedDoc.get("Transmission");
            boolean hasAirCon = (boolean) selectedDoc.get("Air Con");


            BigDecimal dailyCostBigD = BigDecimal.valueOf(dailyCostD);     //converting double to BigDecimal, to use for calculations


//            if(type.equals("CD")){
//                double duration = (double) selectedDoc.get("Duration");
//                MusicItem storedCD = new CD(itemID, title, genre, releasedDate, artist, price, type, duration);
//                WestminsterMusicStoreManager.itemsInStore.add(storedCD);
//                WestminsterMusicStoreManager.allItemIDs.put(itemID,type);
////                System.out.println(storedCD);            //to check whether item was added
//
//            }else if(type.equals("Vinyl")){
//                double speed = (double) selectedDoc.get("Speed(RPM)");
//                double diameter = (double) selectedDoc.get("Diameter(cm)");
//                MusicItem storedVinyl = new Vinyl(itemID, title, genre, releasedDate, artist, price, type, speed, diameter);
//                WestminsterMusicStoreManager.itemsInStore.add(storedVinyl);
//                WestminsterMusicStoreManager.allItemIDs.put(itemID,type);
////                System.out.println(storedVinyl);            //to check whether item was added
//            }
        }




        //convert daily cost back to BigDecimal when importing data!!!!



        //================
        //importing from bookedVehicles collection



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
