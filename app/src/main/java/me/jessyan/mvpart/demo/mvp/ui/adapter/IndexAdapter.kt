package me.jessyan.mvpart.demo.mvp.ui.adapter

import me.jessyan.art.base.BaseHolder
import me.jessyan.art.base.DefaultAdapter
import me.jessyan.mvpart.demo.R
import me.jessyan.mvpart.demo.mvp.model.entity.IndexEntity

/**
 * Created by chan on 2018/4/13.
 */
class IndexAdapter(infos: MutableList<IndexEntity.SubsBean>) : DefaultAdapter<IndexEntity.SubsBean>(infos) {

    override fun bindData(holder: BaseHolder<*>?, position: Int, item: IndexEntity.SubsBean?) {
        holder?.run {
            item?.run {
                setImageByUrl(R.id.iv_avatar, item.resourceUrl)
                        .setText(R.id.tv_name, item.subModuleName)
            }
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.recycle_list
    }

}