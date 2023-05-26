package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.status;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassMetadata;

import java.util.List;
import java.util.Optional;

public class BoardingPassIdMapper {

    public static void fillBoardingPassesWithId(List<BoardingPassMetadata> boardingPassesMetadata, List<BoardingPass> boardingPasses) {

        for (BoardingPassMetadata boardingPassMetadata : boardingPassesMetadata) {

            Optional<BoardingPass> matchedBoardingPass = boardingPasses.stream()
                    .filter(boardingPass -> boardingPass.providerPassengerSectionId().equals(boardingPassMetadata.providerPassengerSectionId()))
                    .findFirst();

            matchedBoardingPass.ifPresent(boardingPass -> boardingPass.setId(boardingPassMetadata.boardingPassId()));
        }

    }

}
