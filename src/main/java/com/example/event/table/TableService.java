package com.example.event.table;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.event.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public Table addTable(TableDto request) {
        Table table = new Table();
        table.setTableNumber(request.getTableNumber());
        table.setCapacity(request.getCapacity());
        table.setIsAvailable(true);
        table.setType(request.getType());
        table.setPrice(request.getPrice());

        Table savedTable = tableRepository.save(table);
        return savedTable;
    }

    public void updateTable(Integer id, TableDto request) {
        Table table = tableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tồn tại bàn với id " + id));

        table.setTableNumber(request.getTableNumber());
        table.setCapacity(request.getCapacity());
        table.setIsAvailable(request.getIsAvailable());
        table.setType(request.getType());
        table.setPrice(request.getPrice());

        tableRepository.save(table);
    }

    public void deleteTable(Integer id) {
        Optional<Table> tableOpt = tableRepository.findById(id);
        if (tableOpt.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.toString());
        }

        tableRepository.deleteById(id);
    }

    public List<Table> getAllTables() {
        List<Table> tables = tableRepository.findAll();
        return tables;
    }

    public TableResponse getTableById(Integer id) {
        Table table = tableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.toString()));
        return TableResponse.fromEntity(table);
    }

    public List<Table> getAvailableTables() {
        List<Table> tables = tableRepository.findByIsAvailableTrue();
        return tables;
    }
}
