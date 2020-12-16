package com.bschool.loan;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "loan")
@AssociationOverrides({
        @AssociationOverride(name = "pk.book", joinColumns = @JoinColumn(name = "book_id")),
        @AssociationOverride(name = "pk.customer", joinColumns = @JoinColumn(name = "customer_id"))
})
public class Loan implements Serializable {

    @Serial
    private static final long serialVersionUID = -126902011829663576L;

    @Delegate
    @EmbeddedId
    private LoanId pk = new LoanId();

    @Column(name = "begin_date", nullable = false)
    private LocalDate beginDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

}
