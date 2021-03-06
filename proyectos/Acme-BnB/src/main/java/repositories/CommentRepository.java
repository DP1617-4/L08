
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	// Devolvemos los comentarios de un Actor, incluidos los que se hizo a si mismo.
	@Query("select c from Comment c where c.commentable.id = ?1")
	Collection<Comment> allCommentsOfACommentable(int commentableId);

	// Devolvemos los comentarios de un Actor que se hizo a si mismo.
	@Query("select c from Comment c where c.commentable.id = ?1 and c.actor.id = ?1")
	Collection<Comment> allCommentsOfAnActorDidToHimself(int actorId);

	// Devolvemos los comentarios de un Actor, pero sin incluir los que se hizo a si mismo.
	@Query("select c from Comment c where not c.actor.id = ?1 and c.commentable.id = ?1")
	Collection<Comment> allCommentsExceptSelfComments(int actorId);

}
