package com.cason.demo.job;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jingle.huang on 2017/4/28.
 */
public class DemoElasticJob extends AbstractSimpleElasticJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
        logger.info("开job lo， do something---------------jobExecutionMultipleShardingContext :[{}]",jobExecutionMultipleShardingContext);
    }
}
