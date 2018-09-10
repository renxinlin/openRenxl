package club.renxl.www.blog.common.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.mysql.jdbc.Driver;

@Configuration
public class DataSourceConfig {
	
	/**
	 * 64主键
	 * @return
	 */
	/*@Bean
	@Scope("singleton")//每次调用新建一个bean示例
    public IdGenerator getIdGenerator() {
        return new IdWorker (0,0);
    }*/
	public static void main(String[] args) {
		IdWorker idWorker = new IdWorker (0,0);
		int i = 0;
		while(i<100) {
			i++;
			System.out.println(idWorker.generateId().longValue());
		}
	}
    @Bean
    public DataSource getDataSource() {
        return buildDataSource();
    }

    private DataSource buildDataSource() {
        //设置分库映射
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>(2);
        //添加两个数据库ds_0,ds_1到map里
        dataSourceMap.put("article0", createDataSource("0"));
        dataSourceMap.put("article1", createDataSource("1"));
        // 设置默认db为ds_0，也就是为那些没有配置分库分表策略的指定的默认库
        // 如果只有一个库，也就是不需要分库的话，map里只放一个映射就行了，只有一个库时不需要指定默认库
        // ，但2个及以上时必须指定默认库，否则那些没有配置策略的表将无法操作数据
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap, "article0");

        //设置分表映射，将t_order_0和t_order_1两个实际的表映射到t_order逻辑表
        //0和1两个表是真实的表，t_order是个虚拟不存在的表，只是供使用。如查询所有数据就是select * from t_order就能查完0和1表的
        // 文章维度
        TableRule orderTableRule = TableRule.builder("axin_blogs_artitle")
                .actualTables(Arrays.asList("axin_blogs_artitle_0", "axin_blogs_artitle_1"))
                .databaseShardingStrategy(new DatabaseShardingStrategy("id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("id", new ModuloTableShardingAlgorithm())) 
                .dataSourceRule(dataSourceRule)
                .build();
        // 评论维度
        TableRule orderTableRule1 = TableRule.builder("axin_blogs_comment")
                .actualTables(Arrays.asList("axin_blogs_comment_0", "axin_blogs_comment_1"))
                .databaseShardingStrategy(new DatabaseShardingStrategy("topic_id", new CommentModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("topic_id", new CommentModuloTableShardingAlgorithm()))
                .dataSourceRule(dataSourceRule)
                .build();
         

        //具体分库分表策略，按什么规则来分
        ShardingRule shardingRule = ShardingRule.builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(orderTableRule, orderTableRule1)).build();

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);

        return dataSource;
    }

    
    private static DataSource createDataSource(final String dataSourceName) {
        //使用druid连接数据库
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName(Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://47.104.88.31:3306/axin20180716_%s?useUnicode=true&amp;characterEncoding=utf-8", dataSourceName));
        result.setUsername("root");
        result.setPassword("abcde12345");
        return result;
    }
}