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

    public String addTable(TableDto request) {
        Table table = new Table();
        table.setTableNumber(request.getTableNumber());
        table.setCapacity(request.getCapacity());
        table.setIsAvailable(true);
        table.setType(request.getType());
        table.setPrice(request.getPrice());

        tableRepository.save(table);
        return "Table added successfully";
    }

    public String updateTable(Integer id, TableDto request) {
        Optional<Table> tableOpt = tableRepository.findById(id);
        if (tableOpt.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.toString());
        }

        Table table = tableOpt.get();
        table.setTableNumber(request.getTableNumber());
        table.setCapacity(request.getCapacity());
        table.setIsAvailable(request.getIsAvailable());
        table.setType(request.getType());
        table.setPrice(request.getPrice());

        tableRepository.save(table);
        return "Table updated successfully";
    }

    public String deleteTable(Integer id) {
        Optional<Table> tableOpt = tableRepository.findById(id);
        if (tableOpt.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.toString());
        }

        tableRepository.deleteById(id);
        return "Table deleted successfully";
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
