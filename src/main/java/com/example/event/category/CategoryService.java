package com.example.event.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.event.exception.AlreadyExistsException;
import com.example.event.exception.NotFoundException;
import com.example.event.item.ItemRepository;
import com.example.event.logger.Logger;
import com.example.event.logger.LoggerService;
import com.example.event.role.Role;
import com.example.event.user.User;
import com.example.event.user.UserRepository;

@Service
public class CategoryService {
    private CategoryRepository organizerRepository;
    private ItemRepository eventRepository;
    private LoggerService loggerService;
    private UserRepository userRepository;
    @Autowired
    public CategoryService(CategoryRepository organizerRepository, ItemRepository eventRepository,LoggerService loggerService,UserRepository userRepository) {
        this.organizerRepository = organizerRepository;
        this.eventRepository = eventRepository;
        this.loggerService = loggerService;
        this.userRepository = userRepository;
    }
    public List<Category> getAllOrganizers(String name) {
        List<Category> organizers = organizerRepository.findAll();
        organizers.removeIf((o) -> !o.getName().contains(name));
        return organizers;
    }
    public void deleteOrganizerById(Integer id, Integer userId) {
        
        Category o = organizerRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại danh mục với id "+id));
        if(eventRepository.existsByCategoryId(id)) {
            throw new AlreadyExistsException("Dữ liệu danh mục \""+o.getName()+"\" đang được sử dụng");
        }
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
            loggerService.addLogger(user, "- Xóa danh mục: "+o.getName(),roleName);
            organizerRepository.deleteById(id);
        } 
        
    }
    public void updateOrganizerById(Integer id, Integer userId,Category organizer) {
        //Kiểm tra id có tồn tại không
        Category organizer2 = organizerRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại danh mục với id: "+id));
        organizer2.setName(organizer.getName());
        organizerRepository.save(organizer2);
        //Viết logger
        String content = "- Cập nhật danh mục: \"Tên danh mục: "+organizer.getName()+"\"";
        addLogger(userId, content);
    }
    public void addOrganizer(Category organizer,Integer userId) {
        organizerRepository.save(organizer);
        addLogger(userId, "- Thêm danh mục: \"Tên danh mục: "+organizer.getName()+"\"");
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
    public Category getInfoById(Integer id) {
        return organizerRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại danh mục với id: "+id));
    }
}
