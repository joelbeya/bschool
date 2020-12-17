import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Customer} from '../models/customer';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private baseUrl = 'http://localhost:8080/rest/api/';

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Save a new Customer object in the Backend server data base.
   */
  saveCustomer(customer: Customer): Observable<Customer> {
    return this.httpClient.post<Customer>(`${this.baseUrl}/customer/create`, customer);
  }

  /**
   * Update an existing Customer object in the Backend server data base.
   */
  updateCustomer(customer: Customer): Observable<Customer> {
    return this.httpClient.put<Customer>(`${this.baseUrl}/customer/update`, customer);
  }

  /**
   * Delete an existing Customer object in the Backend server data base.
   */
  deleteCustomer(customer: Customer): Observable<string> {
    return this.httpClient.delete<string>(`${this.baseUrl}/customer/delete/${customer.id}`);
  }

  /**
   * Search customer by email
   */
  searchCustomerByEmail(email: string): Observable<Customer> {
    return this.httpClient.get<Customer>(`${this.baseUrl}/customer/searchByEmail/${email}`);
  }

  /**
   * Search books by first name
   */
  searchCustomerByFirstName(firstName: string): Observable<Customer[]> {
    return this.httpClient.get<Customer[]>(`${this.baseUrl}/customer/searchByFirstName/${firstName}`);
  }

  /**
   * Search books by last name
   */
  searchCustomerByLastName(lastName: string): Observable<Customer[]> {
    return this.httpClient.get<Customer[]>(`${this.baseUrl}/customer/searchByLastName/${lastName}`);
  }


}
