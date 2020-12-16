package com.bschool.category.controller;

import com.bschool.book.controller.BookRestController;
import com.bschool.category.Category;
import com.bschool.category.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/rest/api/category")
public class CategoryRestController {

    public static final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    private final CategoryRepository categoryRepository;

    public CategoryRestController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        logger.info("get all categories: {} ", categories.toString());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
