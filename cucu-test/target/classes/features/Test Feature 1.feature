#Author: shivam9ronaldo7@gmail.com

@test
Feature: Title of your feature1
  I want to use this template for my feature file

  @ui
  Scenario Outline: Open webpage
    Given open "<name>"
    Then I verify the "<xpathlocator>" is loaded
    Examples:
      | name                      | xpathlocator                                     |
      | https://www.amazon.in/    | //span[text()='Hello, sign in']                  |
      | https://www.flipkart.com/ | //a[text()='New to Flipkart? Create an account'] |
      | https://www.ajio.com/     | //span[text()='Sign In / Join AJIO']             |
