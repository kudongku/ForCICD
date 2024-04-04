package com.six.cicd_setting.config;

import static com.six.cicd_setting.constant.AppConstant.PRIMARY;
import static com.six.cicd_setting.constant.AppConstant.SECONDARY;
import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = isCurrentTransactionReadOnly() ? SECONDARY : PRIMARY;
        log.info(">>>>>> current data source : {}", dataSourceName);
        return dataSourceName;
    }
}
