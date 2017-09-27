package com.ismcenter.evataxiapp.Interfaces;

import com.ismcenter.evataxiapp.Modelos.Request.ResponseNearbyService;

/**
 * Created by yxzan on 10/30/2016.
 */

public class AcceptService {

    public interface OnRecyclerItemClickedListener{
        public void onRecyclerItemClicked(ResponseNearbyService clickService);
    }

}