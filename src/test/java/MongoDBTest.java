import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryOperators;

/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月2日
 * 文件说明: 
 */

public class MongoDBTest {
	private Mongo mg =null;
	private DB db;
	private DBCollection users;
	@Before
	public void init(){
		try{
			mg =new Mongo("192.168.10.139");
			
		}catch(UnknownHostException e){
			e.printStackTrace();
		}catch(MongoException e){
			e.printStackTrace();
		}
		
		db=mg.getDB("temp");
		users=db.getCollection("users");
	}
	@After
	public void destory(){
		if(mg!=null)
			mg.close();
		mg=null;
		db=null;
		users=null;
		System.gc();
	}
	
	public void print(Object o){
		System.out.println(o);
	}
	public void queryAll(){
		print("查询users的所有数据");
		DBCursor cur =users.find();
		while(cur.hasNext()){
			print(cur.next());
		}
	}
	
	@Test
	public void add(){
		queryAll();
		print("count:"+users.count());
		DBObject user =new BasicDBObject();
		user.put("name","hoojo");
		user.put("age",25);
		
		
		user.put("sex","男");
	print("save:"+users.save(user).getN());
		
		print("insert:"+users.insert(user,new BasicDBObject("name","tom")).getN());
		
		List<DBObject> list =new ArrayList<DBObject>();
		user.put("name","hi");
		list.add(user);
		DBObject user2=new BasicDBObject("name","lucy");
		list.add(user2);
		
		//print("insert list:"+users.insert(list).getN());
		print("count:"+users.count());
		queryAll();
		users.drop();
	}	
	@Test
	public void remove(){
		queryAll();
		print("删除id =4de :"+users.remove(new BasicDBObject("_id",new ObjectId("565e9a29a2df949942a45ab7"))).getN());
		print("remove age>=24:"+users.remove(new BasicDBObject("_id",new BasicDBObject("$gt",24))).getN());
	}
	@Test
	public void modify(){
		print("修改:"+users.update(new BasicDBObject("_id",new ObjectId("565e9a29a2df949942a45ab7")),
				new BasicDBObject("age",99)
				).getN());
		print("修改:"+users.update(new BasicDBObject("_id",new ObjectId("565e9a29a2df949942a45ab7")),
				new BasicDBObject("age",121),
				true,//if db not exsit whether add
				false//modify mutil 
				).getN());
		
		print("修改:"+users.update(new BasicDBObject("name","haha"),
				new BasicDBObject("name","dingding"),
				true,//if db not exsit whether add
				true//modify mutil  true:if has mutil data dos't modify
				).getN());
	}
	@Test
	public void query(){
		print("find id= 4de:"+users.find(new BasicDBObject("_id",new ObjectId("565e9a29a2df949942a45ab7"))).toArray());
		
		print("find age= 24:"+users.find(new BasicDBObject("age",24)).toArray());
		
		
		print("find age>= 24:"+users.find(new BasicDBObject("age",new BasicDBObject("$gte",24))).toArray());
		
		print("find age<= 24:"+users.find(new BasicDBObject("age",new BasicDBObject("$lte",24))).toArray());
		
		print("find age!= 25:"+users.find(new BasicDBObject("age",new BasicDBObject("$ne",24))).toArray());
		
		
		print("find age in 25 26 27:"+users.find(new BasicDBObject("age",new BasicDBObject(QueryOperators.IN,new int[]{25,26,27}))).toArray());
		
		print("find age in 25 26 27:"+users.find(new BasicDBObject("age",new BasicDBObject(QueryOperators.IN,new int[]{25,26,27}))).toArray());
	}
}
