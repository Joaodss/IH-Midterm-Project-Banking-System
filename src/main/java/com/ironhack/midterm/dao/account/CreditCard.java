package com.ironhack.midterm.dao.account;

import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.ironhack.midterm.util.MoneyUtil.*;

@Entity
@Table(name = "credit_card_account")
@PrimaryKeyJoinColumn(name = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditCard extends Account {

  @Valid
  @NotNull
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount", nullable = false)),
      @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency", nullable = false))
  })
  private Money creditLimit;

  @NotNull
  @Column(name = "interest_rate")
  private BigDecimal interestRate;

  @NotNull
  @Column(name = "last_interest_update")
  private LocalDate lastInterestUpdate;


  // ======================================== CONSTRUCTORS ========================================
  public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
    super(balance, primaryOwner, secondaryOwner);
    this.creditLimit = newMoney("100");
    this.interestRate = newBD("0.2");
    this.lastInterestUpdate = getCreationDate().toLocalDate().withDayOfMonth(1).plusMonths(1);
  }

  public CreditCard(Money balance, AccountHolder primaryOwner) {
    super(balance, primaryOwner);
    this.creditLimit = newMoney("100");
    this.interestRate = newBD("0.2");
    this.lastInterestUpdate = getCreationDate().toLocalDate().withDayOfMonth(1).plusMonths(1);
  }


  // ======================================== METHODS ========================================
  public void updateCurrencyValues() {
    setPenaltyFee(convertCurrency(getBalance(), getPenaltyFee()));
    setCreditLimit(convertCurrency(getBalance(), getCreditLimit()));
  }


  // ======================================== OVERRIDE METHODS ========================================


}
