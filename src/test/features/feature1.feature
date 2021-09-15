@Test
 Feature: Google
 Background:
 Given user navigates to "https://www.google.com"
 Scenario: type stuff in
 When user sees "defaultText" element on "feature1" page
 When user types "Hello" into "defaultText" textbox on "feature1" page
 When user clicks on "defaultButton" button on "feature1" page