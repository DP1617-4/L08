package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Finder;
import domain.Property;
import domain.Tenant;

import repositories.FinderRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class FinderService {
	
	//managed repository-------------------
		@Autowired
		private FinderRepository finderRepository;
		
		//supporting services-------------------
		@Autowired
		private TenantService tenantService;
		
		@Autowired
		private PropertyService propertyService;
		
		@Autowired
		private ValueService valueService;
		
		@Autowired
		private AdministratorService administratorService;
		
		//Basic CRUD methods-------------------
		
		public Finder create(){
			
			Finder created;
			created = new Finder();
			Tenant principal = tenantService.findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(principal.getId() != 0);
			
			return created;
		}
		
		public Finder findOne(int finderId){
			
			Assert.isTrue(finderId != 0);
			Finder retrieved;
			retrieved = finderRepository.findOne(finderId);
			Assert.isTrue(checkPrincipal(retrieved));
			return retrieved;
		}

		public Finder save(Finder finder){
			
			Finder saved;
			Assert.notNull(finder);
			Assert.isTrue(checkPrincipal(finder));
			Tenant principal = tenantService.findByPrincipal();
			
			Calendar cal = Calendar.getInstance();
			Date now;
			now = new Date(System.currentTimeMillis() - 3600 * 1000);
			cal.setTime(now);
			cal.add(Calendar.HOUR, -1);
			Date dateOneHourBack = cal.getTime();
			if(principal.equals(finder.getTenant()) && finder.getLastUpdate().getTime() - dateOneHourBack.getTime() <= 3600000){
				saved = this.findByTenant(principal);
				saved.setLastUpdate(new Date(System.currentTimeMillis() - 1));
				return saved;
			}
			else{
				
				//asignamos el tenant al finder si se acaba de crear
					if(finder.getId() == 0){
						finder.setTenant(principal);	
					}
				//actualizamos la fecha de la �ltima b�squeda
				Date lastUpdate = new Date(System.currentTimeMillis() - 1);
				finder.setLastUpdate(lastUpdate);
				//inicializamos la colecci�n filtrada de properties
				Collection<Property> filtered;
				filtered = new ArrayList<Property>();
				//empezamos a a�adir properties que cumplan con los requisitos
				//primero la ciudad de destino
				String attribute = "City";
				filtered.addAll(valueService.findAllPropertiesByValueContent(finder.getDestinationCity(), attribute));
				//ahora el rate
				Double min = finder.getMinimumPrice();
				Double max = finder.getMaximumPrice();
				if(finder.getMaximumPrice()!= null && finder.getMinimumPrice() != null){
					filtered.addAll(propertyService.findAllByMinMaxRate(min, max));
				}
				if(finder.getMaximumPrice()!= null && finder.getMinimumPrice() == null){
					filtered.addAll(propertyService.findAllByMinRate(min));
				}
				if(finder.getMaximumPrice()== null && finder.getMinimumPrice() != null){
					filtered.addAll(propertyService.findAllByMaxRate(max));
				}
				//por ultimo la KeyWord
				if(finder.getKeyWord() != null){
					String keyWord = finder.getKeyWord();
					filtered.addAll(propertyService.findAllByContainsKeyWordAddress(keyWord));
					filtered.addAll(propertyService.findAllByContainsKeyWordName(keyWord));
				}
				//y ya cambiamos las properties a las filtradas
				finder.setCache(filtered);
				
				//guardamos
				saved = finderRepository.save(finder);
			return saved;
			}
			
			
		
			
		}
		
		public void delete(Finder finder){
			
			Assert.notNull(finder);
			Assert.isTrue(checkPrincipal(finder));
			Assert.isTrue(finder.getId() != 0);
			Assert.isTrue(finderRepository.exists(finder.getId()));
			finderRepository.delete(finder);
			
		}
		
		
		
		
		//Auxiliary methods

		public Boolean checkPrincipal(Finder e){
			
			Boolean result = false;
			UserAccount tenantUser = e.getTenant().getUserAccount();
			UserAccount principal = LoginService.getPrincipal();
			if(tenantUser.equals(principal)){
				result = true;
			}
			return result;
			
		}
		//Our other bussiness methods

		public Collection<Finder> findAll() {
			
			return finderRepository.findAll();
		}
		
		public Finder findByTenant(Tenant t){
			
			return	finderRepository.findByTenantId(t.getId());
		}

		public Double[] findAvgMinAndMaxPerFinder(){
			Assert.notNull(administratorService.findByPrincipal());
			Double[] result = finderRepository.findAvgMinAndMaxPerFinder();
			return result;
		}
		

}