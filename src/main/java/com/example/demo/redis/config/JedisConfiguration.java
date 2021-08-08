package com.example.demo.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.example.demo.StaticData;
import com.example.demo.statemachine.modelo.Estado;

/**
 * Clase que implementa la configuración necesaria para el acceso a la base de datos de Redis
 * @author Cristina Herráez Pérez
 */

@Configuration
@EnableRedisRepositories
public class JedisConfiguration 
{
	/**
	 * Configura la conexión con un host un puerto determinados.
	 * @return JedisConnectionFactory Factoría de conexiones a Redis
	 */
	@Bean
    public JedisConnectionFactory jedisConnectionFactory() 
	{
        return new JedisConnectionFactory(new RedisStandaloneConfiguration(StaticData.IP_LOCALHOST, StaticData.REDIS_PORT));
    }
	
	/** 
	 * Crea un plantilla para las operaciones con Redis utilizando una factoría de conexiones.
	 * @return RedisTemplate Plantilla que contiene una factoría de conexiones
	 */
	@Bean
    public RedisTemplate<String, Estado> redisTemplate() 
	{
        RedisTemplate<String, Estado> template = new RedisTemplate<>();
        
        template.setConnectionFactory(jedisConnectionFactory());
        
        return template;
    }
}
