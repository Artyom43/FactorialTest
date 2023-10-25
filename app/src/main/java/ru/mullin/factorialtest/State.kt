package ru.mullin.factorialtest

sealed class State

data object Error : State()
data object Progress : State()
class Success(
    val factorial: String = "",
) : State()
