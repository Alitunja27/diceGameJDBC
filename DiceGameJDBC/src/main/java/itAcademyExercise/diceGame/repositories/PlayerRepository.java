package itAcademyExercise.diceGame.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import itAcademyExercise.diceGame.domain.Player;


@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
