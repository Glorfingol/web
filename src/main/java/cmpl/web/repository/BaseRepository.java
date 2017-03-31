package cmpl.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpl.web.model.news.dao.BaseEntity;

public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
