package com.logstream.segmentation.segment;

import java.util.List;

public interface SegmentService {
    
    List<Segment> readByCustomerId(Long customerId);

    Segment create(Segment segment);

    void deleteById(Long segmentId);

    void deleteRelationSegmentsByCustomerId(Long customerId, List<String> slugs);

    void createRelationSegmentsByCustomerId(Long customerId, List<String> slugs);

} 