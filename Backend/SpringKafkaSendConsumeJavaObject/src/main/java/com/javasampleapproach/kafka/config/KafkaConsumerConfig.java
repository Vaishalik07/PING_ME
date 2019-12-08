package com.javasampleapproach.kafka.config;

import java.util.HashMap;
import java.util.Map;

import com.javasampleapproach.kafka.model.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value("${jsa.kafka.bootstrap-servers}")
	private String bootstrapServer;

	@Value("foo")
	private String groupIdUser;

	//---------------------- User Channel Consumer Bean-------------------
	@Bean
	public ConsumerFactory<String, User> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdUser);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		return new DefaultKafkaConsumerFactory<>(props,
				new StringDeserializer(),
				new JsonDeserializer<>(User.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
//------------------------------------------------------------------------------------------------------------------
	@Value("goo")
	private String groupIdMessage;

	//----------------------------------------Direct Message Consumer Bean-------------------------
	@Bean
	public ConsumerFactory<String, Message> consumerFactoryDM() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdMessage);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Message.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactoryDM() {
		ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryDM());
		return factory;
	}
	//---------------------------------------------------------------------------------------------------------

	//----------------------------------------Channel Message Consumer Bean-------------------------------------------
	@Bean
	public ConsumerFactory<String, ChannelMessage> consumerFactoryCM() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdMessage);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ChannelMessage.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,ChannelMessage> kafkaListenerContainerFactoryCM() {
		ConcurrentKafkaListenerContainerFactory<String, ChannelMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryCM());
		return factory;
	}
	//--------------------------------------------------------------------------------------------------------------------------

	//------------ Channel Request Bean -------------------------------------------------------------------------------------
	@Bean
	public ConsumerFactory<String, Channel> consumerFactoryChannel() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdMessage);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Channel.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,Channel> kafkaListenerContainerFactoryChannel() {
		ConcurrentKafkaListenerContainerFactory<String, Channel> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryChannel());
		return factory;
	}

	// Git Channel Request Bean ------------------------------------------------------------------------------------------
	@Bean
	public ConsumerFactory<String, GitStuff> consumerFactoryGit() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdMessage);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(GitStuff.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String,GitStuff> kafkaListenerContainerFactoryGit() {
		ConcurrentKafkaListenerContainerFactory<String, GitStuff> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryGit());
		return factory;
	}

}
