# MatchIt

MatchIt is a Java desktop application for creating profiles and finding people by their skills. It uses Swing for the interface and MySQL for storing users and skills.

## Features

- Register with a name, email, and password.
- Create a profile with a location, biography, and multiple skills.
- Log in with stored credentials.
- Browse users or filter by Java, Python, UI/Design, JavaScript, or SQL.
- Enter a goal, working style, and weekly availability in the profile form (these fields are not currently persisted).

## Requirements

- JDK 23, matching the included IntelliJ IDEA project configuration.
- A local MySQL server.
- MySQL Connector/J 8.0.11, included as `mysql-connector-java-8.0.11.jar`.
- IntelliJ IDEA.

## Database setup

The application expects a database named `matchy` at `localhost:3306`. It does not create its tables automatically. Run the following SQL in your MySQL client against a fresh database. 

```sql
CREATE DATABASE matchy CHARACTER SET utf8mb4;
USE matchy;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    bio TEXT,
    goal VARCHAR(50),
    working_style VARCHAR(50),
    availability INT
);

CREATE TABLE skills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(100)
);

CREATE TABLE user_skills (
    user_id INT NOT NULL,
    skill_id INT NOT NULL,
    level VARCHAR(50) NOT NULL DEFAULT 'basic',
    PRIMARY KEY (user_id, skill_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (skill_id) REFERENCES skills(id)
);

INSERT INTO skills (name, category) VALUES
    ('Java', 'Programming'),
    ('Python', 'Programming'),
    ('UI/Design', 'Design'),
    ('JavaScript', 'Programming'),
    ('SQL', 'Databases');
```



