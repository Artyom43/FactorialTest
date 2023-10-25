package ru.mullin.factorialtest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val coroutineScope =
        CoroutineScope(CoroutineName("my coroutine scope") + Dispatchers.Main)


    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    fun calculate(value: String?) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }
        coroutineScope.launch {
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            Log.d("MAinViewModel", coroutineContext.toString())
            _state.value = Success(result)
        }
    }

    private fun factorial(number: Long): String {
        var result = BigInteger.ONE
        (1..number).forEach {
            result = result.multiply(BigInteger.valueOf(it))
        }
        return result.toString()
    }

//    private suspend fun factorial(number: Long): String {
//        return suspendCoroutine {
//            thread {
//                var result = BigInteger.ONE
//                (1..number).forEach {
//                    result = result.multiply(BigInteger.valueOf(it))
//                }
//                it.resumeWith(Result.success(result.toString()))
//            }
//        }
//    }

}