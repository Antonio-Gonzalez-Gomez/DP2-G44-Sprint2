package acme.features.administrator.spamFilter;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.filters.Filter;
import acme.entities.filters.Word;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSpamFilterRepository extends AbstractRepository{

	@Query("SELECT w from Word w ORDER BY w.id")
	List<Word> findSpamWords();
	
	@Query("SELECT DISTINCT f from Filter f")
	List<Filter> findFilters();

	@Query("select f from Filter f where f.id = ?1")
	Filter findOneFilterById(int id);
}
