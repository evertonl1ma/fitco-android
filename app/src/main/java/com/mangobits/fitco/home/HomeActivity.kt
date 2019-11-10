package com.mangobits.fitco.home


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mangobits.agroaz.splash.SplashActivity
import com.mangobits.fitco.R
import com.mangobits.fitco.about.AboutActivity
import com.mangobits.fitco.app.App
import com.mangobits.fitco.contact.ContactActivity
import com.mangobits.fitco.post.Post
import com.mangobits.fitco.post.PostAdapter
import com.mangobits.fitco.post.PostSearch
import com.mangobits.fitco.post.PostService
import com.mangobits.fitco.price.PriceActivity
import com.mangobits.fitco.reports.WeeklyReportsActivity
import com.mangobits.fitco.product.MainActivity
import com.mangobits.fitco.product.ProductActivity
import com.mangobits.startupkit.core.service.JsonContainer
import com.mangobits.startupkit.core.service.RestServiceListener
import com.mangobits.startupkit.user.UserFacade
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*
import mangobits.startupkit.core.util.MessageAlertDialog

import java.util.ArrayList

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, PostAdapter.ClickpostListener {


    var mAdapter: PostAdapter? = null

    var mLayoutManager: RecyclerView.LayoutManager? = null

    var recyclerView: RecyclerView? = null

    var postSearch: PostSearch? = null
    var listPost = ArrayList<Post>()


    var loading: Boolean? = null
    var page: Int = 1

    var swipe: SwipeRefreshLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

//
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        title = ""

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));


        drawer.addDrawerListener(toggle)
        toggle.syncState()


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.inflateMenu(R.menu.home_drawer)

        txtSeeMore.setOnClickListener(clickSeeMore)



        recyclerView = findViewById<RecyclerView>(R.id.mRecyclerHomePost)

        mLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = mLayoutManager





        mAdapter = PostAdapter(this, ArrayList<Post>())
        mAdapter!!.setClickPostListener(this)

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                val visibleItemCount = (mLayoutManager as LinearLayoutManager).getChildCount()
                val totalItemCount = (mLayoutManager as LinearLayoutManager).getItemCount()
                val pastVisiblesItems = (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()


                if (!loading!! && visibleItemCount + pastVisiblesItems >= totalItemCount) {

//                    mAdapter!!.notifyDataSetChanged()
                    page++
                    loadService()
                }
            }
        })

        swipe = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        swipe!!.setOnRefreshListener { loadService() }
        page = 1
        loadService()


    }

    var clickSeeMore = View.OnClickListener {

        val intent = Intent(this@HomeActivity, MainActiviesActivity::class.java)
        startActivity(intent)

    }


    fun loadService() {

        loading = true




        postSearch = PostSearch()

//        if (user != null) {
//            postSearch!!.idUser = user!!.id
//        }
        postSearch!!.page = page
//        if (App.latitude != null && App.longitude != null) {
//            postSearch!!.lat = App.latitude
//            postSearch!!.log = App.longitude

//        }

        PostService(this).search(postSearch!!, object : RestServiceListener<Array<Post>> {
            override fun processCallback(resultado: JsonContainer, dados: Array<Post>?) {

                if (resultado.success!!) {
                    if (dados != null) {

                        if (page == 1) {
                            recyclerView!!.adapter = mAdapter
                            listPost = dados.toCollection(ArrayList())
                            recyclerView!!.scrollToPosition(0)
                            mAdapter!!.animateTo(listPost)
                        } else {
                            listPost.addAll(dados.toCollection(ArrayList()))
                            mAdapter!!.setListItens(listPost)
                            mAdapter!!.updateItems()
                        }


                        loadData()
                    }
                } else {
                    val id = resources.getIdentifier(resultado.desc, "string", packageName)
                    val value = if (id == 0) resultado.desc else resources.getText(id) as String
                    val alert = MessageAlertDialog.createMsgDialog(this@HomeActivity, getText(R.string.validation).toString(), value!!).show()
                }
            }

        })
        onItemsLoadComplete()


    }

    fun onItemsLoadComplete() {
        // Atualize o adapter e notifique que os dados mudaram

        // Pare o refresh animation
        swipe!!.isRefreshing = false
    }


    fun loadData() {

        if (listPost.count() > 0) {
            loading = false

//            lblFeedEmpty!!.visibility = View.GONE
            recyclerView!!.visibility = View.VISIBLE
//            recyclerView!!.adapter = mAdapter
//
//            mAdapter!!.setListItens(listPost)
//            mAdapter!!.updateItems()

        } else {
//            lblFeedEmpty!!.visibility = View.VISIBLE
            recyclerView!!.visibility = View.INVISIBLE


        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        //item.isChecked = false

        if (id == R.id.nav_about) {
            startActivity(Intent(this@HomeActivity, AboutActivity::class.java))
        } else if (id == R.id.nav_report) {
            startActivity(Intent(this@HomeActivity, WeeklyReportsActivity::class.java))
        } else if (id == R.id.nav_product) {
            startActivity(Intent(this@HomeActivity, ProductActivity::class.java))
        } else if (id == R.id.nav_price) {
            startActivity(Intent(this@HomeActivity, PriceActivity::class.java))
        }


        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.home, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent = Intent(this@HomeActivity, ContactActivity::class.java)
        startActivity(intent)

//        val id = item.itemId
//        if (id == R.id.log_in) {
//            return false
//        }
//
        return true
    }
//


    override fun clickPost(view: View, position: Int) {
        val post = listPost!!.get(position)

        openNewTabWindow(post.infoUrl!!.url.toString(), this)

    }

    fun openNewTabWindow(urls: String, context: Context) {
        val uris = Uri.parse(urls)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }

}
