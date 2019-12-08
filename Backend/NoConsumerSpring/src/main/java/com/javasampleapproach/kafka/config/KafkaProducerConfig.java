package com.javasampleapproach.kafka.config;

import java.util.HashMap;
import java.util.Map;

import com.javasampleapproach.kafka.model.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public class KafkaProducerConfig {

	@Value("${jsa.kafka.bootstrap-servers}")
	private String bootstrapServer;
	//-----------------------------------User Producer factory Bean ------------------------------
	@Bean
	public ProducerFactory<String, User> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, User> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

//--------------------------------------------------------------------------------------------------------

//--------------------------------------Direct Message Producer Factory Bean-------------------------------
    @Bean
	public ProducerFactory<String, Message> producerFactoryDM() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, Message> kafkaTemplateDM() {
		return new KafkaTemplate<>(producerFactoryDM());
	}

//----------------------------------------------------------------------------------------------------------

//-------------------------------ChannelMessage Bean----------------------------------------------------

	@Bean
	public ProducerFactory<String, ChannelMessage> producerFactoryCM() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, ChannelMessage> kafkaTemplateCM() {
		return new KafkaTemplate<>(producerFactoryCM());
	}
	//-----------------------------------------------------------------------------------------------------

	//--------------------------------------Channel creation Bean -------------------------------------------------
	@Bean
	public ProducerFactory<String, Channel> producerFactoryChannel() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, Channel> kafkaTemplateChannel() {
		return new KafkaTemplate<>(producerFactoryChannel());
	}
//-----------------------------------------------------------------------------------------------------------------------
	//------------------------------------- Git related Channel Bean --------------------------------------------
	@Bean
	public ProducerFactory<String, GitStuff> producerFactoryGit() {
	Map<String, Object> configProps = new HashMap<>();
	configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
	configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	return new DefaultKafkaProducerFactory<>(configProps);
}

	@Bean
	public KafkaTemplate<String, GitStuff> kafkaTemplateGit() {
		return new KafkaTemplate<>(producerFactoryGit());
	}
	//----------------------------------------------------------------------------------------------------------------
}

