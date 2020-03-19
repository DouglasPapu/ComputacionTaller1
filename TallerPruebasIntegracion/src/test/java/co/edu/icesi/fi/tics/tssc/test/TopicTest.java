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
	
	@Test
	public void testSpringException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultSprints(0);
	    topic.setDefaultGroups(0);
		
		assertThrows(RuntimeException.class, () -> {			
			topicService.saveTopic(topic);			
		});
		
	}
	
	
	
	@Test
	public void testSpringNotException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultSprints(1);
		topic.setDefaultGroups(2);
	
		when(topicRepository.save(topic)).thenReturn(topic);
		assertTrue(topicService.saveTopic(topic).equals(topic));
		verify(topicRepository, times(1)).save(topic);
		
	}
	
	@Test
	public void testGroupsException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultGroups(0);
		topic.setDefaultSprints(-1);
	
		
		assertThrows(RuntimeException.class, () -> {			
			topicService.saveTopic(topic);			
		});
		
	}
	
	@Test
	public void testGroupsNotException() {
		TsscTopic topic = new TsscTopic();
		topic.setDefaultGroups(2);
		topic.setDefaultSprints(2);
		
		when(topicRepository.save(topic)).thenReturn(topic);
		assertTrue(topicService.saveTopic(topic).equals(topic));
		verify(topicRepository, times(1)).save(topic);
	}
	
	
	
}
