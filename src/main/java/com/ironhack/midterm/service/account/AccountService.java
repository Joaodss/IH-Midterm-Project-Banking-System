package com.ironhack.midterm.service.account;

import com.ironhack.midterm.dao.account.Account;
import com.ironhack.midterm.dto.AccountEditDTO;
import com.ironhack.midterm.model.Money;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.InstanceNotFoundException;
import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.List;

public interface AccountService {

  // ======================================== GET Methods ========================================
  List<Account> getAll();

  Account getById(Long id) throws InstanceNotFoundException;

  Money getBalanceById(long id) throws InstanceNotFoundException;

  List<Account> getAllByUsername(String username);

  Account getByUsernameAndId(String username, long id) throws InstanceNotFoundException;

  Money getBalanceByUsernameAndId(String username, long id) throws InstanceNotFoundException;


  // ============================== Freeze Account ==============================
  void freezeAccount(long id) throws InstanceNotFoundException;

  void unFreezeAccount(long id) throws InstanceNotFoundException;


  // ============================== Save Account ==============================
  void save(Account account);

  void edit(long id, AccountEditDTO accountEdit) throws InstanceNotFoundException;

}
