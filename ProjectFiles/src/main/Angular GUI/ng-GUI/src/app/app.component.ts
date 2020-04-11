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

  today = new Date();           // used to get current date
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

      this.openSnackBarBooking('Close');
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

      this.openSnackBarAvailability('Close');

    })
    .catch((error) => {
      console.log(error);
    });
  }


  bookVehicle() {
    console.log('book vehicle');

    try {
      this.postBookingData();     // call post method to book vehicle
    } catch {
      this.snackBar.open('Make sure that you have chosen the required vehicle and entered the pick up & drop off dates!', 'Close', {
        duration: 10000,
        panelClass: ['error-snackbar']
      });
    }
  }

  checkAvailability() {
    console.log('check availability of vehicle');

    try {
      this.postCheckingData();     // call post method to check availability
    } catch {
      this.snackBar.open('Make sure that you have chosen the required vehicle and entered the pick up & drop off dates!', 'Close', {
        duration: 10000,
        panelClass: ['error-snackbar']
      });
    }
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


  openSnackBarBooking(action: string) {      // content to display when a vehicle is requested to be booked
    if (this.responseCheck === 'successful') {

      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
      this.displayPickUpDate = this.pickUpDate.toLocaleDateString('en-US', options);
      this.displayDropOffDate = this.dropOffDate.toLocaleDateString('en-US', options);

      const dateRange = ' was booked from '.concat(this.displayPickUpDate).concat(' to ', this.displayDropOffDate);
      this.completeMessage = 'The vehicle with Plate No: '.concat(this.chosenPlateNo).concat(dateRange);
      console.log(this.completeMessage);

      this.snackBar.open(this.completeMessage, action, {
        duration: 15000,
        panelClass: ['success-snackbar']
      });

    } else {
      const chosenVehicle = 'The vehicle with Plate No: '.concat(this.chosenPlateNo);
      this.completeMessage = chosenVehicle.concat(' isn\'t available for booking during the requested time period.');
      this.snackBar.open(this.completeMessage, action, {
        duration: 10000,
      });
    }
  }


  openSnackBarAvailability(action: string) {      // content to display when the availability of a vehicle is checked
    if (this.responseCheck === 'successful') {

      const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
      this.displayPickUpDate = this.pickUpDate.toLocaleDateString('en-US', options);
      this.displayDropOffDate = this.dropOffDate.toLocaleDateString('en-US', options);

      const dateRange = ' is available for booking from '.concat(this.displayPickUpDate).concat(' to ', this.displayDropOffDate);
      this.completeMessage = 'The vehicle with Plate No: '.concat(this.chosenPlateNo).concat(dateRange);
      console.log(this.completeMessage);

      this.snackBar.open(this.completeMessage, action, {
        duration: 15000,
        panelClass: ['success-snackbar']
      });
    } else {
      const chosenVehicle = 'The vehicle with Plate No: '.concat(this.chosenPlateNo);
      this.completeMessage = chosenVehicle.concat(' isn\'t available for booking during the requested time period.');
      this.snackBar.open(this.completeMessage, action, {
        duration: 10000,
      });
    }
  }

}

/*References:

https://www.w3schools.com/angular/angular_tables.asp
https://stackoverflow.com/questions/34973654/angularjs-create-a-table-from-an-array


https://stackoverflow.com/questions/22209117/create-table-from-json-data-with-angularjs-and-ng-repeat/22209337


https://material.angular.io/components/table/overview


Booking confirmation - Snack bar
https://material.angular.io/components/snack-bar/overview
https://stackoverflow.com/questions/56389290/angular-material-snackbar-change-color

Calendar Date input
https://material.angular.io/components/datepicker/overview

Button
https://material.angular.io/components/button/overview

mat table selection
https://stackblitz.com/edit/mat-table-row-click-event
https://stackoverflow.com/questions/52759637/how-to-get-the-selected-row-values-in-table-in-angular

matDatePicker
https://angular-material-nw1brd.stackblitz.io/
https://stackoverflow.com/questions/54828459/angular-material-date-picker-min-and-max-date-validation-messages

https://www.devglan.com/angular/angular-data-table-example


JS Date
https://www.w3schools.com/js/js_date_methods.asp
https://stackoverflow.com/questions/3552461/how-to-format-a-javascript-date

https://www.tutorialspoint.com/typescript/typescript_string_concat.htm


Typescript passing functions
https://stackoverflow.com/questions/47813442/could-not-able-to-access-property-of-angular-component-inside-it

*/
