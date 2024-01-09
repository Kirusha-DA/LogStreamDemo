package com.logstream.segmentation.segment;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping(path="/api/segments", 
                produces="application/json")
@CrossOrigin(origins="http://segmentcloud:8080")
public class SegmentController {

    private final SegmentService segmentSrvc;
    
    public SegmentController(SegmentService segmentSrvc) {
        this.segmentSrvc = segmentSrvc;
    }

    @GetMapping(path="customers/{customerId}")
    public ResponseEntity<List<Segment>> readByCustomerId(@PathVariable("customerId") Long customerId) {
        List<Segment> segments = segmentSrvc.readByCustomerId(customerId);
        if (segments != null) {
            return ResponseEntity.ok(segments);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("customers/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void addSlugsToCustomer(
        @PathVariable("customerId") Long customerId,
        @RequestBody List<String> slugs) {
        log.info("SIZE: {}", customerId);
        segmentSrvc.createRelationSegmentsByCustomerId(customerId, slugs);
    }

    @DeleteMapping("customers/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSlugsFromUser(
        @PathVariable("customerId") Long customerId,
        @RequestParam("slug") List<String> slugs) {
        try {
            segmentSrvc.deleteRelationSegmentsByCustomerId(customerId, slugs);
        } catch (EmptyResultDataAccessException e) {}
    }
    
    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Segment> create(@RequestBody Segment segment) {
        Segment newSegment = segmentSrvc.create(segment);
        String newURI = String.format(
            "http://localhost:%d/api/segments/%d",
            8080, newSegment.getId()
        );

        return ResponseEntity.created(
            UriComponentsBuilder
            .fromUriString(newURI)
            .build()
            .toUri()
        ).build();
    }
    
    @DeleteMapping("/{segmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("segmentId") Long segmentId) {
        try {
            segmentSrvc.deleteById(segmentId);
        } catch (EmptyResultDataAccessException e) {}
    }

}
