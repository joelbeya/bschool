import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './dashboard.component';

const routes: Routes = [
  {path: '', component: DashboardComponent},
  {
    path: 'book',
    loadChildren: () => import('./book/book.module').then(module => module.BookModule)
  },
  {
    path: 'customer',
    loadChildren: () => import('./customer/customer.module').then(module => module.CustomerModule)
  },
  {
    path: 'loan',
    loadChildren: () => import('./loan/loan.module').then(module => module.LoanModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {
}
