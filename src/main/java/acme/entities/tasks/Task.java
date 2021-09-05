package acme.entities.tasks;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.entities.roles.Manager;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes= {@Index(columnList="executionPeriod, workload, privacy, finished, manager_id")})
public class Task extends DomainEntity {

    protected static final long    serialVersionUID    = 1L;
    
    
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
    
    @Min(0)
    protected Double workload;
    
    @NotNull
    protected Double fakeWorkload;
    
    @NotNull
    protected Boolean privacy;
    
    @NotNull
    protected Boolean finished;
    
    protected Double executionPeriod;
    
 // Derived attributes -----------------------------------------------------


 	@SuppressWarnings("deprecation")
	public void setExecutionPeriod() {
		this.executionPeriod = (this.endingDate.getYear()*8760-8760 + this.endingDate.getMonth()*720 + 
			this.endingDate.getDate()*24-24 + this.endingDate.getHours() + this.endingDate.getMinutes()/60. + 
			this.endingDate.getSeconds()/3600.) - (this.startDate.getYear()*8760-8760 + 
			this.startDate.getMonth()*720 + this.startDate.getDate()*24-24 + 
			this.startDate.getHours() + this.startDate.getMinutes()/60. + this.startDate.getSeconds()/3600.);
 	}
    
 // Relationships ----------------------------------------------------------


 	@NotNull
 	@Valid
 	@ManyToOne(optional = false)
 	protected Manager manager;
    
    
    
}