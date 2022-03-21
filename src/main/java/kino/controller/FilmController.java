package kino.controller;

import kino.dto.CreateFilmRequest;
import kino.entity.Category;
import kino.entity.Film;
import kino.repository.CategoryRepository;
import kino.repository.FilmRepository;
import kino.repository.UserRepository;
import kino.service.CategoryService;
import kino.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FilmService filmService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper mapper;

    @Value("${kino.upload.file}")
    private String imagePath;

    @GetMapping("/addFilm")
    public String addFilmPage(ModelMap map) {
        map.addAttribute("categories", categoryService.findAll());
        map.addAttribute("films",filmService.findAll());
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String addFilm(@ModelAttribute CreateFilmRequest createFilmRequest,
                          @RequestParam("picture") MultipartFile uploadedFile) throws IOException {
        List<Category> categories = new ArrayList<>();
        for (Integer category : createFilmRequest.getCategories()) {
            categories.add(categoryRepository.getById(category));
        }
        Film film = Film.builder()
                .id(createFilmRequest.getId())
                .title(createFilmRequest.getTitle())
                .description(createFilmRequest.getDescription())
                .duration(createFilmRequest.getDuration())
                .categories(categories)
                .build();
        if (!uploadedFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
            File newFile = new File(imagePath + fileName);
            uploadedFile.transferTo(newFile);
            film.setPic_url(fileName);
        }
        filmRepository.save(film);
        return "redirect:/";
    }


}
