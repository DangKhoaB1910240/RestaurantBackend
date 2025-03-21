package com.example.event.table;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/table")
@CrossOrigin(origins = { "http://localhost:4200" })

@RequiredArgsConstructor
public class TableResource {

    private final TableService tableService;

    @PostMapping("/add")
    public ResponseEntity<?> addTable(@RequestBody TableDto request) {
        return ResponseEntity.ok(tableService.addTable(request));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateTable(@PathVariable Integer id, @RequestBody TableDto request) {
        this.tableService.updateTable(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Integer id) {
        tableService.deleteTable(id);
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Table>> getAllTables() {
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableResponse> getTableById(@PathVariable Integer id) {
        return ResponseEntity.ok(tableService.getTableById(id));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Table>> getAvailableTables() {
        return ResponseEntity.ok(tableService.getAvailableTables());
    }
}
