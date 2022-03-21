package kino.service;


import kino.entity.Category;
import kino.entity.Film;
import kino.repository.CategoryRepository;
import kino.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final CategoryRepository categoryRepository;

    @Value("${kino.upload.file}")
    private String imagePath;


    public Film addFilm(Film film, MultipartFile uploadFiles, List<Integer> categories) throws IOException {
        List<Category> categoriesFromDB = getCategoriesFromRequest(categories);
        film.setCategories(categoriesFromDB);
        if (!uploadFiles.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + uploadFiles.getOriginalFilename();
            File newFile = new File(imagePath + fileName);
            uploadFiles.transferTo(newFile);
            film.setPic_url(fileName);
        }
        filmRepository.save(film);
        return film;
    }


    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Film findById(int id) {
        return filmRepository.getById(id);
    }


    private void saveItemImages(MultipartFile uploadFile, Film film) throws IOException {

    }


    private List<Category> getCategoriesFromRequest(List<Integer> categoriesIds) {
        List<Category> categories = new ArrayList<>();
        for (Integer category : categoriesIds) {
            categories.add(categoryRepository.getById(category));
        }
        return categories;
    }


}
