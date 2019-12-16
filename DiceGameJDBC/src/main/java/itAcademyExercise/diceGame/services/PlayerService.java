package itAcademyExercise.diceGame.services;import itAcademyExercise.diceGame.domain.Games;import itAcademyExercise.diceGame.domain.Player;import itAcademyExercise.diceGame.exception.ErrorException;import itAcademyExercise.diceGame.repositories.GameRepository;import itAcademyExercise.diceGame.repositories.PlayerRepository;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.time.LocalDate;import java.util.*;@Servicepublic class PlayerService {    private String playerName;    @Autowired    PlayerRepository playerRepository;    @Autowired    GameService gameService;    @Autowired    GameRepository gameRepository;    public List<Player> getAllPlayers (){        List<Player> allPlayers = new ArrayList<Player>();        playerRepository.findAll().forEach(allPlayers::add);        for (int i=0;i<allPlayers.size();i++){            Integer playerId = allPlayers.get(i).getId();            allPlayers.get(i).setSuccessfulRate(successfulRateByPlayerId(playerId));        }        return allPlayers;    }    public Optional<Player> getPlayer(Integer id) {        return playerRepository.findById(id);    }        public void addNewPlayer(Player newPlayer) {        boolean repeated = false;    	if("".equals(newPlayer.getPlayerName())) {            newPlayer.setPlayerName("Anonymous");        }        List<Player> allPlayers = new ArrayList<Player>();        playerRepository.findAll().forEach(allPlayers::add);        for(int i=0;i<allPlayers.size();i++) {        	if(newPlayer.getPlayerName().equals(allPlayers.get(i).getPlayerName())) {        		repeated = true;        		throw new ErrorException("Name: " + newPlayer.getPlayerName() + " is already taken, try again");        	}        }        if(newPlayer.getPlayerName().equalsIgnoreCase("Anonymous") || repeated == false ) {        	Integer playerId = allPlayers.get(allPlayers.size() - 1).getId()+1;        	LocalDate playerRegisterDate = LocalDate.now();	        String strPlayerRegisterDate = playerRegisterDate.toString();	        Double successfulRate = Double.valueOf(0);	        newPlayer.setId(playerId);	        newPlayer.setSuccessfulRate(successfulRate);	        newPlayer.setPlayerRegisterDate(strPlayerRegisterDate);	        playerRepository.save(newPlayer);        }    }        @Transactional    public void updatePlayerNewName(Integer id, Player newName) {    	boolean repeated = false;    	if("".equals(newName.getPlayerName())) {    		newName.setPlayerName("Anonymous");        }        List<Player> allPlayers = new ArrayList<Player>();        playerRepository.findAll().forEach(allPlayers::add);        for(int i=0;i<allPlayers.size();i++) {        	if(newName.getPlayerName().equals(allPlayers.get(i).getPlayerName())) {        		repeated = true;        		throw new ErrorException("Name: " + newName.getPlayerName() + " is already taken, try again");        	}        }        if(newName.getPlayerName().equalsIgnoreCase("Anonymous") || repeated == false ) {	        playerRepository.findById(id).get().setPlayerName(newName.getPlayerName());        }    }    @Transactional    public void deletePlayer(Integer id) {        gameRepository.deleteByPlayerId(id);        playerRepository.deleteById(id);    }        @Transactional    public Double successfulRateByPlayerId (Integer playerId){    	Double percentageSuccessfulRateByPlayer = 0.000;    	List <Games> allPlays = gameService.getAllPlays(playerId);        Double playerSuccessfulRate = Double.valueOf(0);        for (int i=0;i<allPlays.size();i++){            if (allPlays.get(i).getGameResult().equalsIgnoreCase("win")){                playerSuccessfulRate++;            }        }        if (playerSuccessfulRate != 0) {        	percentageSuccessfulRateByPlayer = (playerSuccessfulRate*100)/allPlays.size();        }                return percentageSuccessfulRateByPlayer;    }        public List<String> getWorstPlayer (){        List<Player> allPlayers = new ArrayList<Player>();        List<String> worstPlayersList = new ArrayList<String>();        playerRepository.findAll().forEach(allPlayers::add);        for (int i=0;i<allPlayers.size();i++){            Integer playerId = allPlayers.get(i).getId();            allPlayers.get(i).setSuccessfulRate(successfulRateByPlayerId(playerId));        }        Comparator<Player> compareBySuccessfulRate = (Player p1, Player p2) -> p1.getSuccessfulRate().compareTo(p2.getSuccessfulRate());        Collections.sort(allPlayers, compareBySuccessfulRate);        worstPlayersList.add(allPlayers.get(0).getPlayerName());        for (int k=1; k<allPlayers.size();k++) {        	int comparator = allPlayers.get(0).getSuccessfulRate().compareTo(allPlayers.get(k).getSuccessfulRate());        	if (comparator == 0) {        		worstPlayersList.add(allPlayers.get(k).getPlayerName());        	}        		        }        return worstPlayersList;    }    public List<String> getBestPlayer (){        List<Player> allPlayers = new ArrayList<Player>();        List<String> bestPlayersList = new ArrayList<String>();        playerRepository.findAll().forEach(allPlayers::add);        for (int i=0;i<allPlayers.size();i++){            Integer playerId = allPlayers.get(i).getId();            allPlayers.get(i).setSuccessfulRate(successfulRateByPlayerId(playerId));        }        Comparator<Player> compareBySuccessfulRate = (Player p1, Player p2) -> p1.getSuccessfulRate().compareTo(p2.getSuccessfulRate());        Collections.sort(allPlayers, compareBySuccessfulRate.reversed());        bestPlayersList.add(allPlayers.get(0).getPlayerName());        for (int k=1; k<allPlayers.size();k++) {        	int comparator = allPlayers.get(0).getSuccessfulRate().compareTo(allPlayers.get(k).getSuccessfulRate());        	if (comparator == 0) {        		bestPlayersList.add(allPlayers.get(k).getPlayerName());        	}        		        }        return bestPlayersList;    }    @Override    public boolean equals(Object o) {        if (this == o) return true;        if (!(o instanceof PlayerService)) return false;        PlayerService that = (PlayerService) o;        return playerName.equals(that.playerName);    }    @Override    public int hashCode() {        return Objects.hash(playerName);    }}