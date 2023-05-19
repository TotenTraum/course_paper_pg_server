package com.traum

import java.sql.Timestamp

/**
 * @param func функция-расширение для параметра T, которая возвращает элемент E
 * @return Функция, принимающая экземпляр класса T и возвращающая экземпляр E
 */
fun <T, E> scoped(func: T.() -> E): (T) -> E = { it.func() }


fun currentTimestamp(): Timestamp = Timestamp(System.currentTimeMillis())