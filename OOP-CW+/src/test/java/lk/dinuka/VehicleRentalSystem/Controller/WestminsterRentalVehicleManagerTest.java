package lk.dinuka.VehicleRentalSystem.Controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class WestminsterRentalVehicleManagerTest {




    @Test
    public void addVehicle() {

    }

    @Test
    public void testDeleteVehicle() {
        String plateNo = "40";


    }

    @Test
    public void printList() {
    }


    //testing write/ save file ---------------
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testCreateFile() throws IOException {
        File file = tempFolder.newFile("test.txt");
        assertTrue(file.exists());
    }
    // ----------------


    @Test
    public void testViewGUI() {
    }
}