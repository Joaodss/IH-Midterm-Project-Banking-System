package com.ironhack.midterm.controller;

import com.ironhack.midterm.dao.transaction.Transaction;
import com.ironhack.midterm.dao.transaction.TransactionReceipt;
import com.ironhack.midterm.dto.LocalTransactionDTO;

import java.util.List;

public interface TransactionController {

  // ======================================== GET TRANSACTION Methods ========================================
  // -------------------- Account Specific Transactions [ADMIN / Specific USER] --------------------
  List<Transaction> getTransactions(long id);

  // -------------------- User Specific Transactions by Id [ADMIN / Specific USER] --------------------
  Transaction getTransactionsById(long accountId, long transactionId);


  // ======================================== GET RECEIPT Methods ========================================
  // -------------------- User Specific Receipt by Account Id [ADMIN / Specific USER] --------------------
  TransactionReceipt getReceiptsByTransactionId(long accountId, long transactionId);


  // ======================================== POST TRANSACTION Methods ========================================
  // -------------------- Add Account Specific Local Transaction [ADMIN / Specific USER] --------------------
  void createLocalTransaction(long id, LocalTransactionDTO localTransaction);

  // -------------------- Add Account Specific Third Party Transaction [ADMIN / Specific USER] --------------------
//  void createThirdPartyTransaction(ThirdPartyTransactionDTO localTransaction);


}
