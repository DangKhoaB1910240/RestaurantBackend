package com.example.event.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.event.category.Category;
import com.example.event.category.CategoryRepository;
import com.example.event.exception.AlreadyExistsException;
import com.example.event.exception.InvalidValueException;
import com.example.event.exception.NotFoundException;
import com.example.event.logger.LoggerService;
import com.example.event.role.Role;
import com.example.event.user.User;
import com.example.event.user.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ItemService {
    @Autowired
    private ItemRepository eventRepository;
    @Autowired
    private CategoryRepository organizerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoggerService loggerService;
    public List<Item> getAllItems() {
        Specification<Item> spec = (root, query, cb) -> {
            query.orderBy(
                cb.asc(root.get("organizer").get("id")),
                cb.asc(root.get("startDateTime")),
                cb.asc(root.get("endDateTime"))
            );
            return cb.conjunction();
        };
        return eventRepository.findAll(spec);
    }
    public List<Item> getItemsByOrganizerId(Integer organizerId) {
        return eventRepository.findByCategoryId(organizerId);
    }
    public Item getItemById(Integer eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }
    public List<Item> getItemsByStatus(ItemStatus status) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        switch (status) {
            // case SAU:
            //     return eventRepository.findByStartDateTimeAfter(now);
            // case DANG:
            //     return eventRepository.findByStartDateTimeBeforeAndEndDateTimeAfter(now, now);
            // case TRUOC:
            //     return eventRepository.findByEndDateTimeBefore(now);
            default:
                return new ArrayList<>();
        }
    }
    public void deleteItemById(Integer id, Integer userId) {
        Item e = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại món với id "+id));
        if(userId!=null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Không tồn tại user"));
            String roleName = new String();
            boolean lastElement = false;

            // Lặp qua các role
            for (int i = 0; i < user.getRoles().size(); i++) {
                Role r = user.getRoles().get(i);
                roleName += r.getName();
                
                // Kiểm tra nếu phần tử hiện tại là phần tử cuối cùng
                if (i == user.getRoles().size() - 1) {
                    lastElement = true;
                }
                
                // Nếu không phải phần tử cuối cùng, thêm dấu phẩy
                if (!lastElement) {
                    roleName += " ,";
                }
            }
            loggerService.addLogger(user, "- Xóa món: "+e.getItemName(),roleName);
            eventRepository.deleteById(id);
        } 
        
    }
    public List<Item> getItemsByStatusAndOrganizerIdAndName(
         ItemStatus eventStatus,
         Integer organizerId,
         String eventName
    ) {
        LocalDateTime now = LocalDateTime.now();
        Specification<Item> spec = (root, query, cb) -> {
            query.orderBy(
                cb.asc(root.get("organizer").get("id")),
                cb.asc(root.get("startDateTime")),
                cb.asc(root.get("endDateTime"))
            );
            return cb.conjunction();
        };
        
        List<Item> events = eventRepository.findAll(spec);
        if(eventStatus !=null) {
            switch (eventStatus) {
                // case SAU:
                //     events.retainAll(eventRepository.findByStartDateTimeAfter(now));
                //     break;
                    
                // case DANG:
                //     events.retainAll(eventRepository.findByStartDateTimeBeforeAndEndDateTimeAfter(now, now));
                //     break;
                // case TRUOC:
                //     events.retainAll(eventRepository.findByEndDateTimeBefore(now));
                //     break;
                default:
                    break;
                    
            }
        }
        if (organizerId != null) {
            events.removeIf(e ->  !e.getCategory().getId().equals(organizerId));
        }
        if (eventName != null && !eventName.isEmpty()) {
            events.removeIf(e -> !e.getItemName().toLowerCase().contains(eventName.toLowerCase()));
        }
        return events;
    }
    public List<Item> getItemsByOrganizerIdExcludingItemId(Integer organizerId, Integer eventId) {
        return eventRepository.findTop5ByCategoryIdAndIdNot(organizerId, eventId);
    }
    @Transactional
    public void addItem( ItemRequestDTO eventRequestDTO,
    Integer userId) {
        Category o = organizerRepository.findById(eventRequestDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Không tồn tại danh mục với id: "+eventRequestDTO.getCategoryId()));
        Item event = new Item();
        event.setDescription(eventRequestDTO.getDescription());
        event.setItemName(eventRequestDTO.getItemName());
        event.setImg(eventRequestDTO.getImg());
        
        if(eventRequestDTO.getCost() <10000 || eventRequestDTO.getCost()%1000!=0) {
            throw new InvalidValueException("Giá món phải lớn hơn hoặc bằng 10.000đ và chia hết cho 1000");
        }
        event.setCost(eventRequestDTO.getCost());
        String content = "- Thêm món "+event.getItemName()+" thuộc danh mục "+o.getName();
        eventRepository.save(event);
        addLogger(userId,content);
    }
    private void addLogger(Integer userId,String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Không tồn tại user "));
        String roleName = new String();
            boolean lastElement = false;

            // Lặp qua các role
            for (int i = 0; i < user.getRoles().size(); i++) {
                Role r = user.getRoles().get(i);
                roleName += r.getName();
                
                // Kiểm tra nếu phần tử hiện tại là phần tử cuối cùng
                if (i == user.getRoles().size() - 1) {
                    lastElement = true;
                }
                
                // Nếu không phải phần tử cuối cùng, thêm dấu phẩy
                if (!lastElement) {
                    roleName += " ,";
                }
            }
        loggerService.addLogger(user,content,roleName);
    }
    public void updateItemById(Integer id, Integer userId,ItemRequestDTO eventRequestDTO) {
        Category o = organizerRepository.findById(eventRequestDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Không tồn tại danh mục với id: "+eventRequestDTO.getCategoryId()));
        Item event = new Item();
        event.setDescription(eventRequestDTO.getDescription());
        event.setItemName(eventRequestDTO.getItemName());
        event.setImg(eventRequestDTO.getImg());
        event.setStatus(eventRequestDTO.getStatus());
        if(eventRequestDTO.getCost() <10000 || eventRequestDTO.getCost()%1000!=0) {
            throw new InvalidValueException("Giá vé phải lớn hơn hoặc bằng 10.000đ và chia hết cho 1000");
        }
        event.setCost(eventRequestDTO.getCost());
        //Kiểm tra id có tồn tại không
        Item event2 = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại món với id: "+id));
        //Viết logger
        String content = "- Cập nhật món \""+event2.getItemName()+"\" thành \""+event.getItemName()+"\"";
        addLogger(userId, content);
        event2.setDescription(event.getDescription());
        event2.setItemName(event.getItemName());
        event2.setImg(event.getImg());
        event2.setStatus(event.getStatus());
        event2.setCost(event.getCost());
        eventRepository.save(event2);
        
    }
}
