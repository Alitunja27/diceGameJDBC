package itAcademyExercise.diceGame.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import itAcademyExercise.diceGame.domain.Games;

import java.util.List;


@Repository
public interface GameRepository extends CrudRepository<Games,Integer> {

    public List<Games> findByPlayerId (Integer playerId);

    public List<Games> deleteByPlayerId (Integer playerId);
    
}
