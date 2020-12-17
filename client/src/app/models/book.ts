import {Category} from './category';

export class Book {
  id: number;
  title: string;
  isbn: string;
  totalExemplars: number;
  author: string;
  releaseDate: Date;
  category = new Category();
}
