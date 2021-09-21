package com.ironhack.midterm.dao.transaction;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.enums.Status;
import com.ironhack.midterm.enums.TransactionType;
import com.ironhack.midterm.model.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import static com.ironhack.midterm.util.MoneyUtil.negativeMoney;
import static com.ironhack.midterm.util.MoneyUtil.newMoney;

@Entity
@Table(name = "penalty_fee_transaction")
@PrimaryKeyJoinColumn(name = "id")
@NoArgsConstructor
@Getter
@Setter
public class PenaltyFeeTransaction extends Transaction {


  // ======================================== CONSTRUCTORS ========================================
  public PenaltyFeeTransaction(Money baseAmount, Account account, Account targetAccount) {
    super(baseAmount, account, targetAccount);
  }

  public PenaltyFeeTransaction(Money baseAmount, Account targetAccount) {
    super(baseAmount, targetAccount);
  }

  // ======================================== METHODS ========================================
  public TransactionReceipt acceptAndGenerateReceipt() {
    setStatus(Status.ACCEPTED);
    return new TransactionReceipt(
        getTargetAccount(),
        TransactionType.PENALTY_FEE,
        negativeMoney(getConvertedAmount()),
        getStatus(),
        "The penalty fee of " + getConvertedAmount().toString() + " was withdrawn from this account.",
        getOperationDate(),
        this
    );
  }

  public TransactionReceipt refuseAndGenerateReceipt() {
    setStatus(Status.REFUSED);
    return new TransactionReceipt(
        getTargetAccount(),
        TransactionType.PENALTY_FEE,
        newMoney("0", getConvertedAmount().getCurrency().getCurrencyCode()),
        getStatus(),
        "An error occurred! The penalty fee of " + getConvertedAmount().toString() + " was withdrawn from this account.",
        getOperationDate(),
        this
    );
  }

  public TransactionReceipt refuseAndGenerateReceipt(String message) {
    setStatus(Status.REFUSED);
    return new TransactionReceipt(
        getTargetAccount(),
        TransactionType.PENALTY_FEE,
        newMoney("0", getConvertedAmount().getCurrency().getCurrencyCode()),
        getStatus(),
        message,
        getOperationDate(),
        this
    );
  }


  // ======================================== OVERRIDE METHODS ========================================


}
