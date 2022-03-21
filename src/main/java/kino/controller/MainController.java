package kino.controller;

import kino.entity.Film;
import kino.repository.CategoryRepository;
import kino.repository.FilmRepository;
import kino.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${kino.upload.file}")
    private String imagePath;


    @GetMapping("/")
    public String main(ModelMap map) {
        map.addAttribute("films", filmRepository.findAll());
        return "index";
    }

    @GetMapping("/tv")
    public String tv(ModelMap map) {
        return "catalog";
    }

    @GetMapping("/movies/{id}")
    public String singleMovie(@PathVariable int id, ModelMap map) {
        Film film = filmRepository.findById(id).orElseThrow(RuntimeException::new);
        map.addAttribute("films", film);
        map.addAttribute("categories", categoryRepository.findAll());
        return "singleMovie";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);

    }
}
