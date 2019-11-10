package com.mangobits.fitco.reports

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mangobits.fitco.R
import com.mangobits.fitco.app.App.Companion.user
import com.mangobits.startupkit.core.service.JsonContainer
import com.mangobits.startupkit.core.service.RestServiceListener
import kotlinx.android.synthetic.main.weekly_reports.*
import mangobits.startupkit.core.util.MessageAlertDialog
import java.util.ArrayList

class WeeklyReportsActivity : AppCompatActivity(), ReportAdapter.ClickreportListener {


    var mAdapter: ReportAdapter? = null

    var mLayoutManager: RecyclerView.LayoutManager? = null

    var recyclerView: RecyclerView? = null

    var listReport = ArrayList<Report>()


    var loading: Boolean? = null
    var page: Int = 1

    var swipe: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weekly_reports)


        recyclerView = findViewById<RecyclerView>(R.id.mRecyclerReports)

        mLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = mLayoutManager


        mAdapter = ReportAdapter(this, ArrayList<Report>())
        mAdapter!!.setClickReportListener(this)

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


    fun loadService() {

        loading = true




        ReportService(this).listReports(page.toString(), object : RestServiceListener<Array<Report>> {
            override fun processCallback(resultado: JsonContainer, dados: Array<Report>?) {

                if (resultado.success!!) {
                    if (dados != null) {

                        if (page == 1) {
                            recyclerView!!.adapter = mAdapter
                            listReport = dados.toCollection(ArrayList())
                            recyclerView!!.scrollToPosition(0)
                            mAdapter!!.animateTo(listReport)
                        } else {
                            listReport.addAll(dados.toCollection(ArrayList()))
                            mAdapter!!.setListItens(listReport)
                            mAdapter!!.updateItems()
                        }


                        loadData()
                    }
                } else {
                    val id = resources.getIdentifier(resultado.desc, "string", packageName)
                    val value = if (id == 0) resultado.desc else resources.getText(id) as String
                    val alert = MessageAlertDialog.createMsgDialog(this@WeeklyReportsActivity, getText(R.string.validation).toString(), value!!).show()
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

        if (listReport.count() > 0) {
            loading = false

            lblFeedEmpty!!.visibility = View.GONE
            recyclerView!!.visibility = View.VISIBLE


        } else {
            lblFeedEmpty!!.visibility = View.VISIBLE
            recyclerView!!.visibility = View.INVISIBLE


        }
    }

    override fun clickReport(view: View, position: Int) {
        val report = listReport!!.get(position)

        val intent = Intent(this@WeeklyReportsActivity, WeeklyReportDetailActivity::class.java)
        intent.putExtra("report", report)
        startActivity(intent)
    }

}
