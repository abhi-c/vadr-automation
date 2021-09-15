@MySite
 Feature: Automate My Site
 Background:
 Given user navigates to "http://www.sdlc.co/contact-us"
 Scenario: Fill out contact us form
 When user sees "NameText" element on "feature2" page
 Then user sees "EmailText" element on "feature2" page
 When user sees "MessageText" element on "feature2" page