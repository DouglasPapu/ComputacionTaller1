package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.exceptions.*;

public interface TopicService {

	public TsscTopic saveTopic(TsscTopic nuevo) throws CapacityException, TopicException, SpringException;
	public TsscTopic editTopic(TsscTopic editado) throws TopicException, CapacityException, SpringException;
}
