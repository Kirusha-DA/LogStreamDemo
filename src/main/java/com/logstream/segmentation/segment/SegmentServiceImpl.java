package com.logstream.segmentation.segment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentServiceImpl implements SegmentService{
    
    private SegmentRepository segmentRepo;

    public SegmentServiceImpl(SegmentRepository segmentRepo) {
        this.segmentRepo = segmentRepo;
    }

    public List<Segment> readByCustomerId(Long customerId) {
        return segmentRepo.readByCustomers_Id(customerId);
    }

    public Segment create(Segment segment) {
        return segmentRepo.save(segment);
    } 

    public void deleteById(Long segmentId) {
        segmentRepo.deleteById(segmentId); 
    }

    public void deleteRelationSegmentsByCustomerId(Long customerId, List<String> slugs) {
        for (String slug: slugs) {
            Segment segment = segmentRepo.findBySlug(slug);
            segmentRepo.removeRelationSegmentByCustomerId(customerId, segment.getId());
        } 
    }

    public void createRelationSegmentsByCustomerId(Long customerId, List<String> slugs) {
        for (String slug: slugs) {
            Segment segment = segmentRepo.findBySlug(slug);
            segmentRepo.createRelationSegmentsByCustomerId(customerId, segment.getId());
        }
    }
}
