package com.traum

import com.traum.dtos.auth.LoginDTO
import com.traum.factories.IConnectionFactory
import com.traum.serializers.BigDecimalSerializer
import com.traum.serializers.TimestampSerializer
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import java.math.BigDecimal
import java.sql.Timestamp
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability

/**
 * @param func функция-расширение для параметра T, которая возвращает элемент E
 * @return Функция, принимающая экземпляр класса T и возвращающая экземпляр E
 */
fun <T, E> scoped(func: T.() -> E): (T) -> E = { it.func() }

object JWTOptions {
    var secret: String? = null
    var issuer: String? = null
    var audience: String? = null
    var myRealm: String? = null
}

object RepositoryInjector : KoinComponent {
    val factory by inject<IConnectionFactory>()
    inline fun <reified T : Any> getRepository(call: ApplicationCall): T {
        val principal = call.principal<JWTPrincipal>()
        var username = "admin"
        var password = "admin"
        if (principal != null) {
            username = principal.payload.getClaim("username").asString()
            password = principal.payload.getClaim("password").asString()
        }
        return this@RepositoryInjector.get<T> { parametersOf(factory.createConnection(username, password)) }
    }

    inline fun <reified T : Any> getRepository(loginDTO: LoginDTO): T {
        return this@RepositoryInjector.get<T> {
            parametersOf(
                factory.createConnection(
                    loginDTO.username,
                    loginDTO.password
                )
            )
        }
    }
}

typealias BigDecimalJson = @Serializable(with = BigDecimalSerializer::class) BigDecimal
typealias TimestampJson = @Serializable(with = TimestampSerializer::class) Timestamp

/**
 * Преобразует объект класса T в класс R
 */
fun <T : Any, R : Any> T.adapt(value: R) {
    val props = this::class.memberProperties
    value::class.memberProperties.forEach { valueProp ->
        if (valueProp.visibility == KVisibility.PUBLIC && valueProp is KMutableProperty1) {
            val prop = props.singleOrNull { x -> x.name == valueProp.name }
            val propType = prop?.returnType?.withNullability(valueProp.returnType.isMarkedNullable)
            if (propType == valueProp.returnType) {
                val field = prop.getter.call(this)
                if (field != null)
                    valueProp.setter.call(value, field)
            }
        }
    }
}

/**
 *  Преобразует объект класса T в созданный объект класса R
 */
inline fun <T : Any, reified R : Any> T.adapt(): R {
    val value = primaryCtor(R::class)
    this.adapt(value)
    return value
}

/**
 *  Получает объект класс T и преобразовывает в объект класса R
 */
suspend inline fun <reified T : Any, reified R : Any> ApplicationCall.receiveAndAdapt(): R =
    this.receive<T>().adapt<T, R>()

fun <T : Any> primaryCtor(kClass: KClass<T>): T {
    val ctor = kClass.constructors.find { it.parameters.all { param -> param.isOptional } }
        ?: throw RuntimeException("cannot create default constructor")
    return ctor.callBy(emptyMap())
}

fun currentTimestamp(): Timestamp = Timestamp(System.currentTimeMillis())