package com.tecforte.blog.service;

import com.tecforte.blog.domain.Entry;
import com.tecforte.blog.repository.BlogRepository;
import com.tecforte.blog.repository.EntryRepository;
import com.tecforte.blog.service.dto.BlogDTO;
import com.tecforte.blog.service.dto.EntryDTO;
import com.tecforte.blog.service.mapper.BlogMapper;
import com.tecforte.blog.service.mapper.EntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Entry}.
 */
@Service
@Transactional
public class EntryService {

    private final Logger log = LoggerFactory.getLogger(EntryService.class);

    private final EntryRepository entryRepository;

    private final EntryMapper entryMapper;
    
    private  BlogRepository blogRepository;

    private  BlogMapper blogMapper;

    public EntryService(EntryRepository entryRepository, EntryMapper entryMapper) {
        this.entryRepository = entryRepository;
        this.entryMapper = entryMapper;
    }
    
    BlogService blogService = new BlogService(blogRepository, blogMapper);

    /**
     * Save a entry.
     *
     * @param entryDTO the entity to save.
     * @return the persisted entity.
     */
    public EntryDTO save(EntryDTO entryDTO) {
        log.debug("Request to save Entry : {}", entryDTO);
        Entry entry = entryMapper.toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return entryMapper.toDto(entry);
    }

    /**
     * Get all the entries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entries");
        return entryRepository.findAll(pageable)
            .map(entryMapper::toDto);
    }
    
    /**
     * Delete all matched entries.
     */
    public void findAllToClean(String[] keywords) {
        log.debug("Request to clean all Entry");
        
        List<Entry>  entry = new ArrayList<Entry>();
        
        for(String param: keywords) {
        	entry.addAll(entryRepository.findByKeyword(param));
        }
        
        for(Entry singleEntry: entry) {
        	entryRepository.deleteById(singleEntry.getId());
        }
    }
    
    /**
     * Delete matched entry.
     */
    public void findAllByBlogIdAndKeywordsToClean(Long blogId, String[] keywords) {
        log.debug("Request to clean Entry by Blog Id and Keywords");
        
        List<Entry>  entry = new ArrayList<Entry>();
        
        for(String param: keywords) {
        	entry.addAll(entryRepository.findByBlogIdAndKeywords(blogId,param));
        }
        
        for(Entry singleEntry: entry) {
        	entryRepository.deleteById(singleEntry.getId());
        }
    }


    /**
     * Get one entry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntryDTO> findOne(Long id) {
        log.debug("Request to get Entry : {}", id);
        return entryRepository.findById(id)
            .map(entryMapper::toDto);
    }

    /**
     * Delete the entry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entry : {}", id);
        entryRepository.deleteById(id);
    }
}
