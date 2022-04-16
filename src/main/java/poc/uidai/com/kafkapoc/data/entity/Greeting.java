package poc.uidai.com.kafkapoc.data.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Target;

@Getter
@Setter
@Entity
@Table(name = "greetings")
@Data
public class Greeting {
    @Id
    private long id;
    @Column(name = "message")
    private String message;
}

