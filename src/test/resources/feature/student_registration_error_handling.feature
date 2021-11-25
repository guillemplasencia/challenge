Feature: Student registration errors

  Background:
    Given the application is running

  Scenario: Reject student registration when a student exists with same name
    Given student named 'xyz' successfully registers into courses "course A, course B"
    Then student named 'xyz' unsuccessfully registers into courses "course C"

  Scenario: Reject to delete student that does not exist
    Given student named 'xyz' successfully registers into courses "course A, course B"
    Given student named 'pqr' successfully registers into courses "course A, course B"
    Then student named 'abc' and its registered courses are unsuccessfully deleted

  Scenario: Course not found error when list students enrolled to a course that does not exist
    Given student named 'xyz' successfully registers into courses "course A, course B"
    And student named 'abc' successfully registers into courses "course B, course C"
    Then listing all students enrolled into course "course X" reports error http status 404

  Scenario Outline: Save score errors
    Given student named 'xyz' successfully registers into courses "course A, course B"
    Then saving student '<studentName>' score <score> of course '<courseName>' gives error with http status <error>

    Examples:
    |studentName | courseName  | score| error |
    | abc        | course A    | 5.0  | 404   |
    | xyz        | not_exists  | 5.0  | 404   |
    | xyz        | course A    | -5.0 | 400   |

  Scenario: List all students not enrolled to a course
    Given student named 'xyz' successfully registers into courses "course A, course B"
    And student named 'abc' successfully registers into courses "course B, course C"
    And student named 'def' successfully registers into courses "course D"
    Then list of students not enrolled in course 'course X' gives error with http status 404