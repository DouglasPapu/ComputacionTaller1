package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface TopicService {

	public TsscTopic saveTopic(TsscTopic nuevo);
	public TsscTopic editTopic(TsscTopic editado);
}
