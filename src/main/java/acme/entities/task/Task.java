package acme.entities.task;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    
    
    protected Double workload;
    
    
    protected Boolean finished;
    
    
    protected Boolean privacy;
    
}