package co.edu.icesi.fi.tics.tssc.services;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.ITopicRepository;

public class TopicServiceImpl implements TopicService{

	public ITopicRepository repository;
	
	@Autowired
	public TopicServiceImpl(ITopicRepository repository) {
		this.repository = repository;
		
	}
	
	
	@Override
	public TsscTopic saveTopic(TsscTopic nuevo) {

		if(nuevo == null) {
			throw new RuntimeException();
		}else if (nuevo.getDefaultGroups() <= 0 || nuevo.getDefaultSprints() <= 0) {
			
			throw new RuntimeException();
			
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
