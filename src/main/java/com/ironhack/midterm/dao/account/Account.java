package com.ironhack.midterm.dao.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.midterm.dao.transaction.Deposit;
import com.ironhack.midterm.dao.transaction.LocalTransaction;
import com.ironhack.midterm.dao.transaction.ThirdPartyTransaction;
import com.ironhack.midterm.dao.user.AccountHolder;
import com.ironhack.midterm.model.Money;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ironhack.midterm.util.MoneyUtil.newMoney;
import static com.ironhack.midterm.util.validation.DateTimeUtil.dateTimeNow;

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Valid
  @NotNull
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "amount", column = @Column(name = "balance_amount", nullable = false)),
      @AttributeOverride(name = "currency", column = @Column(name = "balance_currency", nullable = false))
  })
  private Money balance;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "primary_owner_id")
  @JsonIgnoreProperties(value = {"username", "password", "roles", "primaryAddress", "mailingAddress", "primaryAccounts", "secondaryAccounts", "requestList"}, allowSetters = true)
  private AccountHolder primaryOwner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "secondary_owner_id")
  @JsonIgnoreProperties(value = {"username", "password", "roles", "primaryAddress", "mailingAddress", "primaryAccounts", "secondaryAccounts", "requestList"}, allowSetters = true)
  private AccountHolder secondaryOwner;

  @Valid
  @NotNull
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "amount", column = @Column(name = "penaltyFee_amount", nullable = false)),
      @AttributeOverride(name = "currency", column = @Column(name = "penaltyFee_currency", nullable = false))
  })
  private Money penaltyFee;

  @NotNull
  @Column(name = "creation_date")
  private LocalDateTime creationDate;

  // ======================================== MAPPING ========================================
  @OneToMany(mappedBy = "targetAccount", cascade = {})
  @JsonIgnoreProperties(value = {}, allowSetters = true)
  @ToString.Exclude
  private List<Deposit> depositList = new ArrayList<>();

  @OneToMany(mappedBy = "account", cascade = {})
  @JsonIgnoreProperties(value = {}, allowSetters = true)
  @ToString.Exclude
  private List<LocalTransaction> transferSentList = new ArrayList<>();

  @OneToMany(mappedBy = "targetAccount", cascade = {})
  @JsonIgnoreProperties(value = {}, allowSetters = true)
  @ToString.Exclude
  private List<LocalTransaction> transferReceivedList = new ArrayList<>();

  @OneToMany(mappedBy = "targetAccount", cascade = {})
  @JsonIgnoreProperties(value = {}, allowSetters = true)
  @ToString.Exclude
  private List<ThirdPartyTransaction> thirdPartyTransactionList = new ArrayList<>();


  // ======================================== CONSTRUCTORS ========================================
  public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
    this.balance = balance;
    this.primaryOwner = primaryOwner;
    this.secondaryOwner = secondaryOwner;
    this.penaltyFee = newMoney("40");
    this.creationDate = dateTimeNow();
  }

  public Account(Money balance, AccountHolder primaryOwner) {
    this.balance = balance;
    this.primaryOwner = primaryOwner;
    this.penaltyFee = newMoney("40");
    this.creationDate = dateTimeNow();
  }


  // ======================================== OVERRIDE METHODS ========================================


}
