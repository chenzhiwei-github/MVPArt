package me.jessyan.mvpart.demo.mvp.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import me.jessyan.art.di.component.AppComponent
import me.jessyan.art.mvp.BasePresenter
import me.jessyan.art.mvp.IModel
import me.jessyan.art.mvp.IRepositoryManager
import me.jessyan.art.mvp.Message
import me.jessyan.mvpart.demo.app.ApiConfiguration
import me.jessyan.mvpart.demo.app.utils.net.NetworkUtils
import me.jessyan.mvpart.demo.mvp.model.entity.IndexEntity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.*

class MainPresenter(appComponent: AppComponent) : BasePresenter<IModel>() {
    private var mErrorHandler: RxErrorHandler? = null
    private var mManager: IRepositoryManager? = null

    init {
        this.mErrorHandler = appComponent.rxErrorHandler()
        this.mManager = appComponent.repositoryManager()
    }

    fun reqIndex(msg: Message) {
        val map = TreeMap<String, String>()
        map["moduleNo"] = "android_index"
        NetworkUtils.requestToObject(mManager, ApiConfiguration.Domain.GETPICTURELIST, map, IndexEntity::class.java)
                .doOnSubscribe { disposable ->
                    addDispose(disposable)
                    msg.target.showLoading()
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    msg.target.hideLoading()
                    msg.recycle()
                }
                .subscribe(object : ErrorHandleSubscriber<IndexEntity>(mErrorHandler!!) {
                    override fun onNext(indexEntity: IndexEntity) {
                        msg.what = 0
                        msg.obj = indexEntity
                        msg.handleMessageToTargetUnrecycle()
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mErrorHandler = null
        this.mManager = null
    }

}