package com.verifone.simcard.service;

import com.verifone.simcard.dto.request.CreateRecordDto;
import com.verifone.simcard.dto.request.RecordDto;
import com.verifone.simcard.dto.response.RecordListResponseDto;
import com.verifone.simcard.dto.response.RecordResponseDto;
import com.verifone.simcard.exception.AppErrorCodes;
import com.verifone.simcard.exception.AppException;
import com.verifone.simcard.model.Record;
import com.verifone.simcard.repository.RecordRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService{

  @Autowired
  RecordRepository recordRepository;

  @Override
  public RecordResponseDto createRecord(CreateRecordDto createRecordDto) {
    var existingRecord = recordRepository.findByMobileNumber(createRecordDto.getMobileNumber());
    if (existingRecord.isEmpty()) {
      Record record = new Record(createRecordDto.getSimCardNumber(),createRecordDto.getMobileNumber(), createRecordDto.getStateOfRegistration(),createRecordDto.isKyc(), createRecordDto.getTelecomProvider(),createRecordDto.getFullName());
      recordRepository.save(record);
      return new RecordResponseDto(HttpStatus.OK, "Record created successfully", record);
    }
    throw new AppException(AppErrorCodes.DUPLICATE_RECORD);
  }

  @Override
  public RecordListResponseDto getAllRecords() {
    List<Record> recordList = recordRepository.findAll();
    return new RecordListResponseDto(HttpStatus.OK, "All records are fetched successfully", recordList);
  }

  @Override
  public RecordResponseDto getRecordById(Integer id) {
    var existingRecord = recordRepository.findById(id);
    if(existingRecord.isPresent()) {
      Record record = existingRecord.get();
      return new RecordResponseDto(HttpStatus.OK, "Record fetched successfully", record);
    }
    throw new AppException(AppErrorCodes.INVALID_RECORD_ID);
  }

  @Override
  public RecordResponseDto updateRecord(Integer id, RecordDto recordDto) {
    var existingRecord = recordRepository.findById(id);
    if(existingRecord.isPresent()) {
      var record = existingRecord.get();
      record = updateRecordWithDetails(record,recordDto);
      recordRepository.save(record);
      return new RecordResponseDto(HttpStatus.OK, "Record updated successfully", record);
    }
    throw new AppException(AppErrorCodes.INVALID_RECORD_ID);
  }

  @Override
  public RecordResponseDto deleteRecord(Integer id) {
    var existingRecord = recordRepository.findById(id);
    if(existingRecord.isPresent()) {
      Record record = existingRecord.get();
      recordRepository.delete(record);
      return new RecordResponseDto(HttpStatus.OK, "Record deleted successfully", record);
    }
    throw new AppException(AppErrorCodes.INVALID_RECORD_ID);
  }

  @Override
  public RecordListResponseDto getRecordsToRenew() {
    List<Record> records = recordRepository.findAll();
    List<Record> recordsToRenew = records.stream().filter(record -> ChronoUnit.DAYS.between(LocalDate.now().plusDays(30), record.getExpiryDate()) <= 30).collect(
        Collectors.toList());
    return new RecordListResponseDto(HttpStatus.OK, "Fetched all records that are expiring in next 30 days", recordsToRenew);
  }

  private Record updateRecordWithDetails(Record existingRecord, RecordDto recordDto) {
    existingRecord.setSimCardNumber(recordDto.getSimCardNumber());
    existingRecord.setMobileNumber(recordDto.getMobileNumber());
    existingRecord.setStatus(recordDto.isStatus());
    existingRecord.setExpiryDate(recordDto.getExpiryDate());
    existingRecord.setStateOfRegistration(recordDto.getStateOfRegistration());
    existingRecord.setKyc(recordDto.isKyc());
    existingRecord.setTelecomProvider(recordDto.getTelecomProvider());
    existingRecord.setFullName(recordDto.getFullName());

    return existingRecord;
  }

}
