package co.edu.icesi.fi.tics.tssc.repositories;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface ITopicRepository extends CrudRepository<TsscTopic, Long>{

	 public void saveTopic();
	 public TsscTopic editTopic();
	
}
