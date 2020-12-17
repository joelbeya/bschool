import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SimpleLoan} from '../models/simple-loan';
import {Observable} from 'rxjs';
import {Loan} from '../models/loan';
import {SimpleMail} from '../models/simple-mail';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  private baseUrl = 'http://localhost:8080/rest/api/';

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Save a new simpleLoan object in the Backend server data base.
   */
  saveLoan(simpleLoan: SimpleLoan): Observable<Loan> {
    return this.httpClient.post<Loan>(`${this.baseUrl}/loan/create`, simpleLoan);
  }

  /**
   * Close an existing loan object in the Backend server data base.
   */
  closeLoan(simpleLoan: SimpleLoan): Observable<boolean> {
    return this.httpClient.post<boolean>(`${this.baseUrl}/loan/close`, simpleLoan);
  }

  /**
   * Search Loans by email
   */
  searchLoansByEmail(email: string): Observable<Loan[]> {
    return this.httpClient.get<Loan[]>(`${this.baseUrl}/loan/searchCustomersLoansByEmail/${email}`);
  }

  /**
   * Search Loans by maximum date
   */
  searchLoansByMaximumDate(maxDate: Date): Observable<Loan[]> {
    const month: string = maxDate.getMonth() < 10 ? '0' + (maxDate.getMonth() + 1) : '' + (maxDate.getMonth() + 1);
    const dayOfMonth: string = maxDate.getDate() < 10 ? '0' + maxDate.getDate() : '' + maxDate.getDate();
    const maxEndDate: string = maxDate.getFullYear() + '-' + month + '-' + dayOfMonth;
    return this.httpClient.get<Loan[]>(`${this.baseUrl}/loan/maxEndDate/${maxEndDate}`);
  }

  /**
   * Send an email to a customer
   */
  sendEmail(simpleMail: SimpleMail): Observable<boolean> {
    const headers = new HttpHeaders();
    headers.append('content-type', 'application/json');
    headers.append('accept', 'application/json');
    return this.httpClient.put<boolean>(`${this.baseUrl}/customer/sendEMail`, simpleMail, {headers});
  }

}
