package com.example.quizapp.core

// "https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">
sealed class Either<out L, out R> {

    data class Left<out L>(val a: L) : Either<L, Nothing>()

    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRight: Boolean get() = this is Right<R>

    val isLeft: Boolean get() = this is Left<L>

    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }

    fun onSuccess(fn: (success: R) -> Unit): Either<L, R> = apply { if (this is Right) fn(b) }
    fun onError(fn: (error: L) -> Unit): Either<L, R> = apply { if (this is Left) fn(a) }
}

fun <L> left(a: L) = Either.Left(a)

fun <R> right(b: R) = Either.Right(b)

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(a)
        is Either.Right -> fn(b)
    }

fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))

fun <T, L, R> Either<L, R>.either(fnL: (L) -> T, fnR: (R) -> T): T =
    when (this) {
        is Either.Left -> fnL(a)
        is Either.Right -> fnR(b)
    }
