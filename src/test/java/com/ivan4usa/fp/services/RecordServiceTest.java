package com.ivan4usa.fp.services;

import com.ivan4usa.fp.entities.Record;
import com.ivan4usa.fp.enums.RecordType;
import com.ivan4usa.fp.repositories.RecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-local.properties")
@ContextConfiguration(classes = RecordService.class)
class RecordServiceTest {

    @Autowired
    private RecordService service;

    @MockBean
    private RecordRepository repository;

    @Test
    void findAll() {
        // Set up a mock repository
        Record record1 = new Record(1L, LocalDate.parse("2020-04-02"),
                new BigDecimal("1000.00"), "comment", RecordType.INCOME, 1L, null,
                null);

        Record record2 = new Record(2L, LocalDate.parse("2020-04-03"),
                new BigDecimal("1000.00"), "comment", RecordType.INCOME, 1L, null,
                null);

        Record record3 = new Record(3L, LocalDate.parse("2020-04-04"),
                new BigDecimal("1000.00"), "comment", RecordType.INCOME, 1L, null,
                null);
        doReturn(Arrays.asList(record1, record2, record3)).when(repository).findRecordsByUserIdOrderByRecordDateDesc(1L);
        // Execute the service call
        List<Record> records = service.findAll(1L);

        // Assert the response
        Assertions.assertEquals(3, records.size(), "findAll should return 3 accounts");
    }

    @Test
    void findById() {
        // Set up a mock repository
        Record record = new Record(8L, LocalDate.parse("2021-07-04"),
                new BigDecimal("2000.00"), "comment", RecordType.INCOME, 5L, null,
                null);
        doReturn(Optional.of(record)).when(repository).findById(8L);
        // Execute the service call
        Optional<Record> returnedRecord = service.findById(8L);
        // Assert the response
        Assertions.assertNotNull(Optional.of(returnedRecord).get(), "Record was not found");
        Assertions.assertSame(returnedRecord.get(), record, "The record returned was not the same as the mock");
    }

    @Test
    void testFindByIdNotFound() {
        // Set up a mock repository
        doReturn(Optional.empty()).when(repository).findById(1L);
        // Execute the service call
        Optional<Record> returnedRecord = service.findById(1L);
        // Assert the response
        Assertions.assertFalse(returnedRecord.isPresent(), "Record should not be found");
    }

    @Test
    void add() {
        // Set up a mock repository
        Record newRecord = new Record(1L, LocalDate.parse("2021-02-02"),
                new BigDecimal("100.00"), "comment", RecordType.INCOME, 5L, null,
                null);
        doReturn(newRecord).when(repository).save(any());
        // Execute the service call
        Record returnedRecord = service.add(newRecord);
        // Assert the response
        Assertions.assertNotNull(returnedRecord, "The saved record should not be null");
        Assertions.assertEquals(LocalDate.parse("2021-02-02"), returnedRecord.getRecordDate(), "The date should be different");
        Assertions.assertEquals(RecordType.INCOME, returnedRecord.getRecordType(), "The record type should be different");
        Assertions.assertEquals("comment", returnedRecord.getComment(), "The comment should be different");
        Assertions.assertEquals(new BigDecimal("100.00"), returnedRecord.getAmount(), "The amount should be different");
        Assertions.assertEquals(null, returnedRecord.getAccount(), "The account should be different");
        Assertions.assertEquals(null, returnedRecord.getCategory(), "The category should be different");
        Assertions.assertEquals(5L, returnedRecord.getUserId(), "The user id should be different");
    }

    @Test
    void update() {
        // Set up a mock repository
        Record updateRecord = new Record(7L, LocalDate.parse("2021-01-01"),
                new BigDecimal("100.00"), "comment", RecordType.TR_IN, 2L, null,
                null);
        doReturn(updateRecord).when(repository).save(any());
        // Execute the service call
        Record returnedRecord = service.update(updateRecord);
        // Assert the response
        Assertions.assertNotNull(returnedRecord, "The saved record should not be null");
        Assertions.assertEquals(LocalDate.parse("2021-01-01"), returnedRecord.getRecordDate(), "The name should be different");
        Assertions.assertEquals(RecordType.TR_IN, returnedRecord.getRecordType(), "The record type should be different");
        Assertions.assertEquals("comment", returnedRecord.getComment(), "The comment should be different");
        Assertions.assertEquals(new BigDecimal("100.00"), returnedRecord.getAmount(), "The amount should be different");
        Assertions.assertEquals(null, returnedRecord.getAccount(), "The account should be different");
        Assertions.assertEquals(null, returnedRecord.getCategory(), "The category should be different");
        Assertions.assertEquals(2L, returnedRecord.getUserId(), "The user id should be different");
    }

    @Test
    void delete() {
        // Set up a mock repository
        Record deleteRecord = new Record(4L, LocalDate.parse("2021-06-08"),
                new BigDecimal("100.00"), "comment", RecordType.TR_IN, 2L, null,
                null);
        doNothing().when(repository).deleteById(4L);
        // Execute the service call
        service.add(deleteRecord);
        service.delete(4L);
        Optional<Record> returnedRecord = service.findById(4L);
        // Assert the response
        Assertions.assertFalse(returnedRecord.isPresent(), "Record should not be found");
    }
}
