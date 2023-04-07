package note.app.domain;

import lombok.Data;

@Data
public class Note {

  private Long id;
  private String value;
  private Integer likeNumber;
}
