package acme.features.administrator.word;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.filters.Word;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorWordRepository extends AbstractRepository {
	
	@Query("SELECT DISTINCT w from Word w")
	List<Word> findWords();
	
	@Query("select w from Word w where w.id = ?1")
	Word findOneWordById(int id);

}
