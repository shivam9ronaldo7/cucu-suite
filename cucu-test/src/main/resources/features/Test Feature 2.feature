#Author: shivam9ronaldo7@gmail.com

@test
Feature: Title of your feature2
  I want to use this template for my feature file

  @tag1.1
  Scenario: Title of your scenario2.1
    Given I want to write a step with precondition
    And some other precondition
    When I complete action
    And some other action
    And yet another action
    Then I validate the outcomes
    And check more outcomes

  @tag1.2
  Scenario Outline: Title of your scenario outline2.2
    Given I want to write a step with <name>
    When I check for the <value> in step
    Then I verify the <status> in step
    Examples: 
      | name  | value | status  |
      | name1 |     5 | success |
      | name2 |     7 | Fail    |

  Scenario: Placeholder
    Given user adds below placeholder
    """json-placeholder-data
    [
      {
        "key": "numeric",
        "type": "random",
        "details": {
          "length": 9,
          "type": "numeric"
        }
      },
      {
        "key": "alphanumeric",
        "type": "random",
        "details": {
          "length": 9,
          "type": "alphanumeric"
        }
      },
      {
        "key": "alphanumeric",
        "type": "random",
        "details": {
          "length": 9,
          "type": "alphabetic"
        }
      },
      {
        "key": "uuid",
        "type": "random",
        "details": {
          "type": "uuid"
        }
      },
      {
        "key": "timestamp",
        "type": "timestamp",
        "details": {
          "format": "yyyy-MMM-dd",
          "timezone": "Asia/Kolkata",
          "timeStampOffsetDetails": {
            "unit": "hours",
            "value": -2
          }
        }
      },
      {
        "key": "replace-placeholder",
        "type": "replace",
        "details": {
          "text": "SHIV-:uuid:"
        }
      }
    ]
    """