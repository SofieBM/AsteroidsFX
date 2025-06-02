module ScoreClient
{
    requires Common;
    requires spring.context;
    requires spring.web;
    requires com.fasterxml.jackson.databind;
    //provides dk.sdu.mmmi.cbse.common.services.IScoreService with dk.sdu.mmmi.cbse.scoreclient.ScoreServiceClient;
    exports dk.sdu.mmmi.cbse.scoreclient;
}