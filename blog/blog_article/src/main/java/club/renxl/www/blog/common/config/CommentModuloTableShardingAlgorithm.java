package club.renxl.www.blog.common.config;
import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by wuwf on 17/4/19.
 */
//1,3,5,7,9 11,13,15 x = 1+2(n-1)  奇数1库，n奇数1表
//2,4,6,8,10 y =2+2(n-1)           偶数2库，n偶数2表
//3个库 123，456，789   数字/数据库个数= 路由地址（余数）
//     数据库个数 3
      //%3  120  120  120// 库
//    // /3 001  112  223  334除数 
//      %2  表个数
//      001  110 001       表      //除数 /表个数为数据表的地址
//除的整数除以表个数=表地址
public final class CommentModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {

    /**
     *  select * from t_order from t_order where order_id = 11
     *          └── SELECT *  FROM t_order_1 WHERE order_id = 11
     *  select * from t_order from t_order where order_id = 44
     *          └── SELECT *  FROM t_order_0 WHERE order_id = 44
     */
    public String doEqualSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        for (String each : tableNames) {
        	// 1，2 ==》1，3 5 7 9
            if (each.endsWith((shardingValue.getValue() / 2)%2 + "")) {
                 return each;
            }
        }
        throw new IllegalArgumentException();
    }
    public static void main(String[] args) {
		System.out.println("0".endsWith((1L/ 2)%2 + ""));
		System.out.println((1 / 2)%2 + "");
	}

    /**
     *  select * from t_order from t_order where order_id in (11,44)
     *          ├── SELECT *  FROM t_order_0 WHERE order_id IN (11,44)
     *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,44)
     *  select * from t_order from t_order where order_id in (11,13,15)
     *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,13,15)
     *  select * from t_order from t_order where order_id in (22,24,26)
     *          └──SELECT *  FROM t_order_0 WHERE order_id IN (22,24,26)
     */
    public Collection<String> doInSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith((value/ 2)%2+ "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    /**
     *  select * from t_order from t_order where order_id between 10 and 20
     *          ├── SELECT *  FROM t_order_0 WHERE order_id BETWEEN 10 AND 20
     *          └── SELECT *  FROM t_order_1 WHERE order_id BETWEEN 10 AND 20
     */
    public Collection<String> doBetweenSharding(final Collection<String> tableNames, final ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith((i/ 2)%2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}