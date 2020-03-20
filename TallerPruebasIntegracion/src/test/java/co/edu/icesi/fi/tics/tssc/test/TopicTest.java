package co.edu.icesi.fi.tics.tssc.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.ITopicRepository;
import co.edu.icesi.fi.tics.tssc.services.TopicService;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.testng.annotations.BeforeTest;


@RunWith(MockitoJUnitRunner.class)
class TopicTest {

	@Mock
	private ITopicRepository topicRepository;

	@InjectMocks
	private TopicServiceImpl topicService;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	// *********************************************
	// PRUEBAS PARA EL GUARDAR
	// *********************************************

	// Se encarga de verificar si lanza la excepcion al tener un topic con Spring
	// menor o igual a cero.
	@Test
	public void testSpringException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultSprints(0);
		topic.setDefaultGroups(3);

		assertThrows(SpringException.class, () -> {
			topicService.saveTopic(topic);
		});
	}

	// Se encarga de testear si un topic guarda correctamente con Spring.
	@Test
	public void testSpringNotException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultSprints(1);
		topic.setDefaultGroups(2);

		when(topicRepository.save(topic)).thenReturn(topic);
		try {
			assertTrue(topicService.saveTopic(topic).equals(topic));
		} catch (CapacityException | TopicException | SpringException e) {
			// TODO Auto-generated catch block
			fail();
		}
		verify(topicRepository, times(1)).save(topic);

	}

	// Se encarga de verificar si lanza la excepcion al tener un topic con Groups
	// menor o igual a cero.
	@Test
	public void testGroupsException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultGroups(-2);
		topic.setDefaultSprints(3);

		assertThrows(CapacityException.class, () -> {
			topicService.saveTopic(topic);
		});

	}

	// Se encarga de testear si un topic guarda correctamente con Group.
	@Test
	public void testGroupsNotException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultGroups(2);
		topic.setDefaultSprints(2);

		when(topicRepository.save(topic)).thenReturn(topic);
		try {
			assertTrue(topicService.saveTopic(topic).equals(topic));
		} catch (CapacityException | TopicException | SpringException e) {
			// TODO Auto-generated catch block
			fail();
		}
		verify(topicRepository, times(1)).save(topic);
	}

	// *************************************
	// PRUEBAS PARA EDITAR
	// *************************************

	// Verifica si el método lanza la excepcion cuando se intenta actualizar un
	// topic inexistente en la database.
	@Test
	public void testEditTopicException() {

		TsscTopic topic2 = new TsscTopic();
		topic2.setDefaultGroups(5);
		topic2.setDefaultSprints(10);

		assertThrows(NoSuchElementException.class, () -> {
			topicService.editTopic(topic2);
		});
	}

	// Verifica si el método lanza la excepcion cuando se intenta
	// actualizar un topic nulo.
	@Test
	public void testEditTopicExceptionNull() {
		assertThrows(TopicException.class, () -> {
			topicService.editTopic(null);
		});

	}

	// Verifica si el método edita de manera correcta cuando se intenta actualizar
	// un topic existente en la database.

	@Test
	public void testEditTopicNotException() {

		try {

			//Por el cual voy a editar.
			TsscTopic topic2 = new TsscTopic();
			topic2.setDefaultGroups(35);
			topic2.setDefaultSprints(50);

			Optional<TsscTopic> list = Optional.of(topic2);
			
			Mockito.when(topicRepository.save(topic2)).thenReturn(topic2);
			Mockito.when(topicRepository.findById(topic2.getId())).thenReturn(list);
			
			assertTrue(topicService.editTopic(topic2).equals(topic2));
			
			verify(topicRepository, times(1)).save(topic2);

		} catch (TopicException e) {
			fail();
		}

	}


}
