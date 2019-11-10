package com.mangobits.fitco.reports

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import com.mangobits.fitco.R
import kotlinx.android.synthetic.main.weekly_report_detail.*

class WeeklyReportDetailActivity : AppCompatActivity() {

    var report: Report? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weekly_report_detail)


        report = intent.getSerializableExtra("report") as? Report

        txtPost.text = report!!.descriptionEn
        val imagePipeline = Fresco.getImagePipeline()
        imagePipeline.clearMemoryCaches()
        imagePipeline.clearDiskCaches()
        imagePipeline.clearCaches()
        ReportService(this).image(imgPost, report!!.id!!)
//        ReportService(cv.context).image(imgReport, report.id!!)

        txtTitle.text = report!!.titleEn


    }
}
