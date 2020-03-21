package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface StoryService {

	public TsscStory saveStory(TsscStory nuevo, long id) throws Exception;
	public TsscStory editStory(TsscStory editado) throws Exception;
}
