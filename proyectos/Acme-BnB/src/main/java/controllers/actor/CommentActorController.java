
package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import controllers.AbstractController;
import domain.Comment;
import domain.Commentable;

@Controller
@RequestMapping("/comment/actor")
public class CommentActorController extends AbstractController {

	// Services -----------------------------------------------------------------------
	@Autowired
	private CommentService		commentService;

	@Autowired
	private CommentableService	commentableService;


	// Constructor --------------------------------------------------------------------
	public CommentActorController() {
		super();
	}

	// Listing ------------------------------------------------------------------------

	// Creation -----------------------------------------------------------------------

	// Edition ------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int commentableId) {
		ModelAndView result;
		Commentable commentable = commentableService.findOne(commentableId);
		try {
			Comment comment = commentService.create(commentable);
			result = createEditModelAndView(comment);
			result.addObject("comment", comment);
			result.addObject("commentableId", commentable.getId());
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/comment/list.do?commentableId=" + commentable.getId());
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Comment comment, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(comment);
		} else {
			try {
				comment = commentService.save(comment);
				result = new ModelAndView("redirect:/commentable/display.do?commentableId=" + comment.getCommentable().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(comment, "comment.commit.error");
			}
		}
		return result;
	}

	// Ancillary methods --------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Comment comment) {
		ModelAndView result;
		result = createEditModelAndView(comment, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Comment comment, String message) {
		ModelAndView result;
		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("errorMessage", message);
		return result;
	}
}