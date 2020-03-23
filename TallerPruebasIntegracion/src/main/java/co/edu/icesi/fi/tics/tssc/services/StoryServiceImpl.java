package co.edu.icesi.fi.tics.tssc.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.GameException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.StoryException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.repositories.IGameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.IStoryRepository;

@Service
public class StoryServiceImpl implements StoryService {

	private IGameRepository gameRepository;

	private IStoryRepository storyRepository;

	@Autowired
	public StoryServiceImpl(IGameRepository gameRepository, IStoryRepository storyRepository) {
		// TODO Auto-generated constructor stub
		this.gameRepository = gameRepository;
		this.storyRepository = storyRepository;
	}

	@Override
	public TsscStory saveStory(TsscStory nuevo, long id)
			throws StoryException, GameException, BusinessValueException, InitialSprintException, PriorityException {
		// TODO Auto-generated method stub
		if (nuevo == null) {
			throw new StoryException();
		} else if (gameRepository.findById(id).isPresent() == false) {
			throw new GameException();

		} else if (nuevo.getBusinessValue().compareTo(new BigDecimal(0)) == 0
				|| nuevo.getBusinessValue().compareTo(new BigDecimal(0)) == -1) {
			throw new BusinessValueException();

		} else if (nuevo.getInitialSprint().compareTo(new BigDecimal(0)) == 0
				|| nuevo.getInitialSprint().compareTo(new BigDecimal(0)) == -1) {
			throw new InitialSprintException();

		} else if (nuevo.getPriority().compareTo(new BigDecimal(0)) == 0
				|| nuevo.getPriority().compareTo(new BigDecimal(0)) == -1) {
			throw new PriorityException();

		} else {
			nuevo.setTsscGame(gameRepository.findById(id).get());
			return storyRepository.save(nuevo);
		}
	}

	@Override
	public TsscStory editStory(TsscStory editado) throws StoryException {
		if (editado == null) {
			throw new StoryException();
		} else if (storyRepository.findById(editado.getId()) == null) {
			throw new StoryException();
		} else {

			return storyRepository.save(editado);

		}
	}

}
