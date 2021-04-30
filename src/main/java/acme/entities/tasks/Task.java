package acme.entities.tasks;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.entities.roles.Manager;
import acme.framework.entities.DomainEntity;
import acme.framework.entities.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task extends DomainEntity {

    protected static final long    serialVersionUID    = 1L;
    
    @NotEmpty
    protected UserAccount useraccount;
    @NotEmpty
    @Size(max = 80, message = "Max 80 characters")
    protected String title;
    
    @NotBlank
    @Size(max = 500, message = "Max 500 characters")
    protected String description;
    
    @URL
    protected String link;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    protected Date startDate;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    protected Date endingDate;
    
    @NotNull
    protected Double workload;
    
    @NotNull
    protected Boolean privacy;
    
    @NotNull
    protected Boolean finished;
    
    protected Double executionPeriod;
    
 // Derived attributes -----------------------------------------------------


 	public void setExecutionPeriod() {
		this.executionPeriod = (double) ((this.endingDate.getYear()*525600-525600 + this.endingDate.getMonth()*43200 + 
			this.endingDate.getDate()*1440-1440 + this.endingDate.getHours()*60 + this.endingDate.getMinutes() + 
			this.endingDate.getSeconds()/60) - (this.startDate.getYear()*525600-525600 + 
			this.startDate.getMonth()*43200 + this.startDate.getDate()*1440-1440 + 
			this.startDate.getHours()*60 + this.startDate.getMinutes() + this.startDate.getSeconds()/60));
 	}
    
 // Relationships ----------------------------------------------------------


 	@NotNull
 	@Valid
 	@ManyToOne(optional = false)
 	protected Manager manager;
    
    
    
}