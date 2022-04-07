package com.example.githubusers

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.githubusers.adapter.MyUserAdapter
import com.example.githubusers.adapter.requestQueue
import com.example.githubusers.databinding.ActivityMainBinding

import com.example.githubusers.models.GithubUsers
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.info.view.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(this)
        loadArray("https://api.github.com/users")

    }

    private fun loadArray(url: String) {
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,url,null,object: Response.Listener<JSONArray>{
            override fun onResponse(response: JSONArray?) {
                val str = response.toString()
                val type = object :TypeToken<ArrayList<GithubUsers>>(){}.type
                val list = Gson().fromJson<ArrayList<GithubUsers>>(str,type)
                val myUserAdapter = MyUserAdapter(list,object :MyUserAdapter.RvClick{
                    @SuppressLint("SetTextI18n")
                    override fun itemClick(githubUsers: GithubUsers, position: Int) {
                            val dialog = AlertDialog.Builder(this@MainActivity).create()
                            val infoUser = LayoutInflater.from(this@MainActivity).inflate(R.layout.info,null,false)
                            infoUser.tv_1.text ="login-> " +  githubUsers.login
                            infoUser.tv_2.text = "avatar-url-> "+ githubUsers.avatar_url
                            infoUser.tv_3.text = "id-> "+githubUsers.id
                            infoUser.tv_4.text ="followers-url-> "+ githubUsers.followers_url
                            infoUser.tv_5.text = "following-url-> "+githubUsers.following_url
                            infoUser.tv_6.text = "gists-url-> "+githubUsers.gists_url
                            infoUser.tv_7.text ="html-url-> "+ githubUsers.html_url
                            infoUser.tv_8.text = "events-url-> "+githubUsers.events_url

                            dialog.setView(infoUser)
                            dialog.show()
                    }
                })
                binding.rvGithub.adapter = myUserAdapter

            }
        },object:Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {

            }
        })
        requestQueue.add(jsonArrayRequest)
    }



}