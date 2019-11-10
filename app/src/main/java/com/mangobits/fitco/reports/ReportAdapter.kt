package com.mangobits.fitco.reports

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.mangobits.fitco.R
import kotlinx.android.synthetic.main.card_report.view.*
import mangobits.startupkit.core.util.TextUtils
import java.text.SimpleDateFormat


class ReportAdapter(private val context: Context, private var listItens: ArrayList<Report>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var clickReportListener: ClickreportListener? = null

    private var tamanhoAntigoLista = 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = listItens!![position]

        (holder as? ReportAdapter.ReportHolder)?.bindView(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var ph: RecyclerView.ViewHolder? = null


        val v = LayoutInflater.from(context).inflate(R.layout.card_report, parent, false)
        ph = ReportHolder(v)
        configClicks(v, ph)

        return ph
    }


    private fun configClicks(view: View, holder: ReportHolder) {


        holder.cv.setOnClickListener { v -> clickReportListener!!.clickReport(v, holder.adapterPosition) }
    }


    override fun getItemViewType(position: Int): Int {


        return 1
    }


    override fun getItemCount(): Int {

        return listItens!!.size
    }


    class ReportHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cv: CardView
        internal var imgReport: SimpleDraweeView
        internal var lblReport: TextView
        internal var lblCreationDate: TextView

        var report: Report? = null


        init {
            cv = itemView.findViewById(R.id.cvReport)
            imgReport = itemView.findViewById(R.id.imgPost)
            lblReport = itemView.findViewById(R.id.lblPost)
            lblCreationDate = itemView.findViewById(R.id.lblCreationDate)

        }


        fun bindView(report: Report) {

            this.report = report

            val formatter = SimpleDateFormat("MMM/yyyy")


            lblCreationDate.text = formatter.format(report.creationDate)


            lblReport.text = report.titleEn

            val urlServico = StringBuilder()

            urlServico.append("image/")

            urlServico.append(report.id)

            ReportService(cv.context).image(imgReport, report.id!!)
        }


    }


    fun setListItens(listItens: ArrayList<Report>) {

        if (this.listItens != null) {
            tamanhoAntigoLista = this.listItens!!.size
        }

        this.listItens = listItens
    }


    fun animateTo(models: List<Report>) {
        applyAndAnimateRemovals(models)
        applyAndAnimateAdditions(models)
        applyAndAnimateMovedItems(models)
    }


    fun removeItem(position: Int): Report {
        val model = listItens!!.removeAt(position)
        notifyItemRemoved(position)
        return model
    }

    fun addItem(position: Int, model: Report) {
        listItens!!.add(position, model)
        notifyItemInserted(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val model = listItens!!.removeAt(fromPosition)
        listItens!!.add(toPosition, model)
        notifyItemMoved(fromPosition, toPosition)
    }


    private fun applyAndAnimateRemovals(newModels: List<Report>) {
        for (i in listItens!!.indices.reversed()) {
            val model = listItens!![i]
            if (!newModels.contains(model)) {
                removeItem(i)
            }
        }
    }


    private fun applyAndAnimateAdditions(newModels: List<Report>) {
        var i = 0
        val count = newModels.size
        while (i < count) {
            val model = newModels[i]
            if (!listItens!!.contains(model)) {
                addItem(i, model)
            }
            i++
        }
    }

    private fun applyAndAnimateMovedItems(newModels: List<Report>) {
        for (toPosition in newModels.indices.reversed()) {
            val model = newModels[toPosition]
            val fromPosition = listItens!!.indexOf(model)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }


    fun updateItems() {

        notifyItemRangeInserted(tamanhoAntigoLista, listItens!!.size)
    }


    interface ClickreportListener {

        fun clickReport(view: View, position: Int)

    }

    fun setClickReportListener(clickReportListener: ClickreportListener) {

        this.clickReportListener = clickReportListener
    }

}
