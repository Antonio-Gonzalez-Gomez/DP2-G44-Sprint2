package acme.entities.tasks;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

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
    
    
    protected String link;
    
    @NotNull
    @DateTimeFormat(pattern= "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    protected LocalDateTime startDate;
    
    @NotNull
    @DateTimeFormat(pattern= "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    protected LocalDateTime endingDate;
    
    @NotBlank
    protected Double workload;
    
    
    
}