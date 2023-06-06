package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.create;

import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.create.Segment;

import java.util.List;

public class SegmentMother {

    public static final Segment SEGMENT = Segment.builder().withSections(List.of(SectionMother.SECTION)).build();

}
