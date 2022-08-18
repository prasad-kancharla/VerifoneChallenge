package com.verifone.simcard.repository;

import com.verifone.simcard.model.Record;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaSpecificationExecutor<Record>, JpaRepository<Record,Integer> {
  Optional<Record> findByMobileNumber(String mobileNumber);
}
