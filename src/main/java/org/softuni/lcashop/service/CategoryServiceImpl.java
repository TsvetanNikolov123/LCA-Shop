package org.softuni.lcashop.service;

import org.modelmapper.ModelMapper;
import org.softuni.lcashop.domain.entities.Category;
import org.softuni.lcashop.domain.models.service.CategoryServiceModel;
import org.softuni.lcashop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);

        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll()
                .stream()
                .map(category ->
                        this.modelMapper.map(category, CategoryServiceModel.class)
                )
                .collect(Collectors.toList());
    }
}
