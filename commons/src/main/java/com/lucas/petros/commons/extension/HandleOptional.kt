package com.lucas.petros.commons.extension

fun String?.handleOpt() = this ?: ""

fun Int?.handleOpt() = this ?: 0

fun Long?.handleOpt() = this ?: 0

fun Boolean?.handleOpt() = this ?: false

fun <T> List<T>?.handleOpt() = this ?: listOf()
