package com.verifone.simcard.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecordDto {

    @JsonProperty("Sim_Card_Number")
    private String simCardNumber;

    @JsonProperty("Mobile_Number")
    private String mobileNumber;

    @JsonProperty("State_Of_Registration")
    private String stateOfRegistration;

    @JsonProperty("KYC")
    private boolean kyc;

    @JsonProperty("Telecom_Provider")
    private String telecomProvider;

    @JsonProperty("Full_Name")
    private String fullName;

}
