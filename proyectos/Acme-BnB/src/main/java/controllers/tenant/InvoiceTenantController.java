
package controllers.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InvoiceService;
import domain.Invoice;

@Controller
@RequestMapping("/invoice/tenant")
public class InvoiceTenantController {

	// Services ---------------------------------------------------------------

	@Autowired
	private InvoiceService	invoiceService;


	// Constructors -----------------------------------------------------------

	public InvoiceTenantController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") int invoiceId) {

		ModelAndView result;
		Invoice invoice;
		String requestURI = "invoice/tenant/display.do";
		invoice = invoiceService.findOne(invoiceId);

		result = new ModelAndView("invoice/display");
		result.addObject("invoice", invoice);
		result.addObject("requestURI", requestURI);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int requestId) {
		ModelAndView result;
		Invoice invoice;

		invoice = invoiceService.create(requestId);
		invoice = invoiceService.save(invoice);
		result = new ModelAndView("redirect:/invoice/tenant/display.do?invoiceId=" + invoice.getId());

		return result;
	}

}
