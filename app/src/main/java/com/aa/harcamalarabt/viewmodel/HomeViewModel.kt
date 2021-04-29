package com.aa.harcamalarabt.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aa.harcamalarabt.model.CurrencyModel
import com.aa.harcamalarabt.service.CurrencyAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel: ViewModel() {

    private val service = CurrencyAPIService()
    private val compositeDisposable = CompositeDisposable()

    val errorMessage = MutableLiveData<Boolean>()
    val loadingMessage = MutableLiveData<Boolean>()
    val data = MutableLiveData<CurrencyModel>()

    fun getData(){
        getDataFromInternet()
    }

    private fun getDataFromInternet(){
        loadingMessage.value = true
        compositeDisposable.add(service.loadData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<CurrencyModel>(){
                override fun onSuccess(t: CurrencyModel) {
                    data.value = t
                    errorMessage.value = false
                    loadingMessage.value = false
                    println("Tamamlandi")
                }
                override fun onError(e: Throwable) {
                    errorMessage.value = true
                    loadingMessage.value = false
                    println(e.localizedMessage)
                }
            }))
    }

}