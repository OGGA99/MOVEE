package kino.service;


import kino.entity.Film;
import kino.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;


    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public Film findById(int id) {
        return filmRepository.getById(id);
    }

}
