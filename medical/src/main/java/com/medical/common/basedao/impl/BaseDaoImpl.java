package com.medical.common.basedao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.medical.common.basedao.BaseDao;
import com.medical.common.util.Page;

/**
 * @author 
 * 基本DAO类，hibernate增删改查操作的简单封装
 */
@Repository
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao{
    @Autowired
    protected DataSource  dynamicDataSource;
    protected JdbcTemplate jdbcTemplate;
    
    private static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	/**
	 * 供spring注入SessionFactory使用
	 * @param sessionFactory
	 */
	@SuppressWarnings("unused")
	@Autowired 
	private void setSessionFactory2(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
        jdbcTemplate = new JdbcTemplate(dynamicDataSource);
	}
    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#create(java.lang.Object)
     */
    public Serializable create(final Object entity) {
       return getHibernateTemplate().save(entity);
    }

    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#delete(java.lang.Object)
     */
    public void delete(final Object entity) {
       getHibernateTemplate().delete(entity);
    }

    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#update(java.lang.Object)
     */
    public void update(final Object entity) {
       getHibernateTemplate().update(entity);
    }
    
	
	public void merge(Object o){
		this.getHibernateTemplate().merge(o);
	}
   
    
    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getListByHQL(java.lang.String, java.lang.String, java.lang.Object[], com.xcysoft.udpf.core.table.Page)
     */
    public List<?> getListByHQL(final String selectCount, final String select, final Object[] values, final Page page) {
    	
    	if(selectCount != null && !selectCount.equals("")){
    		if(page != null){
	        	Long count = (Long)getObjectByHQL(selectCount, values);
	        	page.setTotal(count.intValue());
	        	if(page.isEmpty())
		            return Collections.EMPTY_LIST;
    		}
	        
    	}
        return getListByHQL(select, values, page);
    }

    /**
     * @param select
     * @param values
     * @param page
     * @return
     */
    private List<?> getListByHQL(final String select, final Object[] values, final Page page) {
    	// select:
    	HibernateCallback selectCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) {
    			Query query = session.createQuery(select);
    			if(values!=null) {
    				for(int i=0; i<values.length; i++)
    					query.setParameter(i, values[i]);
    			}
    			if(page != null){
    				return query.setFirstResult(page.getFirstResult())
    				.setMaxResults(page.getRp())
    				.list();
    			}else
    				return query.list();
    		}
    	};
    	List<?> list = (List<?>) getHibernateTemplate().executeFind(selectCallback);
    	
    	return list;
    }
    
    /**
     * @param select
     * @param values
     * @param page
     * @return
     */
    public List<?> getListByHQLAsCache(final String select, final Object[] values) {
        return this.getListByHQLAsCache(select, values, null);
    }
    
    /**
     * @param select
     * @param values
     * @param page
     * @return
     */
    private List<?> getListByHQLAsCache(final String select, final Object[] values, final Page page) {
        // select:
        HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                query.setCacheable(true);
                if(values!=null) {
                    for(int i=0; i<values.length; i++)
                        query.setParameter(i, values[i]);
                }
                if(page != null){
                    return query.setFirstResult(page.getFirstResult())
                    .setMaxResults(page.getRp())
                    .list();
                }else
                    return query.list();
            }
        };
        List<?> list = (List<?>) getHibernateTemplate().executeFind(selectCallback);
        
        return list;
    }

    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getListByHQL(java.lang.String, java.lang.Object[])
     */
    public List<?> getListByHQL(final String select, final Object[] values) {
    	return this.getListByHQL(select, values, null);
    }

    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getObjectByHQL(java.lang.String, java.lang.Object[])
     */
    public Object getObjectByHQL(final String select, final Object[] values) {
        HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                if(values!=null) {
                    for(int i=0; i<values.length; i++)
                        query.setParameter(i, values[i]);
                }
                return query.uniqueResult();
            }
        };
        return getHibernateTemplate().execute(selectCallback);
    }
    
    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getObjectByHQL(java.lang.String, java.lang.Object[])
     */
    public Object getObjectByHQLAsCache(final String select, final Object[] values) {
        HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(select);
                query.setCacheable(true);
                if(values!=null) {
                    for(int i=0; i<values.length; i++)
                        query.setParameter(i, values[i]);
                }
                return query.uniqueResult();
            }
        };
        return getHibernateTemplate().execute(selectCallback);
    }


    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getObjectByCriteria(org.hibernate.criterion.DetachedCriteria)
     */
    public Object getObjectByCriteria(final DetachedCriteria dc) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
            	Criteria criteria = dc.getExecutableCriteria(session); 
            	//criteria.setProjection(Projections.rowCount());
                return criteria.uniqueResult();
            }
        };
        return getHibernateTemplate().execute(callback);
    }

    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getListByCriteria(org.hibernate.criterion.DetachedCriteria, java.lang.String, com.xcysoft.udpf.core.table.Page)
     */
    public List<?> getListByCriteria(final DetachedCriteria dc,final String orderBy, final Page page) {
    	if(page !=null){
    		dc.setProjection(Projections.rowCount());
    		int i = Integer.parseInt(this.getObjectByCriteria(dc).toString());
	    	page.setTotal(i);
	        if(page.isEmpty())
	            return Collections.EMPTY_LIST;
    	}
    	//处理排序字段
    	if(orderBy != null){
    		String[] aOrderBys = orderBy.trim().split(",");
    		for(int i = 0; i< aOrderBys.length; i++){
    			String[] aOrderBy = aOrderBys[i].trim().split(" ");
    			//字段为空则不处理
    			if(StringUtils.isBlank(aOrderBy[0]))
    				continue;
    			
    			if(aOrderBy.length>1){//有正反序参数
    				if(aOrderBy[1].equalsIgnoreCase("desc")){
    					dc.addOrder(Property.forName(aOrderBy[0]).desc());
    				}else{
    					dc.addOrder(Property.forName(aOrderBy[0]).asc());
    				}
    			}else{//无正反序参数
    				dc.addOrder(Property.forName(aOrderBy[0]).asc());
    			}
    		}
    	}
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
            	Criteria criteria = dc.getExecutableCriteria(session);
            	criteria.setProjection(null);
            	if(page !=null){
            	criteria.setFirstResult(page.getFirstResult())
	            	.setMaxResults(page.getRp())
	            	.setFetchSize(page.getRp());
            	}
                return criteria.list();
            }
        };
        List<?> list = (List<?>)getHibernateTemplate().execute(callback); 
        return list;
    }


    //SQL查询开始
    
    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getListBySQL(java.lang.String, java.lang.String, java.lang.Object[], com.xcysoft.udpf.core.table.Page, java.lang.Class)
     */
    public List<?> getListBySQL(final String selectCount, final String select, Object[] values, final Page page,final Class<?> c) {
    	if(selectCount != null && !selectCount.equals("") && page != null){
	        String count = getObjectBySQL(selectCount,values).toString();
	        page.setTotal(Integer.parseInt(count));
	        if(page.isEmpty())
	            return Collections.EMPTY_LIST;
    	}
        return getListBySQL(select, values, page,c);
    }
   
    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getListBySQL(java.lang.String, java.lang.Object[], java.lang.Class)
     */
    public List<?> getListBySQL(final String select,final Object[] values,final Class<?> c) {
        return getListBySQL(select, values, null,c);
        
    }

    /**
     * 根据指定SQL和已经初始化的Page对象，查询返回列表
     * @param select
     * @param values
     * @param page
     * @param c
     * @return
     */
    private List<?> getListBySQL(final String select,final Object[] values ,final Page page, final Class<?> c) {
    	// select:
    	HibernateCallback selectCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) {
    			Query query ;
    			if(c == null)
    				query = session.createSQLQuery(select);
    			else{
    				Map<?, ?> map = session.getSessionFactory().getAllClassMetadata();
    				if(map.containsKey(c.getName())){
    					query = session.createSQLQuery(select).addEntity(c);
    				}else{
    					query = session.createSQLQuery(select).setResultTransformer(Transformers.aliasToBean(c));
    				}
    			}
    			if(values!=null) {
    				for(int i=0; i<values.length; i++)
    					query.setParameter(i, values[i]);
    			}
    			if(page != null){
    				return query.setFirstResult(page.getFirstResult())
    				.setMaxResults(page.getRp())
    				.list();
    			}else
    				return query.list();
    		}
    	};
    	return (List<?>) getHibernateTemplate().executeFind(selectCallback);
    }
  

    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getObjectBySQL(java.lang.String, java.lang.Object[])
     */
    public Object getObjectBySQL(final String select,final Object[] values) {
        HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createSQLQuery(select);
                if(values!=null) {
    				for(int i=0; i<values.length; i++)
    					query.setParameter(i, values[i]);
    			}
                return query.uniqueResult();
            }
        };
        return getHibernateTemplate().execute(selectCallback);
    }
    

    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getObjectById(java.lang.Class, java.io.Serializable)
     */
    public Object getObjectById(final Class<?> c,final Serializable id) {
       return getHibernateTemplate().get(c, id);
    }
    
    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#updateBySql(java.lang.String, java.lang.Object[])
     */
    public Object updateBySql(final String sqlUpdate,final Object[] values) {
        HibernateCallback selectCallback = new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = session.createSQLQuery(sqlUpdate);
                if(values!=null) {
    				for(int i=0; i<values.length; i++)
    					query.setParameter(i, values[i]);
    			}
                return new Integer(query.executeUpdate());
            }
        };
        return getHibernateTemplate().execute(selectCallback);
    }
    /* (non-Javadoc)
     * @see com.xcysoft.udpf.core.dao.BaseDAO#getTopListByHQL(java.lang.String, java.lang.Object[], int)
     */
    public List<?> getTopListByHQL(final String select, final Object[] values, final int topnum) {
    	// select:
    	HibernateCallback selectCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) {
    			Query query = session.createQuery(select);
    			if(values!=null) {
    				for(int i=0; i<values.length; i++)
    					query.setParameter(i, values[i]);
    			}
    			if(topnum != -1){
					return query.setFirstResult(0)
					.setMaxResults(topnum)
					.list();
    			}else{
    				return query.list();
    			}
    		}
    	};

    	List<?> list = (List<?>) getHibernateTemplate().executeFind(selectCallback);
    	
    	return list;
    }
    public String getSingleValue(String sqlString) {
        String rst=null;
        try {
            rst=jdbcTemplate.queryForObject(sqlString, String.class);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
        return rst;
    }
	@Override
	public Object jdbcTemplateExcute(CallableStatementCreator csc,
			CallableStatementCallback<?> action) throws DataAccessException {
		return jdbcTemplate.execute(csc, action);
	}
	@Override
	public List<?> excuteProcedure(final String sql){
		log.info("Sql语句:" + sql);
		List<?> list = (List<?>) jdbcTemplate.execute(sql,
				new CallableStatementCallback<Object>() {
					public Object doInCallableStatement(CallableStatement cs)
							throws SQLException, DataAccessException {
						cs.registerOutParameter(1, OracleTypes.CURSOR);
						cs.execute();
						ResultSet rs = (ResultSet) cs.getObject(1);
						List<?> recordsList = getResultSet(rs);
						return recordsList;
					}
				});
		 
//		List list = (List) jdbcTemplate.execute(new CallableStatementCreator() {
//			public CallableStatement createCallableStatement(Connection conn)
//					throws SQLException {
//				CallableStatement cs = conn.prepareCall(sql);
//				cs.registerOutParameter(1, OracleTypes.CURSOR);
//				return cs;
//			}
//		}, new CallableStatementCallback() {
//			public Object doInCallableStatement(CallableStatement cs)
//					throws SQLException, DataAccessException {
//				cs.execute();
//				ResultSet rs = (ResultSet) cs.getObject(1);
//				List recordsList = getResultSet(rs);
//				return recordsList;
//			}
//		});
		return list;
	}
	private  List<Map<String, Object>> getResultSet(ResultSet rs)throws  SQLException{  
        List<Map<String, Object>> list = new  ArrayList<Map<String, Object>>();  
        try  {  
            ResultSetMetaData rsmd = rs.getMetaData();  
             //每循环一次遍历出来1条记录，记录对应的所有列值存放在map中(columnName:columnValue)   
            while (rs.next()){  
                Map<String, Object> map = new  HashMap<String, Object>();   
                int  columnCount = rsmd.getColumnCount();  
                for (int i=0 ;i<columnCount;i++){  
                    String columnName = rsmd.getColumnName(i+1 );  
                    map.put(columnName, rs.getObject(i+1 ));  
                }  
                list.add(map);  
            }  
        } catch  (SQLException e) {  
        	log.error(e.getMessage(), e);
        }  
        return  list;  
    }  
	@Override
	public List<Map<String, Object>> jdbcTemplateQureyForList(String sqlStr)  throws RuntimeException{
		log.debug("@Sql语句:" + sqlStr);
		return jdbcTemplate.queryForList(sqlStr);
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
		log.debug("@Sql语句:" + sql);
		return jdbcTemplate.queryForObject(sql, requiredType, args);
	}
}