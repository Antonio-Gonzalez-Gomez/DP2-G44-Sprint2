package acme.features.administrator.spamFilter;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.filters.Filter;
import acme.entities.filters.Word;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSpamFilterRepository extends AbstractRepository{

	@Query("SELECT w from Word w WHERE w.filter.id = :id ORDER BY w.id")
	List<Word> findSpamWords(@Param("id") int id);
	
	@Query("SELECT DISTINCT f from Filter f")
	List<Filter> findFilters();
}
