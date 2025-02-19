package com.example.event.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.event.category.Category;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ItemResource {
    @Autowired
    private ItemService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer eventId) {
        Item event = eventService.getItemById(eventId);

        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping()
    public List<Item> getAllItems() {
        return eventService.getAllItems();
    }

    @GetMapping("/by-organizer")
    public List<Item> getItemsByOrganizerId(@RequestParam Integer organizerId) {
        return eventService.getItemsByOrganizerId(organizerId);
    }
    @GetMapping("/status")
    public ResponseEntity<List<Item>> getItemsByStatus(@RequestParam ItemStatus status) {
        List<Item> events = eventService.getItemsByStatus(status);
        return ResponseEntity.ok(events);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Item>> getItemsByStatusAndOrganizerIdAndName(
        @RequestParam(required = false) ItemStatus eventStatus,
        @RequestParam(required = false) Integer organizerId,
        @RequestParam(required = false) String eventName
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getItemsByStatusAndOrganizerIdAndName(eventStatus,organizerId,eventName));
    }
    @GetMapping("/by-organizer-excluding")
    public ResponseEntity<List<Item>> getItemsByOrganizerIdExcludingItemId(@RequestParam Integer organizerId, @RequestParam Integer eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getItemsByOrganizerIdExcludingItemId(organizerId, eventId));
    }
    @PostMapping("/user/{userId}")
    public ResponseEntity<Void> addItem(@Valid @RequestBody ItemRequestDTO eventRequestDTO,
    @PathVariable Integer userId) {
        
        eventService.addItem(eventRequestDTO,userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //Cập nhật có 2 loại PUT & PATCH
    @PatchMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> updateItemById(@PathVariable Integer id,@PathVariable Integer userId,@Valid @RequestBody ItemRequestDTO eventRequestDTO) {
        eventService.updateItemById(id,userId,eventRequestDTO);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Integer id,@PathVariable Integer userId) {
        eventService.deleteItemById(id,userId);
        return ResponseEntity.noContent().build();
    }
    

}
