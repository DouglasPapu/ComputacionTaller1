package co.edu.icesi.fi.tics.tssc.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.exceptions.GameException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.IGameRepository;
import co.edu.icesi.fi.tics.tssc.repositories.ITopicRepository;
import co.edu.icesi.fi.tics.tssc.services.GameServiceImpl;

@RunWith(MockitoJUnitRunner.class)
class GameTest {

	@Mock
	private ITopicRepository topicRepository;

	@Mock
	private IGameRepository gameRepository;

	@InjectMocks
	private GameServiceImpl gameService;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

	}

	// *********************************************
	// PRUEBAS PARA EL GUARDAR
	// *********************************************

	// Se encarga de verificar si lanza la excepcion al tener un game con Sprints
	// igual a cero.
	@DisplayName("Sprints Igual A Cero")
	@Test
	public void testSprintsExceptionIgualACero() {
		TsscGame game = new TsscGame();
		game.setNSprints(0);
		game.setNGroups(5);

		assertThrows(SpringException.class, () -> {
			gameService.saveGame(game);
		});
		
		Mockito.verifyNoInteractions(gameRepository);
	}

	// Se encarga de verificar si lanza la excepcion al tener un game con Sprints
	// menor a cero.
	@DisplayName("Sprints Menor A Cero")
	@Test
	public void testSprintsExceptionMenorACero() {
		TsscGame game = new TsscGame();
		game.setNSprints(-5);
		game.setNGroups(5);

		assertThrows(SpringException.class, () -> {
			gameService.saveGame(game);
		});
		
		Mockito.verifyNoInteractions(gameRepository);
	}

	// Se encarga de verificar si lanza la excepcion al tener un game con Groups
	// igual a cero.
	@DisplayName("Groups Igual A Cero")
	@Test
	public void testGroupsExceptionIgualACero() {
		TsscGame game = new TsscGame();
		game.setNGroups(0);
		game.setNSprints(5);

		assertThrows(CapacityException.class, () -> {
			gameService.saveGame(game);
		});
		
		Mockito.verifyNoInteractions(gameRepository);

	}

	// Se encarga de verificar si lanza la excepcion al tener un game con Groups
	// menor a cero.
	@DisplayName("Groups Menor A Cero")
	@Test
	public void testGroupsExceptionMenorACero() {
		TsscGame game = new TsscGame();
		game.setNGroups(-5);
		game.setNSprints(5);

		assertThrows(CapacityException.class, () -> {
			gameService.saveGame(game);
		});
		
		Mockito.verifyNoInteractions(gameRepository);

	}
	
	
	//Se encarga de testear que cuando se guarde un juego nulo, lace la excepción.
	@DisplayName("Guardar Juego nulo")
	@Test
	public void testSaveGameNull() {	
		assertThrows(GameException.class, ()->{	
			gameService.saveGame(null);
		});
		
		Mockito.verifyNoInteractions(gameRepository);
		
	}

	// Se encarga de testear si un game guarda correctamente sin temas, pero con
	// Sprints y Groups Correctos.
	@DisplayName("Guardar Juego sin Tema")
	@Test
	public void testSaveGameSinTema() {
		TsscGame game = new TsscGame();
		game.setNGroups(27);
		game.setNSprints(50);

		when(gameRepository.save(game)).thenReturn(game);

		try {
			assertTrue(gameService.saveGame(game).equals(game));
		} catch (CapacityException | GameException | SpringException e) {
			// TODO Auto-generated catch block
			fail();
		}
		verify(gameRepository, times(1)).save(game);

	}

	// Se encarga de testear que lance la excepción, TopicException, al guardar un
	// Juego con un tema que no existe
	// en la data-base.
	@DisplayName("Juego con Tema Inexistente")
	@Test
	public void testSaveGameTopicException() {
		long id = 2;
		TsscGame game = new TsscGame();

		when(topicRepository.findById(id)).thenReturn(null);

		assertThrows(TopicException.class, () -> {
			gameService.saveGameWithTopic(game, id);
		});

		Mockito.verifyNoInteractions(gameRepository);
	}

	@DisplayName("Guardar Juego con Tema")
	@Test
	public void testSaveGameConTema() {

		long id = 1;
		TsscGame game = new TsscGame();

		Mockito.when(gameRepository.save(game)).thenReturn(game);
		Mockito.when(topicRepository.findById(id)).thenReturn(Optional.of(new TsscTopic()));

		try {
			assertTrue(gameService.saveGameWithTopic(game, id).equals(game));
		} catch (TopicException | CapacityException | SpringException e) {
			// TODO Auto-generated catch block
			fail();
		}
	}

	// *************************************
	// PRUEBAS PARA EL EDITAR
	// **************************************

	// Verifica si el método lanza la excepcion cuando se intenta actualizar un
	// game inexistente en la database.
	@DisplayName("Editar Game Inexistente")
	@Test
	public void testEditGameException() {

		TsscGame game = new TsscGame();
		
		Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(null);
		
		assertThrows(GameException.class, () -> {
			gameService.editGame(game);
		});
		
		
	}

	// Verifica si el método lanza la excepcion cuando se intenta
	// actualizar un game nulo.
	@DisplayName("Editar Game null")
	@Test
	public void testEditGameExceptionNull() {
		assertThrows(GameException.class, () -> {
			gameService.editGame(null);
		});
		
		Mockito.verifyNoInteractions(gameRepository);

	}

	// Verifica si el método edita de manera correcta cuando se intenta actualizar
	// un game existente en la database.
	@DisplayName("Editar Game Correctamente")
	@Test
	public void testEditGameNotException() {

		try {

			// Por el cual voy a editar.
			TsscGame game = new TsscGame();
			game.setNGroups(50);
			game.setNSprints(20);

			Optional<TsscGame> list = Optional.of(game);

			Mockito.when(gameRepository.save(Mockito.any())).thenReturn(game);
			Mockito.when(gameRepository.findById(game.getId())).thenReturn(list);

			assertTrue(gameService.editGame(game).equals(game));

			verify(gameRepository, times(1)).save(game);

		} catch (GameException e) {
			fail();
		}

	}

}
