package com.client.shop

import android.app.Application
import com.client.shop.di.component.AppComponent
import com.client.shop.di.component.DaggerAppComponent
import com.client.shop.di.module.RepositoryModule
import com.daocore.DaoImpl
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.shopify.api.ShopifyApi
import io.reactivex.plugins.RxJavaPlugins


class ShopApplication : Application() {

    companion object {
        private const val BASE_URL = "xpohstore.myshopify.com"
        private const val ACCESS_TOKEN = "afc80014a08846feaf590e1db92e74b6"

        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        val api = ShopifyApi(this, BASE_URL, ACCESS_TOKEN)
        val dao = DaoImpl(this)

        appComponent = DaggerAppComponent.builder()
                .repositoryModule(RepositoryModule(api, dao))
                .build()

        val config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, config)

        RxJavaPlugins.setErrorHandler { e -> e.printStackTrace() }
    }
}