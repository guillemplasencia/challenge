Feature: Student registration

  Background:
    Given the application is running

  Scenario: Add new student along with her course registrations
    Then student named 'xyz' successfully registers into courses "course A, course B"

  Scenario: Delete a student
    Given student named 'xyz' successfully registers into courses "course A, course B"
    Then student named 'xyz' and its registered courses are successfully deleted

  Scenario: Get all students enrolled to a course, sorted by student name
    Given student named 'xyz' successfully registers into courses "course A, course B"
    And student named 'abc' successfully registers into courses "course B, course C"
    Then listing all students enrolled into course "course B" shows:
    | abc       |
    | xyz       |

  Scenario: Save student course scores
    Given student named 'xyz' successfully registers into courses "course A, course B"
    Then student score '5.0' of course 'course A' is successfully saved

  Scenario: List all students not enrolled to a course
    Given student named 'xyz' successfully registers into courses "course A, course B"
    And student named 'abc' successfully registers into courses "course B, course C"
    And student named 'def' successfully registers into courses "course D"
    Then list of students not enrolled in course 'course A' shows:
    |studentName|
    |    abc    |
    |    def    |