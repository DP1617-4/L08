
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.tenant.id = ?1")
	Collection<Request> findAllByTenantId(int tenantId);

}
