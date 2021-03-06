
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Commentable;
import domain.Lessor;
import domain.Tenant;

@Service
@Transactional
public class CommentService {

	// managed repository ------------------------------------------------------
	@Autowired
	private CommentRepository	commentRepository;

	//Services 

	@Autowired
	private ActorService		actorService;

	@Autowired
	private LessorService		lessorService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------
	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Comment create(Commentable commentable) {
		Comment created;
		created = new Comment();
		Date moment = new Date(System.currentTimeMillis() - 100);
		Actor actor = actorService.findByPrincipal();
		created.setActor(actor);
		created.setMoment(moment);
		created.setCommentable(commentable);

		if (actor instanceof Tenant) {
			if (commentable instanceof Lessor) {
				Set<Lessor> commentables = lessorService.findAllCommentableLessors(actor.getId());
				Assert.isTrue(commentables.contains(commentable));
			}
			if (commentable instanceof Tenant) {
				Assert.isTrue(actor.equals(commentable));
			}
		}
		if (actor instanceof Lessor) {
			if (commentable instanceof Tenant) {
				Set<Lessor> commentables = lessorService.findAllCommentableLessors(commentable.getId());
				Assert.isTrue(commentables.contains(actor));
			}
			if (commentable instanceof Lessor) {
				Assert.isTrue(actor.equals(commentable));
			}
		}
		return created;
	}

	public Comment findOne(int commentId) {
		Comment retrieved;
		retrieved = commentRepository.findOne(commentId);
		return retrieved;
	}

	public Collection<Comment> findAll() {
		return commentRepository.findAll();
	}

	public Comment save(Comment comment) {

		Comment saved = commentRepository.save(comment);

		return saved;
	}

	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}

	public Collection<Comment> allCommentsOfAnActor(int commentableId) {

		Collection<Comment> result;
		result = commentRepository.allCommentsOfACommentable(commentableId);
		return result;
	}

	public Collection<Comment> allCommentsOfAnActorDidToHimself(int actorId) {

		Collection<Comment> result;
		result = commentRepository.allCommentsOfAnActorDidToHimself(actorId);
		return result;
	}

	public Collection<Comment> allCommentsExceptSelfComments(int actorId) {

		Collection<Comment> result;
		result = commentRepository.allCommentsExceptSelfComments(actorId);
		return result;
	}

	// Auxiliary methods ------------------------------------------------------

	// Our other bussiness methods --------------------------------------------
	public Comment reconstruct(Comment comment, BindingResult binding) {
		Comment result;
		if (comment.getId() == 0) {
			result = comment;
		} else {
			result = commentRepository.findOne(comment.getId());

			result.setMoment(comment.getMoment());
			result.setText(comment.getText());
			result.setTitle(comment.getTitle());
			result.setStars(comment.getStars());

			validator.validate(result, binding);
		}
		return result;
	}

}
