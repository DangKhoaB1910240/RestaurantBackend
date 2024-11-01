package com.example.event.ghe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.event.event.EventRequestDTO;
import com.example.event.exception.NotFoundException;

@Service
public class GheService {
    @Autowired
    private GheRepository gheRepository;

    public void update(Integer id, Integer gia) {
        Ghe ghe = this.gheRepository.findById(id).orElseThrow(() ->  new NotFoundException("Loại ghế không tồn tại"));
        if(gia<0 || gia%1000 !=0) {
            throw new NotFoundException("giá phải lớn hơn 0 và chia hết cho 1000");
        }
        ghe.setGiaGhe(gia);
        this.gheRepository.save(ghe);
    }

    public Ghe getById(Integer id) {
        Ghe ghe = this.gheRepository.findById(id).orElseThrow(() ->  new NotFoundException("Loại ghế không tồn tại"));
        return ghe;
    }
}
