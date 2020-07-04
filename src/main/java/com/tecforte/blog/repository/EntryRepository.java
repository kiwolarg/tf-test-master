package com.tecforte.blog.repository;
import com.tecforte.blog.domain.Blog;
import com.tecforte.blog.domain.Entry;
import com.tecforte.blog.service.BlogService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Entry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
	
//	@PersistenceContext
//	public static final EntityManagerFactory emf;
//	EntityManager entityManager = emf.createEntityManager();
//	final Logger log = LoggerFactory.getLogger(EntryRepository.class);

	@Query("select entry from Entry entry where entry.blog.id = ?1 and entry.content like %?2%")
    List<Entry> findByBlogIdAndKeywords(Long blogId, String keywords0);
	
	@Query("select entry from Entry entry where entry.content like %?1% ")
    List<Entry> findByKeyword(String keywords);
	
//	building custom native query
//  @SuppressWarnings("unchecked")
//	public static List<Entry> findByKeywords(String[] keywords){
//    	
//    	StringBuilder qry = new StringBuilder();
//    	qry.append("select * from entry where content LIKE ");
//    	for(int i = 0; i < keywords.length; i++) {
//    		if(i == 0) {
//    			qry.append("%"+keywords[i]);
//    		}else if(i > 0 ) {
//    			qry.append(" OR content LIKE %?"+keywords[i]);
//    		}
//    	}
//    	
//    	log.debug(qry.toString());
//    	
//    	Query q = (Query) entityManager.createNativeQuery(qry.toString());
//
//        List<Entry> entries = ((javax.persistence.Query) q).getResultList();
//        
//        return entries;
//    }
	
}
