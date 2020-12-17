import {Book} from './book';
import {Customer} from './customer';

export class Loan {
  book: Book = new Book();
  customer: Customer = new Customer();
  loanBeginDate: Date;
  loanEndDate: Date;
}
