package com.example.githubusers.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.example.githubusers.databinding.ItemRvBinding
import com.example.githubusers.models.GithubUsers
lateinit var requestQueue: RequestQueue
class MyUserAdapter(var list: List<GithubUsers>,val rvClick: RvClick): RecyclerView.Adapter<MyUserAdapter.VH>() {



    inner class VH(private var itemRV: ItemRvBinding):RecyclerView.ViewHolder(itemRV.root){
        fun onBind(githubUsers: GithubUsers,position: Int){
                itemRV.tvGit.text = githubUsers.login
                itemRV.root.setOnClickListener {
                    rvClick.itemClick(githubUsers,position)
                }

           val imageRequest =      ImageRequest(githubUsers.avatar_url,object : Response.Listener<Bitmap>{
                    override fun onResponse(response: Bitmap?) {
                            itemRV.ivGit.setImageBitmap(response)
                    }
                },0,0,ImageView.ScaleType.CENTER_CROP,Bitmap.Config.ARGB_8888,object :Response.ErrorListener{
                    override fun onErrorResponse(error: VolleyError?) {

                    }
                })
            requestQueue.add(imageRequest)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface RvClick{
        fun itemClick(githubUsers: GithubUsers,position: Int)

    }
}