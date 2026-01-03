package com.web.web.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.web.dto.request.StudySetItemRequest;
import com.web.web.dto.response.StudySetItemResponse;
import com.web.web.dto.response.StudySetResponse;
import com.web.web.entity.StudySet;
import com.web.web.entity.StudySetItem;
import com.web.web.mapper.StudySetItemMapper;
import com.web.web.repository.StudySetItemRepository;
import com.web.web.repository.StudySetRepository;

@Service
public class StudySetItemService {
    @Autowired
    StudySetService setService;
    @Autowired
    StudySetRepository setRepo;
    @Autowired
    FolderService folderService;
    @Autowired
    FileService fileService;
    @Autowired
    StudySetItemRepository itemRepo;
    @Autowired
    StudySetItemMapper mapper;

    public void addItems(StudySetItemRequest itemRequest, Integer studySetId) throws Exception {
        StudySet set = setRepo.findById(studySetId).get();
        folderService.checkIfYouCanAccessFolder(set.getFolder().getId());

        String imageSource = null;
        if (itemRequest.getImage() != null && !itemRequest.getImage().isEmpty()) {
            imageSource = fileService.saveFile(itemRequest.getImage(), "studySetItem/");
        }

        StudySetItem item = new StudySetItem(null, itemRequest.getConcept(), itemRequest.getDefine(),
                imageSource, set);
        itemRepo.save(item);
    }

    public void deleteItems(Integer id) throws Exception {
        StudySetItem item = itemRepo.findById(id).get();
        folderService.checkIfYouCanAccessFolder(item.getStudySet().getFolder().getId());
        
        // Chỉ xóa file nếu có imageSource
        if (item.getImageSource() != null && !item.getImageSource().isEmpty()) {
            try {
                Files.delete(fileService.getFullPathFromLink(item.getImageSource()));
            } catch (Exception e) {
                // Ignore if file doesn't exist
            }
        }
        
        itemRepo.deleteById(id);
    }

    public void update(StudySetItemRequest itemRequest, Integer id) throws Exception {
        StudySetItem item = itemRepo.findById(id).get();
        folderService.checkIfYouCanAccessFolder(item.getStudySet().getFolder().getId());
        
        // Cập nhật concept và define
        item.setConcept(itemRequest.getConcept());
        item.setDefine(itemRequest.getDefine());
        
        // Chỉ xử lý image nếu có upload mới
        if (itemRequest.getImage() != null && !itemRequest.getImage().isEmpty()) {
            // Xóa image cũ nếu có
            if (item.getImageSource() != null && !item.getImageSource().isEmpty()) {
                try {
                    Files.delete(fileService.getFullPathFromLink(item.getImageSource()));
                } catch (Exception e) {
                    // Ignore if file doesn't exist
                }
            }
            // Lưu image mới
            item.setImageSource(fileService.saveFile(itemRequest.getImage(), "studySetItem/"));
        }
        
        itemRepo.save(item);
    }

    public Page<StudySetItemResponse> findByStudySet(Integer studySetId, Pageable pageable) {
        StudySet set = setRepo.findById(studySetId).get();
        folderService.checkIfYouCanAccessFolder(set.getFolder().getId());
        return itemRepo.findByStudySet(set, pageable).map(mapper::toDTO);
    }

    public StudySetItemResponse findById(Integer id) {
        return mapper.toDTO(itemRepo.findById(id).get());
    }
}
