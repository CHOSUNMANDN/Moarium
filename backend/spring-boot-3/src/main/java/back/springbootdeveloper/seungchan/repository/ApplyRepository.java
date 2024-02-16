package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ApplyEntity;
import back.springbootdeveloper.seungchan.entity.ApplyEntity;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<ApplyEntity, Long> {
}
