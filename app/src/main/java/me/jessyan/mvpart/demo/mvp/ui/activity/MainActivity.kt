package me.jessyan.mvpart.demo.mvp.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.jessyan.art.base.BaseActivity
import me.jessyan.art.base.DefaultAdapter
import me.jessyan.art.mvp.IView
import me.jessyan.art.mvp.Message
import me.jessyan.art.utils.ArtUtils
import me.jessyan.mvpart.demo.R
import me.jessyan.mvpart.demo.app.utils.net.NetworkUtils
import me.jessyan.mvpart.demo.mvp.model.entity.IndexEntity
import me.jessyan.mvpart.demo.mvp.presenter.MainPresenter
import me.jessyan.mvpart.demo.mvp.ui.adapter.IndexAdapter

class MainActivity : BaseActivity<MainPresenter>(), IView {

    private val mAdapter = IndexAdapter(mutableListOf())

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        initView()
        initData()
    }

    private fun initView() {
        ArtUtils.configRecyclerView(recyclerView, GridLayoutManager(this, 2))
        recyclerView.adapter = mAdapter
    }

    private fun initData() {
        mPresenter.reqIndex(Message.obtain(this))
    }

    override fun obtainPresenter(): MainPresenter? {
        return MainPresenter(ArtUtils.obtainAppComponentFromContext(this))
    }

    override fun showLoading() {
        NetworkUtils.showLoading()
    }

    override fun hideLoading() {
        NetworkUtils.hideLoading()
    }

    override fun showMessage(message: String) {
        ArtUtils.snackbarText(message)
    }

    override fun handleMessage(message: Message) {
        when (message.what) {
            0 -> {
                val indexEntity = message.obj as IndexEntity
                mAdapter.updateData(indexEntity.subs)
            }
            1 -> {
            }
        }
    }

    override fun onDestroy() {
        DefaultAdapter.releaseAllHolder(recyclerView)//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy()
    }

}
