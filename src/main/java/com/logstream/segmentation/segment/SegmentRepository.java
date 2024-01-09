package com.logstream.segmentation.segment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
    @org.springframework.data.jdbc.repository.query.Query(
        """
        SELECT s
        FROM segment s
        LEFT JOIN (SELECT cs FROM customer_segment cs where cs.customer_id = :customerId) c
        WHERE s.id = c.segment_id
        """
    )
    List<Segment> readByCustomers_Id(@Param("customerId") Long customerId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM customer_segment cs \n"
        + "WHERE cs.customer_id = :customerId \n"
        + "AND cs.segment_id = :segmentId",
        nativeQuery=true)
    void removeRelationSegmentByCustomerId(
        @Param("customerId") Long customerId,
        @Param("segmentId") Long segmentId);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO customer_segment (customer_id, segment_id) \n"
        + "VALUES (:customerId, :segmentId)",
        nativeQuery=true
    )
    void createRelationSegmentsByCustomerId(
        @Param("customerId") Long customerId,
        @Param("segmentId") Long segmentId);


    Segment findBySlug(String slug);
} 