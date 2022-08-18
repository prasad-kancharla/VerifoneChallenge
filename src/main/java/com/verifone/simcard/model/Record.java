package com.verifone.simcard.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "Sim_Card_Number")
  private String simCardNumber;

  @Column(name = "Mobile_Number")
  private String mobileNumber;

  private boolean status;

  @Column(name = "Expiry_Date")
  private LocalDate expiryDate;

  @Column(name = "State_Of_Registration")
  private String stateOfRegistration;

  private boolean kyc;

  @Column(name = "Telecom_Provider")
  private String telecomProvider;

  @Column(name = "Full_Name")
  private String fullName;

  public Record(String simCardNumber, String mobileNumber, String stateOfRegistration, boolean kyc, String telecomProvider, String fullName) {
    this.simCardNumber = simCardNumber;
    this.mobileNumber = mobileNumber;
    this.status = true;
    this.expiryDate = LocalDate.now().plusDays(365);
    this.stateOfRegistration = stateOfRegistration;
    this.kyc = kyc;
    this.telecomProvider = telecomProvider;
    this.fullName = fullName;
  }
}
