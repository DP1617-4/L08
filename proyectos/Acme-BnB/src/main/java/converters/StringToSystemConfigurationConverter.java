
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SystemConfigurationRepository;
import domain.SystemConfiguration;

@Component
@Transactional
public class StringToSystemConfigurationConverter implements Converter<String, SystemConfiguration> {

	@Autowired
	SystemConfigurationRepository	systemConfigurationRepository;


	@Override
	public SystemConfiguration convert(String text) {
		SystemConfiguration result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = systemConfigurationRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
