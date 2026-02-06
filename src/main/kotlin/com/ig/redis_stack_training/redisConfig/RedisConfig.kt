package com.ig.redis_stack_training.redisConfig

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Primary
@Configuration
@EnableRedisDocumentRepositories(basePackages = ["com.ig.redis_stack_training.*"])
@EnableRedisRepositories
class RedisConfig {

    @Value("\${spring.data.redis.host}")
    var host: String? = null

    @Value("\${spring.data.redis.port}")
    var port: Int? = null

    @Value("\${spring.data.redis.password}")
    var password: String? = null

    @Bean
    fun connectionFactory(): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration()
        config.hostName = host!!
        config.port = port!!
        val redisPassword = password ?: "myRedisPassword"
        config.password = RedisPassword.of(redisPassword.toCharArray())
        return JedisConnectionFactory(config)
    }
}