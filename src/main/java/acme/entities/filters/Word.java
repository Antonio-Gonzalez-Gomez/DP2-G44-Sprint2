package acme.entities.filters;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Word extends DomainEntity{

	protected static final long	serialVersionUID	= 1L;
	
	@NotBlank
	protected String word;
	
	@NotNull
 	@ManyToOne(optional = false)
 	protected Filter filter;
}
