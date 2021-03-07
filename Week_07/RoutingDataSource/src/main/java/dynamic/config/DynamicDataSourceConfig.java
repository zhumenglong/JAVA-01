package dynamic.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {

    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    private static final String ENTITY_PACKAGE = "dynamic.entity";

    @Primary
    @Bean(name = "WriteDS")
    @ConfigurationProperties("spring.datasource.druid.write")
    public DataSource writeDS() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "ReadDS")
    @ConfigurationProperties("spring.datasource.druid.read")
    public DataSource readDS() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource(DataSource writeDS, DataSource readDS) {
        Map<Object, Object> targetDataSources = new HashMap<>(5);
        targetDataSources.put("ReadDS", readDS);
        return new DynamicDataSource(writeDS, targetDataSources);
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DynamicDataSourceConfig.MAPPER_LOCATION));
        sqlSessionFactoryBean.setTypeAliasesPackage(ENTITY_PACKAGE);
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

}
