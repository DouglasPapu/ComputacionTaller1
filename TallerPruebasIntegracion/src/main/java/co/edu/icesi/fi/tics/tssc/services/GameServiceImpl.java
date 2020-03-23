package co.edu.icesi.fi.tics.tssc.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.GameException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.IGameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.ITopicRepository;

@Service
public class GameServiceImpl implements GameService {

	private IGameRepository gameRepository;

	private ITopicRepository topicRepository;

	@Autowired
	public GameServiceImpl(IGameRepository gameRepository, ITopicRepository topicRepository) {
		// TODO Auto-generated constructor stub
		this.gameRepository = gameRepository;
		this.topicRepository = topicRepository;
	}

	@Override
	public TsscGame saveGameWithTopic(TsscGame nuevo, long id)
			throws CapacityException, TopicException, SpringException, GameException {

		if (nuevo == null) {
			throw new GameException();
			
		} else if (topicRepository.findById(id).isPresent() == false) {
			throw new TopicException();
		} else if (nuevo.getNGroups() <= 0) {
			throw new CapacityException();
		} else if (nuevo.getNSprints() <= 0) {
			throw new SpringException();
		} else {

			nuevo.setTsscTopic(topicRepository.findById(id).get());
			return gameRepository.save(nuevo);
		}

	}

	@Override
	public TsscGame saveGame(TsscGame nuevo) throws CapacityException, GameException, SpringException {
		if (nuevo == null) {
			throw new GameException();
		} else if (nuevo.getNGroups() <= 0) {
			throw new CapacityException();
		} else if (nuevo.getNSprints() <= 0) {
			throw new SpringException();
		} else {
			return gameRepository.save(nuevo);
		}
	}

	@Override
	public TsscGame editGame(TsscGame editado) throws GameException, CapacityException, SpringException {
		if (editado == null) {
			throw new GameException();
		} else if (gameRepository.findById(editado.getId()) == null) {
			throw new GameException();
		 } else if (editado.getNGroups() <= 0) {
			throw new CapacityException();
		} else if (editado.getNSprints() <= 0) {
			throw new SpringException();
		} else {
			return gameRepository.save(editado);
		}

	}

	// Punto d. Refactor

	@Override
	public TsscGame saveGameWithTopic2(TsscGame nuevo, long id)
			throws CapacityException, TopicException, SpringException, GameException {

		if (nuevo == null) {
			throw new GameException();
		} else if (topicRepository.findById(id) == null) {
			throw new TopicException();
		} else if (nuevo.getNGroups() <= 0) {
			throw new CapacityException();
		} else if (nuevo.getNSprints() <= 0) {
			throw new SpringException();
		} else {
			
			nuevo.setTsscTopic(topicRepository.findById(id).get());
			TsscTopic topicAssociated = nuevo.getTsscTopic();
			
			List<TsscStory> listStories = topicAssociated.getTsscStories();
			nuevo.setTsscStories(listStories);
			
			//Ahora el cronograma asociado.
			
			
			return gameRepository.save(nuevo);
		}

	}

}
