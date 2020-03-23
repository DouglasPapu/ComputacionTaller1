package co.edu.icesi.fi.tics.tssc.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.ITopicRepository;

//@EnableJpaRepositories("co.edu.icesi.fi.tics.tssc.repositories")
@Service
public class TopicServiceImpl implements TopicService {

	public ITopicRepository repository;

	@Autowired
	public TopicServiceImpl(ITopicRepository repository) {
		this.repository = repository;

	}

	@Override
	public TsscTopic saveTopic(TsscTopic nuevo) throws CapacityException, TopicException, SpringException {

		if (nuevo == null) {
			throw new TopicException();
		} else if (nuevo.getDefaultGroups() <= 0) {

			throw new CapacityException();

		} else if (nuevo.getDefaultSprints() <= 0) {

			throw new SpringException();

		} else {
			return repository.save(nuevo);

		}

	}

	@Override
	public TsscTopic editTopic(TsscTopic editado) throws TopicException, CapacityException, SpringException {

		if (editado == null) {
			throw new TopicException();
			
		} else if (repository.findById(editado.getId()).isPresent() == false) {
			
			throw new TopicException();
			
		} else if (editado.getDefaultGroups() <= 0) {

			throw new CapacityException();

		} else if (editado.getDefaultSprints() <= 0) {

			throw new SpringException();

		} else {
			return repository.save(editado);

		}

	}

}
