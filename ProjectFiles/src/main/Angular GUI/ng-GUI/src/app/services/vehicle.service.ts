import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
// import {catchError} from 'rxjs/operators';
import axios from 'axios';


@Injectable({
  providedIn: 'root'
})

export class VehicleService {


  // get endpoint
  endpointURL: any =  'http://localhost:4567/hello';      // back-end URL
  // service makes an HTTP call to the backend
  // bookingURL: any = 'http://localhost:4567/books';

  constructor(private http: HttpClient) { }
  getData(): Observable<any> {
    return this.http.get(this.endpointURL);     // getting JSON from the backend
  }

// POST
  // addBooking(booking: any): Observable<any> {
  //   return this.http.post<any>(this.bookingURL, booking);

  //     // .pipe(
  //     //   catchError(this.handleError('addHero', booking))
  //     // );
  // }

//   private handleError(error: any): Promise<any> {
//     console.error('An error occurred', error);
//     return Promise.reject(error.message || error);
//  }

  // addBooking( ) {
  //   axios.post(this.bookingURL, {
  //     plateNo: 'Fred',
  //     lastName: 'Flintstone'
  //   })
  //   .then(function (response) {
  //     console.log(response);
  //   })
  //   .catch(function (error) {
  //     console.log(error);
  //   });
  // }

}

/*Reference:

https://angular.io/guide/http

*/
