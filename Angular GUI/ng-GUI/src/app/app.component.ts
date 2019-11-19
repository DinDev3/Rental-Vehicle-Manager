import { Component, OnInit } from '@angular/core';
import {VehicleService} from '../app/services/vehicle.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {

  title = 'ng-GUI';
  heading: string;

  constructor(private vehicleService: VehicleService) { }     // creating an instance of the service


  ngOnInit() {
    this.heading = 'Vehicle List';

  }

  getServiceData() {
    this.vehicleService.getData().subscribe(   // requesting service for information received from the backend
      data => {
        console.log(data);    // what to do with the received data
      }
    );
  }

}
