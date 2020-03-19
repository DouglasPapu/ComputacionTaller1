package co.edu.icesi.fi.tics.tssc.services;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.ITopicRepository;

public class TopicServiceImpl implements TopicService{

	public ITopicRepository repository;
	
	@Autowired
	public TopicServiceImpl(ITopicRepository repository) {
		this.repository = repository;
		
	}
	
	
	@Override
	public TsscTopic saveTopic(TsscTopic nuevo) 
			 throws CapacityException, TopicException, SpringException {

		if(nuevo == null) {
			throw new TopicException();
		}else if (nuevo.getDefaultGroups() <= 0) {
			
			throw new CapacityException();
			
		}else if(nuevo.getDefaultSprints() <= 0) {
			
			throw new SpringException();
			
		}else {
			
			repository.save(nuevo);
		}
			return nuevo;     
	}

	@Override
	public TsscTopic editTopic(TsscTopic editado) {
		// TODO Auto-generated method stub
		return null;
	}

}
