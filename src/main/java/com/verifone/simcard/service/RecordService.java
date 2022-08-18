package com.verifone.simcard.service;

import com.verifone.simcard.dto.request.CreateRecordDto;
import com.verifone.simcard.dto.request.RecordDto;
import com.verifone.simcard.dto.response.RecordListResponseDto;
import com.verifone.simcard.dto.response.RecordResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface RecordService {
  RecordResponseDto createRecord(CreateRecordDto recordDto);
  RecordListResponseDto getAllRecords();

  RecordResponseDto getRecordById(Integer id);
  RecordResponseDto updateRecord(Integer id, RecordDto recordDto);
  RecordResponseDto deleteRecord(Integer id);

  RecordListResponseDto getRecordsToRenew();
}
