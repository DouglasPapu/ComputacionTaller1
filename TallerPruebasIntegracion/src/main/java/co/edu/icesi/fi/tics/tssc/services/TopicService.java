package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface TopicService {

	public TsscTopic saveTopic(TsscTopic nuevo) throws Exception;
	public TsscTopic editTopic(TsscTopic editado);
}
