import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoanRoutingModule} from './loan-routing.module';
import {LoanComponent} from './loan.component';
import {SharedModule} from '../../shared/shared.module';

@NgModule({
  declarations: [LoanComponent],
  imports: [
    CommonModule,
    SharedModule,
    LoanRoutingModule,
  ]
})
export class LoanModule {
}
