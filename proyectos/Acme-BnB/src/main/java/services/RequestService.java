
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.LoginService;
import security.UserAccount;
import domain.Lessor;
import domain.Property;
import domain.Request;
import domain.Tenant;

@Service
@Transactional
public class RequestService {

	//managed repository-------------------
	@Autowired
	private RequestRepository		requestRepository;

	//supporting services-------------------
	@Autowired
	private TenantService			tenantService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private LessorService			lessorService;

	@Autowired
	private Validator				validator;


	//Basic CRUD methods-------------------

	public Request create(Property property) {

		Request created;
		Tenant principal = tenantService.findByPrincipal();
		created = new Request();
		created.setTenant(principal);
		created.setStatus("PENDING");
		created.setProperty(property);

		return created;
	}

	public Request findOne(int requestId) {

		Request retrieved;
		retrieved = requestRepository.findOne(requestId);
		Assert.isTrue(checkPrincipal(retrieved) || retrieved.getProperty().getLessor().equals(lessorService.findByPrincipal()));
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
		Assert.notNull(tenantService.findByPrincipal());
		long diff = request.getCheckOutDate().getTime() - request.getCheckInDate().getTime();
		Assert.isTrue(request.getCheckInDate().before(request.getCheckOutDate()));
		//Checks if there is one day of difference between the check in and the checkout
		Assert.isTrue((diff * 1.0 / (3600 * 1000 * 24)) >= 1.0);
		saved = requestRepository.save(request);
		return saved;

	}

	public Request accept(Request request) {
		Lessor lessor = lessorService.findByPrincipal();
		Request result;
		result = request;
		result.setStatus("ACCEPTED");
		result = requestRepository.save(request);
		lessorService.addFee(lessor);
		return result;
	}

	public Request deny(Request request) {

		Request result;
		result = request;
		result.setStatus("DENIED");
		result = requestRepository.save(request);
		return result;
	}

	public void delete(Request request) {

		requestRepository.delete(request);

	}

	public Collection<Request> findAll() {

		return requestRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public Request reconstruct(Request request, BindingResult binding) {
		Request result;

		if (request.getId() == 0) {
			result = request;
		} else {
			result = requestRepository.findOne(request.getId());

			result.setCheckInDate(request.getCheckInDate());
			result.setCheckOutDate(request.getCheckOutDate());
			result.setCreditCard(request.getCreditCard());
			result.setInvoice(request.getInvoice());
			result.setProperty(request.getProperty());
			result.setSmoker(request.getSmoker());
			result.setStatus(request.getStatus());
			result.setTenant(request.getTenant());

			validator.validate(result, binding);
		}

		return result;
	}

	public Boolean checkPrincipal(Request e) {

		Boolean result = false;
		UserAccount tenantUser = e.getTenant().getUserAccount();
		UserAccount principal = LoginService.getPrincipal();
		if (tenantUser.equals(principal)) {
			result = true;
		}
		return result;

	}

	public Double[] findAverageAcceptedDeniedPerTenant() {
		Assert.notNull(administratorService.findByPrincipal());
		Double[] result = {
			0.0, 0.0
		};
		result[0] = requestRepository.findAverageAcceptedPerTenant();
		result[1] = requestRepository.findAverageDeniedPerTenant();
		return result;
	}

	public Double[] findAverageAcceptedDeniedPerLessor() {
		Assert.notNull(administratorService.findByPrincipal());
		Double[] result = {
			0.0, 0.0
		};
		result[0] = requestRepository.findAverageAcceptedPerLessor();
		result[1] = requestRepository.findAverageDeniedPerLessor();
		return result;
	}

	public Double[] findAvrageByPropertyWithOverWithoutInvoice() {
		Assert.notNull(administratorService.findByPrincipal());
		Double[] unprocessedAverage = requestRepository.findAverageByPropertyWithInvoice();
		Double[] result = {
			0.0, 0.0
		};
		Double aux = 0.0;
		for (Double d : unprocessedAverage) {
			aux += d;
		}
		result[0] = aux / unprocessedAverage.length;
		unprocessedAverage = requestRepository.findAverageByPropertyWithoutInvoice();
		aux = 0.0;
		for (Double d : unprocessedAverage) {
			aux += d;
		}
		result[1] = aux / unprocessedAverage.length;
		return result;

	}

}
