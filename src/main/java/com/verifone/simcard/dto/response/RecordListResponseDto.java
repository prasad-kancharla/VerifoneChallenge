package com.verifone.simcard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.verifone.simcard.model.Record;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordListResponseDto {

  private HttpStatus code;
  private String message;
  @JsonProperty("List of Records")
  private List<Record> recordsList;

}
