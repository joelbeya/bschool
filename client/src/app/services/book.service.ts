import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Category} from '../models/category';
import {Book} from '../models/book';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private baseUrl = 'http://localhost:8080/rest/api/';

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Get all book's categories as reference data from Backend server.
   */
  loadCategories(): Observable<Category[]> {
    const headers = new HttpHeaders();
    headers.append('content-type', 'application/json');
    headers.append('accept', 'application/json');
    return this.httpClient.get<Category[]>(`${this.baseUrl}/category/all`, {headers});
  }

  /**
   * Get all book's object in the Backend server data base.
   */
  getAllBooks(): Observable<Book[]> {
    const headers = new HttpHeaders();
    headers.append('content-type', 'application/json');
    headers.append('accept', 'application/json');
    return this.httpClient.get<Book[]>(`${this.baseUrl}/book/all`, {headers});
  }

  /**
   * Save a new Book object in the Backend server data base.
   */
  saveBook(book: Book): Observable<Book> {
    return this.httpClient.post<Book>(`${this.baseUrl}/book/create`, book);
  }

  /**
   * Update an existing Book object in the Backend server data base.
   */
  updateBook(book: Book): Observable<Book> {
    return this.httpClient.put<Book>(`${this.baseUrl}/book/update`, book);
  }

  /**
   * Delete an existing Book object in the Backend server data base.
   */
  deleteBook(book: Book): Observable<string> {
    return this.httpClient.delete<string>(`${this.baseUrl}/book/delete/${book.id}`);
  }

  /**
   * Search books by isbn
   */
  searchBookByIsbn(isbn: string): Observable<Book> {
    return this.httpClient.get<Book>(`${this.baseUrl}/book/searchByIsbn/${isbn}`);
  }

  /**
   * Search books by title
   */
  searchBookByTitle(title: string): Observable<Book[]> {
    return this.httpClient.get<Book[]>(`${this.baseUrl}/book/searchByTitle/${title}`);
  }

  /**
   * Search books by category
   */
  searchBookByCategory(category: string): Observable<Book[]> {
    return this.httpClient.get<Book[]>(`${this.baseUrl}/book/searchByCategory/${category}`);
  }

}
