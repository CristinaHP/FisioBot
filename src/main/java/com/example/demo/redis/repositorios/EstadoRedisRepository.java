package com.example.demo.redis.repositorios;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.redis.config.JedisConfiguration;
import com.example.demo.statemachine.modelo.Estado;

/**
 * Clase que representa el repositorio de los registros que se van a guardar en Redis. Estos registros constan de un id
 * que representa el chatId del usuario de Telegram, y un Estado de la máquina de estados.
 * @author Cristina Herráez Pérez
 */

@Repository
public class EstadoRedisRepository
{
	public static final String STATEMACHINE_KEY = "statemachine"; ///< Clave para las operaciones hash que se realizan con Redis.
	
	private HashOperations<String, String, Estado> hashOperations; ///< Operaciones hash para realizar las operaciones CRUD con Redis.
	private RedisTemplate<String, Estado> redisTemplate; ///< Plantilla de Redis para poder realizar las operaciones hash.
	
	/**
	 * Crea un repositorio de Redis.
	 * @param redisTemplate RedisTemplate - Plantilla de Redis
	 */
	public EstadoRedisRepository(RedisTemplate<String, Estado> redisTemplate) 
	{
		this.redisTemplate = redisTemplate;
		redisTemplate.setEnableTransactionSupport(true);
		this.hashOperations = this.redisTemplate.opsForHash();
	}
	
	/**
	 * Genera una conexión con Redis utilizando un objeto RedisTemplate.
	 * @param ctx ConfigurableApplicationContext - Contexto de la aplicación
	 * @return Objeto de operaciones hash
	 */
	@SuppressWarnings("unchecked")
	private HashOperations<String, String, Estado> getConnection(ConfigurableApplicationContext ctx) 
	{
		redisTemplate = (RedisTemplate<String, Estado>) ctx.getBean("redisTemplate");
		hashOperations = redisTemplate.opsForHash();
		return hashOperations;
	}
	
	/**
	 * Cierra la cconexión con Redis.
	 * @param ctx ConfigurableApplicationContext - Contexto de la aplicación
	 */
	private void closeConnection(ConfigurableApplicationContext ctx)
	{
		ctx.close();
	}
	
	/**
	 * Busca un estado en Redis por su id.
	 * @param id Id del usuario, equivalente a su chatId de Telegram
	 * @return último estado en el que se quedó el usuario en la máquina de estados
	 */
	public Estado findById(String id)
	{
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(JedisConfiguration.class);
		hashOperations = getConnection(ctx);

		Estado estado = hashOperations.get(STATEMACHINE_KEY, id);

		closeConnection(ctx);
		
		return estado;
	}
	
	/**
	 * Inserta o actualiza un registro en Redis.
	 * @param estado Estado de la máquina de estados en el que se encuentra el usuario
	 * @param id Id del usuario, equivalente a su chatId de Telegram
	 */
	public void save(Estado estado, String id) 
	{
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(JedisConfiguration.class);
		hashOperations = getConnection(ctx);

		hashOperations.put(STATEMACHINE_KEY, id, estado);

		closeConnection(ctx);
	}
	
	/**
	 * Elimina un registro de Redis por el id.
	 * @param id Id del usuario, equivalente a su chatId de Telegram
	 */
	public void delete(String id)
	{
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(JedisConfiguration.class);
		hashOperations = getConnection(ctx);
		
		hashOperations.delete(STATEMACHINE_KEY, id);
		
		closeConnection(ctx);
	}
}
