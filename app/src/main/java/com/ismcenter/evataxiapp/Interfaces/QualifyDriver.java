package com.ismcenter.evataxiapp.Interfaces;

import com.ismcenter.evataxiapp.Modelos.Request.ResponseNearbyService;
import com.ismcenter.evataxiapp.Modelos.Response.ResponseListQualifiers;

/**
 * Created by yxzan on 10/30/2016.
 */

public class QualifyDriver {

    public interface OnRecyclerItemClickedListener{
        public void onRecyclerItemClicked(ResponseListQualifiers clickDriver);
    }

}