package com.cason.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jingle.huang on 2017/4/28.
 */
//public class DemoElasticJob extends AbstractSimpleElasticJob {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    public void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
//        logger.info("开job lo， do something---------------jobExecutionMultipleShardingContext :[{}]",jobExecutionMultipleShardingContext);
//    }
//}
public class DemoElasticJob implements SimpleJob {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("shardingContext------------------------");
    }
}
