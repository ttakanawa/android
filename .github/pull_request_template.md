## Info
<!-- This template is a guideline, use your own judgement to write a description that -->
<!-- is easy to read and will help get the PR reviewed quickly and accurately -->

### Summary
<!-- MANDATORY: Describe clearly and concisely what this PR changes -->

### Technical considerations
<!-- OPTIONAL: Describe how the changes are implemented, list the different parts the changes consist of if it's more than one -->

### Why is it done like this and not some other way?
<!-- OPTIONAL: Are there other possible solutions that might seem more obvious? Tell us why you didn't go with those -->

### Relationships
<!-- MANDATORY: Mention any issues or PRs that are connected to this -->
<!-- DO NOT OPEN A PULL REQUEST THAT DOES NO CLOSE/REF AN ISSUE -->
Closes #

---
## Repository health

### Pull Request checklist
<!-- MANDATORY: Before submiting your Pull Request, make sure that -->
<!-- all the items below are verified and the boxes are checked -->
- [ ] The code does not hinder the app's performance
- [ ] The code does not introduce regressions
- [ ] The code in this PR is tested thoroughly enough that changing the behavior to an incorrect one breaks tests

## Reviewing

### Pointers
<!-- MANDATORY: Give pointers to help reviewers validate the changes, give a list of things that should be tested, show before/after screenshots, etc. -->

### Review template
<!-- MANDATORY: This is what reviewers should consider when reviewing your Pull request -->
<!-- Feel free to remove items that UI related if your code does not touch UI -->

<!-- General -->
- [ ] The code is thoroughly tested in well written and resilient manner
- [ ] The code is performant and leads to an optimal user experience
- [ ] The code is testable and does not cut corners for the sake of delivering
- [ ] The code considers older platforms well and will work in older devices just like it will in new ones
<!-- UI related -->
- [ ] The code considers light and dark theme
- [ ] The code considers RTL and does not mention left/right
- [ ] The code considers i18n
  - [ ] There are no hardcoded strings in the UI
  - [ ] All strings are included in every language we support OR there's an issue requesting new translations
- [ ] There are no hard-coded numbers referring to dimensions or font sizes
- [ ] The code considers UI elements that are similar and extracts their similarities into styles to avoid duplication

## Merge permissions
<!-- MANDATORY: Is anybody else allowed to merge this? If so, who? -->