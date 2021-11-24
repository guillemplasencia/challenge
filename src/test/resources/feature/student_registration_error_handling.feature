Feature: Student registration errors

  Background:
    Given the application is running

  Scenario: Reject student registration when a student exists with same name
    Given student named 'xyz' successfully registers into courses "course A, course B"
    Then student named 'xyz' unsuccessfully registers into courses "course C"

  @WorkInProgress
  Scenario: Reject to delete student that does not exist
    Given student named 'xyz' successfully registers into courses "course A, course B"
    Then student named 'abc' and its registered courses are unsuccessfully deleted

  @WorkInProgress
  Scenario: Course not found error when list students enrolled to a course that does not exist
    Given student named 'xyz' successfully registers into courses "course A, course B"
    And student named 'abc' successfully registers into courses "course B, course C"
    Then listing all students enrolled into course "course X" reports error 'Course not found'

  @WorkInProgress
  Scenario Outline: Save score errors
    Given student named 'xyz' successfully registers into courses "course A, course B"
    Then student <studentName> score <score> of course <courseName> is unsuccessfully saved, with error <error>

    Examples:
    |studentName | courseName  | score| error                |
    | not_exists | course A    | 5.0  | student not exists   |
    | xyz        | not_exists  | 5.0  | course not exists    |
    | xys        | course A    | -5.0 | invalid score        |

  @WorkInProgress
  Scenario: List all students not enrolled to a course
    Given student named 'xyz' successfully registers into courses "course A, course B"
    And student named 'abc' successfully registers into courses "course B, course C"
    And student named 'def' successfully registers into courses "course D"
    Then list of students not enrolled in course 'course X' reports error 'Course not found'