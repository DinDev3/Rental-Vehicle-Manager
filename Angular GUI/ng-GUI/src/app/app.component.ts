import { Component, OnInit } from '@angular/core';
import {VehicleService} from '../app/services/vehicle.service';
import {MatTableDataSource} from '@angular/material';
import axios from 'axios';
import {stringify} from 'query-string';
import {MatSnackBar} from '@angular/material/snack-bar';

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

  displayPickUpDate: any;
  displayDropOffDate: any;

  responseBook: any;
  responseCheck: any = '';

  completeMessage: any;

  bookingURL: any = 'http://localhost:4567/books';
  checkingURL: any = 'http://localhost:4567/checks';


// ---------------
  // tslint:disable-next-line: max-line-length
  displayedColumns: string[] = ['plateNo', 'make', 'model', 'engineCapacity', 'dailyCost', 'type', 'transmission', 'hasAirCon', 'startType', 'wheelSize'];
  dataSource;
// ---------------

  constructor(private vehicleService: VehicleService,
              private snackBar: MatSnackBar) { }     // creating an instance of the service


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

  postBookingData() {      // post plate no & booking data to backend, to book vehicle

    const data = { plateNo: this.chosenPlateNo,
                    yearPickUp: this.pickUpDate.getFullYear(),
                    monthPickUp: this.pickUpDate.getMonth() + 1,    // months are from 0-11
                    dayPickUp: this.pickUpDate.getDate(),
                    yearDropOff: this.dropOffDate.getFullYear(),
                    monthDropOff: this.dropOffDate.getMonth() + 1,    // months are from 0-11
                    dayDropOff: this.dropOffDate.getDate()
                  };

    axios.post(this.bookingURL, stringify(data) , {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }
    )
    .then((response) => {
      // console.log(response.data);
      this.responseBook = response.data;
      // this.openSnackBar('Hello World', 'Dance');
    })
    .catch((error) => {
      console.log(error);
    });

  //   const url = this.bookingURL;
  //   const data = { plateNo: this.chosenPlateNo,
  //                   yearPickUp: this.pickUpDate.getFullYear(),
  //                   monthPickUp: this.pickUpDate.getMonth(),
  //                   dayPickUp: this.pickUpDate.getDate(),
  //                   yearDropOff: this.dropOffDate.getFullYear(),
  //                   monthDropOff: this.dropOffDate.getMonth(),
  //                   dayDropOff: this.dropOffDate.getDate()
  //                 };
  //   console.log(data);      // all the data is getting added into data here
  //   try {
  //   const response = await fetch(url, {
  //     method: 'POST', // or 'PUT'
  //     body: qs.JSON.stringify(data), // data can be `string` or {object}!
  //     headers: {
  //       'Content-Type': 'application/x-www-form-urlencoded'
  //     }
  //   });

  //   const json = await response.json();     // not receiving response at front end
  //   console.log(json);
  //   console.log('Success:', JSON.stringify(json));
  // } catch (error) {
  //   console.error('Error:', error);
  // }
    // this.vehicleService
    //   .addBooking(this.chosenPlateNo);
      // .subscribe(booking => this.bookings.push(booking));
  // }
  }

  postCheckingData() {      // post plate no & booking data to backend, to check availability

    const data = { plateNo: this.chosenPlateNo,
                    yearPickUp: this.pickUpDate.getFullYear(),
                    monthPickUp: this.pickUpDate.getMonth() + 1,    // months are from 0-11
                    dayPickUp: this.pickUpDate.getDate(),
                    yearDropOff: this.dropOffDate.getFullYear(),
                    monthDropOff: this.dropOffDate.getMonth() + 1,    // months are from 0-11
                    dayDropOff: this.dropOffDate.getDate()
                  };

    axios.post(this.checkingURL, stringify(data) , {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }
    )
    .then((response) => {
      console.log(response.data);
      this.responseCheck = response.data;

      this.openSnackBarAvailability('Hi', 'Bye');

    })
    .catch((error) => {
      console.log(error);
    });
  }


  bookVehicle() {
    console.log('book vehicle');


    this.postBookingData();     // call post method to book vehicle

  }

  checkAvailability() {
    console.log('check availability of vehicle');

    // // console.log(this.pickUpDate);
    // console.log(this.pickUpDate.getFullYear());
    // console.log(this.pickUpDate.getMonth());      // one integer less (0-11)
    // console.log(this.pickUpDate.getDate());

    // // console.log(this.dropOffDate);
    // console.log(this.dropOffDate.getFullYear());
    // console.log(this.dropOffDate.getMonth());      // one integer less (0-11)
    // console.log(this.dropOffDate.getDate());

    this.postCheckingData();     // call post method to check availability

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


  // openSnackBar(message: string, action: string) {
  //   this.snackBar.open(message, action, {
  //     duration: 2000,
  //   });
  // }

  openSnackBarAvailability(message: string, action: string) {
    if (this.responseCheck === 'successful') {

      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
      this.displayPickUpDate = this.pickUpDate.toLocaleDateString("en-US", options);
      this.displayDropOffDate = this.dropOffDate.toLocaleDateString("en-US", options);

      const dateRange = ' is available for booking from '.concat(this.displayPickUpDate).concat(' to ', this.displayDropOffDate);
      this.completeMessage = 'The vehicle with Plate No: '.concat(this.chosenPlateNo).concat(dateRange);
      console.log(this.completeMessage);

      this.snackBar.open(this.completeMessage, action, {
        duration: 15000,
      });
    } else {
      this.snackBar.open('Vehicle isn\'t available during the requested time period', action, {
        duration: 10000,
      });
    }

  }
}

