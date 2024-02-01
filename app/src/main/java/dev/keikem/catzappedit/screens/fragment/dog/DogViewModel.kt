package dev.keikem.catzappedit.screens.fragment.dog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.keikem.catzappedit.domain.usecases.GimmeADogLocalUseCase
import dev.keikem.catzappedit.domain.usecases.GimmeADogRemoteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DogViewModel : ViewModel() {

    private val gimmeADogRemoteUseCase: GimmeADogRemoteUseCase by lazy { GimmeADogRemoteUseCase() }
    private val gimmeADogLocalUseCase: GimmeADogLocalUseCase by lazy { GimmeADogLocalUseCase() }


    private var _imageUrl: MutableLiveData<String?> = MutableLiveData("")
    val imageUrl: LiveData<String?> = _imageUrl

    init {
        loadFromLocal()
    }

    private fun loadFromLocal() {
        Thread {
            val im = gimmeADogLocalUseCase.gimme()
            im?.let { image -> _imageUrl.postValue(image) }
        }.start()
    }

//    fun loadFromRemote() {
//        //Thread - отдельный поток выполнения, он отвечает за то где будет выполнятся операция
//        Thread {
//            Thread.sleep(5000)
//            _imageUrl.postValue(gimmeADogRemoteUseCase.gimme())
//        }.start()
//    }


    fun loadFromRemote() {
        val coroutinExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            _imageUrl.postValue(null)
        }
        viewModelScope.launch(Dispatchers.IO + coroutinExceptionHandler) {
            _imageUrl.postValue( gimmeADogRemoteUseCase.gimme())
        }
    }
//    fun loadFromRemote() {
//        viewModelScope.launch(Dispatchers.IO) {
//            delay(5000)
//            _imageUrl.postValue(gimmeADogRemoteUseCase.gimme())
//        }
//    }
}