package com.studymate.features;

import com.studymate.BaseIntegrationTest;
import org.junit.Test;

public class TypicalScenarioUserAccessEducationalContentIntegrationTest extends BaseIntegrationTest {

    @Test
    public void userWantsToSeeEducationalContentButHasToBeLoggedInAndExternalServerShouldHaveSomeContent(){
        // step 1: there is no educational content in the database
        // step 2: user tries to retrieve JWT token by making a POST request to /token with username=someUser, password=somePassword and system returns UNAUTHORIZED(401)
        // step 3: user makes a GET request to /educational-content without JWT token and system returns UNAUTHORIZED(401)
        // step 4: user makes a POST request to /register with username=someUser, password=somePassword and system registers user and returns OK(200)
        // step 5: user retrieves JWT token by making a POST request to /token with username=someUser, password=somePassword and system returns OK(200) and jwttoken=AAAA.BBBB.CCC
        // step 6: user makes a GET request to /educational-content with header “Authorization: Bearer AAAA.BBBB.CCC” and system returns OK(200) with 0 educational contents
        // step 7: there are 2 new educational contents in the system
        // step 8: user makes a GET request to /educational-content with header “Authorization: Bearer AAAA.BBBB.CCC” and system returns OK(200) with 2 educational contents with ids: 1000 and 2000
        // step 9: user makes a GET request to /educational-content/9999 and system returns NOT_FOUND(404) with message “Content with id 9999 not found”
        // step 10: user makes a GET request to /educational-content/1000 and system returns OK(200) with content
        // step 11: there are 2 new educational contents in the system
        // step 12: user makes a GET request to /educational-content with header “Authorization: Bearer AAAA.BBBB.CCC” and system returns OK(200) with 4 educational contents with ids: 1000,2000, 3000 and 4000
    }
}
