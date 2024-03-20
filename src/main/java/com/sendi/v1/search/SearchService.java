package com.sendi.v1.search;

import org.springframework.stereotype.Service;

public interface SearchService {
    SearchResponse search(String query);
}
