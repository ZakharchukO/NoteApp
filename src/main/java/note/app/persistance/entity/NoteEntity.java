package note.app.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "note")
public class NoteEntity extends AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_generator")
  @SequenceGenerator(name = "note_generator", sequenceName = "note_id_seq")
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(name = "value", nullable = false)
  private String value;

  @Column(name = "like_number", nullable = false)
  private Integer likeNumber;
}
