package note.app.domain;

import lombok.Data;

@Data
public class CreateUpdateNoteRequest {

  private String value;
  private boolean like;
}
