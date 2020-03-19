package co.edu.icesi.fi.tics.tssc.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.repositories.ITopicRepository;
import co.edu.icesi.fi.tics.tssc.services.TopicService;
import co.edu.icesi.fi.tics.tssc.services.TopicServiceImpl;

import org.mockito.junit.MockitoJUnitRunner;
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
	
	//Se encarga de verificar si lanza la excepcion al tener un topic con Spring menor o igual a cero.
	@Test
	public void testSpringException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultSprints(0);
	    topic.setDefaultGroups(3);
		
		assertThrows(SpringException.class, () -> {			
			topicService.saveTopic(topic);			
		});
		
	}
	
	
	//Se encarga de testear si un topic guarda correctamente con Spring.
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
	
	//Se encarga de verificar si lanza la excepcion al tener un topic con Groups  menor o igual a cero.
	@Test
	public void testGroupsException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultGroups(-2);
		topic.setDefaultSprints(3);
	
		
		assertThrows(CapacityException.class, () -> {			
			topicService.saveTopic(topic);			
		});
		
	}
	
	//Se encarga de testear si un topic guarda correctamente con Group.
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
	
	
	
	
}
