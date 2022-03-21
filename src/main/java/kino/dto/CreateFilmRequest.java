package kino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFilmRequest {

    private int id;
    private String title;
    private String description;
    private int duration;
    private List<Integer> categories;


}
