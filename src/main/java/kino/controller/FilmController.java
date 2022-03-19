package kino.controller;

import kino.entity.Category;
import kino.entity.Film;
import kino.repository.CategoryRepository;
import kino.repository.FilmRepository;
import kino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Value("${kino.upload.file}")
    private String imagePath;

    @GetMapping("/addFilm")
    public String addFilmPage(ModelMap map) {
        List<Category> categories = categoryRepository.findAll();
        map.addAttribute("categories", categories);
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String addFilm(@ModelAttribute Film film,
                          @RequestParam("picture") MultipartFile uploadedFile) throws IOException {
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
