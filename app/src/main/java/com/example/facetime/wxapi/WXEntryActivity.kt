package com.example.facetime.wxapi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory


class WXEntryActivity: IWXAPIEventHandler, AppCompatActivity() {
    var result = ""
    lateinit var api: IWXAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this, "wxa734d668789e6b82", false)
        api.registerApp("wxa734d668789e6b82")
        //不写没有回调
        api.handleIntent(intent, this)
    }

    override fun onResp(resp: BaseResp?) {
        result = when (resp?.errCode) {
            BaseResp.ErrCode.ERR_OK -> "wx-----分享成功"
            BaseResp.ErrCode.ERR_USER_CANCEL -> "wx-----分享取消"
            BaseResp.ErrCode.ERR_AUTH_DENIED -> "wx-----分享被拒绝"
            else -> "wx-----分享返回"
        }
        Toast.makeText(this@WXEntryActivity, result, Toast.LENGTH_LONG).show()
    }

    override fun onReq(p0: BaseReq?) {

    }


}