Feature: Standard service status url's

  Scenario: Calling ping
    When a call to the ping url is done
    Then the response has status code 200

  Scenario: Calling dump
    When a call to the dump url is done
    Then the response has status code 200

  Scenario: Calling build
    When a call to the build url is done
    Then the response has status code 200

