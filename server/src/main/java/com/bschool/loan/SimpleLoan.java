package com.bschool.loan;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SimpleLoan {

    private Integer bookId;

    private Integer customerId;

    private LocalDate beginDate;

    private LocalDate endDate;

}
