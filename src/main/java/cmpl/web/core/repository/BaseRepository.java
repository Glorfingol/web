package cmpl.web.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cmpl.web.core.model.BaseEntity;

/**
 * Interface commune de repository
 * 
 * @author Louis
 *
 * @param <T>
 */
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
