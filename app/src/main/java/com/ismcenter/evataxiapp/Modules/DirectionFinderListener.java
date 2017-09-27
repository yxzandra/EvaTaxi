package com.ismcenter.evataxiapp.Modules;

import java.io.IOException;
import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route) throws IOException;
}
