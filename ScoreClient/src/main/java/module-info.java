module ScoreClient {
    requires Common; // Requires the Common module for IScoreService
    requires spring.context;
    requires spring.web; // Requires Spring Web for RestTemplate
    requires com.fasterxml.jackson.databind; // Requires Jackson for JSON serialization/deserialization

    // Provides the IScoreService implementation to other modules via ServiceLoader
    //provides dk.sdu.mmmi.cbse.common.services.IScoreService with dk.sdu.mmmi.cbse.scoreclient.ScoreServiceClient;

    // Exports the package if other modules need to directly access classes within ScoreClient
    // (though typically, they would use the IScoreService interface via ServiceLoader)
    exports dk.sdu.mmmi.cbse.scoreclient;
}