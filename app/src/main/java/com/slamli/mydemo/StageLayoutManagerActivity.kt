package com.slamli.mydemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_stage.*

/**
 * Created by slam.li on 2017/8/17.
 */
class StageLayoutManagerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = MyAdapter()
    }

    class MyAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == 0)
                return MyViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.stage_1, parent, false))
            else
                return MyViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.stage_2, parent, false))
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun getItemViewType(position: Int): Int {
            return position % 2
        }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

}