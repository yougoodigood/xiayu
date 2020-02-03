package com.xiayu.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class XiayuDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

}
