
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Lessor;
import domain.Request;
import domain.Tenant;

@Service
@Transactional
public class RequestService {

	//managed repository-------------------
	@Autowired
	private RequestRepository	requestRepository;

	//supporting services-------------------
	@Autowired
	private TenantService		tenantService;
	
	@Autowired
	private AdministratorService administratorService;


	//Basic CRUD methods-------------------

	public Request create() {

		Request created;
		Tenant principal = tenantService.findByPrincipal();
		created = new Request();
		created.setTenant(principal);

		return created;
	}

	public Request findOne(int requestId) {

		Request retrieved;
		retrieved = requestRepository.findOne(requestId);
		return retrieved;
	}

	public Collection<Request> findAllByTenant(Tenant t) {

		return requestRepository.findAllByTenantId(t.getId());
	}

	public Collection<Request> findAllByLessor(Lessor l) {

		return requestRepository.findAllByLessorId(l.getId());
	}

	public Request save(Request request) {

		Request saved;
		saved = requestRepository.save(request);
		return saved;

	}

	public void delete(Request request) {

		requestRepository.delete(request);

	}

	public Collection<Request> findAll() {

		return requestRepository.findAll();
	}
	
	public Double[] findAverageAcceptedDeniedPerTenant(){
		Assert.notNull(administratorService.findByPrincipal());
		Double[][] unprocessedAverage = requestRepository.findAverageAcceptedDeniedPerTenant();
		Double[] result = {0.0,0.0};
		for(Double[] averageGroup:unprocessedAverage){
			result[0] += averageGroup[0];
			result[1] += averageGroup[1];
		}
		result[0] /= unprocessedAverage.length;
		result[1] /= unprocessedAverage.length;
		return result;
	}
	
	public Double[] findAverageAcceptedDeniedPerLessor(){
		Assert.notNull(administratorService.findByPrincipal());
		Double[][] unprocessedAverage = requestRepository.findAverageAcceptedDeniedPerLessor();
		Double[] result = {0.0,0.0};
		for(Double[] averageGroup:unprocessedAverage){
			result[0] += averageGroup[0];
			result[1] += averageGroup[1];
		}
		result[0] /= unprocessedAverage.length;
		result[1] /= unprocessedAverage.length;
		return result;
	}
	
	public Double[] findAvrageByPropertyWithOverWithoutInvoice(){
		Assert.notNull(administratorService.findByPrincipal());
		Double[] unprocessedAverage = requestRepository.findAverageByPropertyWithInvoice();
		Double[] result = {0.0,0.0};
		Double aux = 0.0;
		for(Double d:unprocessedAverage){
			aux += d;
		}
		result[0] = aux/unprocessedAverage.length;
		
		unprocessedAverage = requestRepository.findAverageByPropertyWithoutInvoice();
		aux = 0.0;
		for(Double d:unprocessedAverage){
			aux += d;
		}
		result[1] = aux/unprocessedAverage.length;
		return result;
		
	}

}
