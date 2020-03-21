package co.edu.icesi.fi.tics.tssc.services;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;

public interface GameService {

	public TsscGame saveGameWithTopic(TsscGame nuevo, long id) throws Exception;
	public TsscGame saveGame(TsscGame nuevo) throws Exception;
	public TsscGame editGame(TsscGame editado) throws Exception;
}
