package back.springbootdeveloper.seungchan.entity;

import back.springbootdeveloper.seungchan.constant.entity.TEST_ENUM;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "test_entity")
@DynamicInsert // insert할시 Null 배제
@DynamicUpdate // update할시 Null 배재
@Builder
@AllArgsConstructor
public class TestEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "test_entity_id")
  private Long test_entity_id;

  @Enumerated(EnumType.STRING)
  private TEST_ENUM testEnum;
}
