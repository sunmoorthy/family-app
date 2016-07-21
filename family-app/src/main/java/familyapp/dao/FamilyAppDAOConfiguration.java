package familyapp.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;

@Configuration
public class FamilyAppDAOConfiguration {

	public @Bean MongoDbFactory getMongoDbFactory() throws Exception{
		//"172.31.35.67"
		return new SimpleMongoDbFactory(new MongoClient("localhost" , 27017) , "familyapp");
	}
	
	@SuppressWarnings("deprecation")
	public @Bean MongoTemplate getMongoTemplate() throws Exception{
		MongoTemplate template = new MongoTemplate(getMongoDbFactory());
		
		MappingMongoConverter converter = 
			new MappingMongoConverter(getMongoDbFactory(), new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		MongoTemplate mongoTemplate = new MongoTemplate(getMongoDbFactory(), converter);
		return mongoTemplate;
	}

}
