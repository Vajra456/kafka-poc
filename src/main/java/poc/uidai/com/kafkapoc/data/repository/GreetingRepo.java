package poc.uidai.com.kafkapoc.data.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poc.uidai.com.kafkapoc.data.entity.Greeting;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

@Repository
@Transactional
public interface GreetingRepo extends CrudRepository<Greeting, Long> {

    @Query("select g from Greeting g where g.id = :id")
    List<Greeting> findByGreetingId(Long id);

}
