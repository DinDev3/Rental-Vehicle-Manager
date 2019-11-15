package lk.dinuka.VehicleRentalSystem.Controller;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lk.dinuka.VehicleRentalSystem.Model.Car;
import lk.dinuka.VehicleRentalSystem.Model.Motorbike;
import lk.dinuka.VehicleRentalSystem.Model.Schedule;
import lk.dinuka.VehicleRentalSystem.Model.Vehicle;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DatabaseController {

    public static void addToSystemDB(String plateNo, String make, String model, String engineCapacity, double dailyCost, String type, String startType, double wheelSize) {
        //Adding a Motorbike to the Collection

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");

        //Access collection
        MongoCollection<Document> collection = database.getCollection("VehiclesInSystem");

        //create a document
        Document newVehicle = new Document("Plate No", plateNo)
                .append("Make", make)
                .append("Model", model)
                .append("Engine Capacity", engineCapacity)
                .append("Daily Cost", dailyCost)
                .append("Type", type)
                .append("Start Type", startType)
                .append("Wheel Size", wheelSize);

        //insert the document
        collection.insertOne(newVehicle);
    }


    public static void addToSystemDB(String plateNo, String make, String model, String engineCapacity, double dailyCost, String type, String transmission, boolean hasAirCon) {
        //Adding a car to the Collection

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");

        //Access collection
        MongoCollection<Document> collection = database.getCollection("VehiclesInSystem");

        //create a document
        Document newVehicle = new Document("Plate No", plateNo)
                .append("Make", make)
                .append("Model", model)
                .append("Engine Capacity", engineCapacity)
                .append("Daily Cost", dailyCost)
                .append("Type", type)
                .append("Transmission", transmission)
                .append("Air Con", hasAirCon);

        //insert the document
        collection.insertOne(newVehicle);
    }


    public static void deleteFromSystemDB(String plateNo) {     //Deleting an item from the Collection

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");

        //Access collection
        MongoCollection<Document> collection = database.getCollection("VehiclesInSystem");

        collection.deleteOne(Filters.eq("Plate No", plateNo));
    }

    public static void importSystemDB() {   //Importing stored data in db to application (From VehiclesInSystem & BookedVehicles)

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");


        //importing from VehiclesInSystem collection (For allVehicles HashMap & vehiclesInSystem ArrayList)
        //Access collection
        MongoCollection<Document> savedCollection = database.getCollection("VehiclesInSystem");

        for(Document selectedDoc : savedCollection.find()){
            String plateNo = (String)selectedDoc.get("Plate No");
            String make = (String) selectedDoc.get("Make");
            String model = (String) selectedDoc.get("Model");
            String engineCapacity = (String) selectedDoc.get("Engine Capacity");
            double dailyCostD = (double) selectedDoc.get("Daily Cost");
            String type = (String) selectedDoc.get("Type");

            BigDecimal dailyCostBigD = BigDecimal.valueOf(dailyCostD);     //converting double to BigDecimal, to use for calculations


            if(type.equals("Car")){
                String transmission = (String) selectedDoc.get("Transmission");
                boolean hasAirCon = (boolean) selectedDoc.get("Air Con");

                Vehicle storedCar = new Car(plateNo,make,model,engineCapacity,dailyCostBigD,type,transmission,hasAirCon);
                WestminsterRentalVehicleManager.allVehicles.put(plateNo,storedCar);
                WestminsterRentalVehicleManager.vehiclesInSystem.add(storedCar);
//                System.out.println(storedCar);            //to check whether Car was added

            }else if(type.equals("Motorbike")){
                String startType = (String) selectedDoc.get("Start Type");
                double wheelSize = (double) selectedDoc.get("Wheel Size");

                Vehicle storedBike = new Motorbike(plateNo,make,model,engineCapacity,dailyCostBigD,type,startType,wheelSize);
                WestminsterRentalVehicleManager.allVehicles.put(plateNo,storedBike);
                WestminsterRentalVehicleManager.vehiclesInSystem.add(storedBike);
//                System.out.println(storedBike);            //to check whether Motorbike was added
            }

        }


        //================
        //importing from BookedVehicles collection (For bookedVehicles HashMap)

        MongoCollection<Document> bookedCollection = database.getCollection("BookedVehicles");


        for(Document selectedDoc : bookedCollection.find()){
            String plateNo = (String)selectedDoc.get("Plate No");

            Document pickUpObject = (Document) selectedDoc.get("pick up");
            Document dropOffObject = (Document) selectedDoc.get("drop off");


            //breaking down date document to create date using Schedule constructor
            //pick up date
            int yearUp = pickUpObject.getInteger("year");
            int monthUp = pickUpObject.getInteger("month");
            int dayUp = pickUpObject.getInteger("day");
            //drop off date
            int yearDown = dropOffObject.getInteger("year");
            int monthDown = dropOffObject.getInteger("month");
            int dayDown = dropOffObject.getInteger("day");


            Schedule bookedSchedule = new Schedule(yearUp,monthUp,dayUp,yearDown,monthDown,dayDown);


            if (WestminsterRentalVehicleManager.bookedVehicles.containsKey(plateNo)){
                ArrayList bookedDates = WestminsterRentalVehicleManager.bookedVehicles.get(plateNo);
                bookedDates.add(bookedSchedule);
                WestminsterRentalVehicleManager.bookedVehicles.put(plateNo,bookedDates);
            }else{

                ArrayList bookedDate = new ArrayList();
                bookedDate.add(bookedSchedule);
                WestminsterRentalVehicleManager.bookedVehicles.put(plateNo,bookedDate);
            }

        }


    }


    public static void addToBookedDB(String plateNo, int yearUp, int monthUp, int dayUp, int yearDown, int monthDown, int dayDown) {
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://cw_user:123098@cluster0-gxfyy.gcp.mongodb.net/test?retryWrites=true&w=majority");
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("VehicleRentalSystem");

        //Access collection
        MongoCollection<Document> collection = database.getCollection("BookedVehicles");

        //if already existing, delete document and add new document------------

        //create a document
        Document newSchedule = new Document("Plate No", plateNo)
                .append("pick up", new Document("year", yearUp)            //document inside document
                        .append("month", monthUp)
                        .append("day", dayUp))
                .append("drop off", new Document("year", yearDown)            //document inside document
                        .append("month", monthDown)
                        .append("day", dayDown));

        //insert the document
        collection.insertOne(newSchedule);

    }
}
