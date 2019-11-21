package lk.dinuka.VehicleRentalSystem.Model;

public interface RentalVehicleManager {
    //constants
    int MAX_VEHICLES = 50;


    //methods
    void addVehicle();
    void deleteVehicle();
    void printList();
    void save();
    void viewGUI();

}
