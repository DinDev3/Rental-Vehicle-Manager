import { Component, OnInit } from '@angular/core';
import {VehicleService} from '../app/services/vehicle.service';
import {MatTableDataSource} from '@angular/material';

// --------------------------------------

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})


export class AppComponent implements OnInit {

  title = 'ng-GUI';
  heading: string;

  getAllVehicles: any;
  allVehicles: any[];

  selectedRowIndex = -1;
  chosenPlateNo: string;
  pickUpDate: any;
  dropOffDate: any;

// ---------------
  // tslint:disable-next-line: max-line-length
  displayedColumns: string[] = ['plateNo', 'make', 'model', 'engineCapacity', 'dailyCost', 'type', 'transmission', 'hasAirCon', 'startType', 'wheelSize'];
  dataSource;
// ---------------

  constructor(private vehicleService: VehicleService) { }     // creating an instance of the service

  ngOnInit() {
    this.heading = 'Vehicle List';

    this.getAllVehicles = this.getServiceData();
    // console.log(this.getAllVehicles);
  }

  getServiceData() {        // get data
    this.vehicleService.getData().subscribe(   // requesting service for information received from the backend
      data => {
        // console.log(data);    // what to do with the received data
        // const jsonInfo = JSON.parse(data);
        this.allVehicles  = data;
        this.dataSource = new MatTableDataSource(this.allVehicles);
        console.log(this.allVehicles);
      }
    );
  }

  // postBookingData(){       // post plate no & booking data to backend
  //   this.vehicleService
  //     .addBooking(newBooking)
  //     .subscribe(booking => this.bookings.push(booking));
  // }

  bookVehicle() {
    console.log('book vehicle');
  }

  checkAvailability() {
    console.log('check availability of vehicle');

    console.log(this.pickUpDate);
    console.log(this.pickUpDate.getFullYear());
    console.log(this.pickUpDate.getMonth());      // one integer less (0-11)
    console.log(this.pickUpDate.getDate());

    console.log(this.dropOffDate);
    console.log(this.dropOffDate.getFullYear());
    console.log(this.dropOffDate.getMonth());      // one integer less (0-11)
    console.log(this.dropOffDate.getDate());

  }

  // -------------  for table filter function
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  rowClicked(row: any): void {
    console.log(row);
    this.chosenPlateNo = row.plateNo;
    console.log(this.chosenPlateNo);

    this.selectedRowIndex = row.plateNo;
  }
}

