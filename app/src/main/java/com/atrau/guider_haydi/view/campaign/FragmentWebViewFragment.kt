package com.atrau.guider_haydi.view.campaign


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.atrau.guider_haydi.R
import com.atrau.guider_haydi.view.HomeActivity
import kotlinx.android.synthetic.main.fragment_fragment_web_view.*


class FragmentWebViewFragment : Fragment() {
    companion object {
        val newFragment = FragmentWebViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {

        (activity as HomeActivity).setSupportActionBar(toolbar_web_view)
        (activity as HomeActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
        txt_title_webview.text = (activity as HomeActivity).campaign.name
        toolbar_web_view.navigationIcon = (activity as HomeActivity).resources.getDrawable(
            R.drawable.ic_arrow_back_black_24dp,
            (activity as HomeActivity).theme
        )

        toolbar_web_view.setNavigationOnClickListener {
            (activity as HomeActivity).popbacktask()
        }
        (activity as HomeActivity).title = "Name campaign"

        webView.webViewClient = WebView()
        webView.webViewClient = com.atrau.guider_haydi.view.campaign.WebView()
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.loadUrl("https://baomoi.com/")


    }


    inner class WebView : WebViewClient() {

        fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.onLoadResource(webView, url) // load the url
            return true
        }


    }

}
