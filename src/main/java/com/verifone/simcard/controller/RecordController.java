package com.verifone.simcard.controller;

import com.verifone.simcard.dto.request.CreateRecordDto;
import com.verifone.simcard.dto.request.RecordDto;
import com.verifone.simcard.dto.response.RecordListResponseDto;
import com.verifone.simcard.dto.response.RecordResponseDto;
import com.verifone.simcard.service.RecordService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RecordController {

  @Autowired
  RecordService recordService;

  @GetMapping
  public ResponseEntity<?> healthCheck() {
    HashMap<String, String> map = new HashMap<>();
    map.put("status", "OK");
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @GetMapping("{recordId}")
  public RecordResponseDto getRecordByID(@PathVariable("recordId") String recordId) {
    return recordService.getRecordById(Integer.valueOf(recordId));
  }

  @GetMapping("listall")
  public RecordListResponseDto getRecords() {
    return recordService.getAllRecords();
  }

  @GetMapping("to-renew")
  public RecordListResponseDto getRecordsToRenew() {
    return recordService.getRecordsToRenew();
  }

  @PostMapping("add")
  public RecordResponseDto createRecord(@RequestBody CreateRecordDto createRecordDto) {
    return recordService.createRecord(createRecordDto);
  }

  @PutMapping("{recordId}")
  public RecordResponseDto updateRecord(@PathVariable("recordId") String recordId, @RequestBody
  RecordDto recordDto) {
    return recordService.updateRecord(Integer.valueOf(recordId), recordDto);
  }

  @DeleteMapping("{recordId}")
  public RecordResponseDto deleteRecord(@PathVariable("recordId") String recordId) {
    return recordService.deleteRecord(Integer.valueOf(recordId));
  }

}
