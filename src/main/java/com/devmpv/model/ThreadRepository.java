package com.devmpv.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ThreadRepository extends PagingAndSortingRepository<Thread, Serializable> {
	List<Thread> findByBoard(BoardName board);
}
