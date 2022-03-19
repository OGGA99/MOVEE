package kino.repository;


import kino.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FilmRepository extends JpaRepository<Film, Integer> {
        
        List<Film> findAllByTitle(String title);
        List<Film> findFilmsByTitle (String title);

}
