# TaskTracker

A Java console task journal with scheduled desktop notifications.

<p>
  <a href="https://buymeacoffee.com/lucky_1uck">
    <img src="https://img.shields.io/badge/Support_the_project-FFDD00?style=for-the-badge&amp;logo=buymeacoffee&amp;logoColor=black" alt="Support the project" width="260" height="40">
  </a>
</p>

## What it does

- Create a journal and add tasks with a name, description, start time and end time.
- List tasks or delete them by matching their names.
- Notify journal subscribers; the console entry point registers the Swing popup notifier.

## Run locally

Use a JDK with Java 8 support or newer and a desktop session for Swing notifications.
There is no Maven or Gradle build; compile directly from the repository root:

```bash
javac -encoding UTF-8 -d out -sourcepath src src/Main.java
java -cp out Main
```

Choose `create`, then use `add`, `show`, `delete` or `exit`. Dates use
`dd.MM.yyyy HH:mm:ss`, for example `20.12.2030 13:40:20`.

## Code map

| Location | Responsibility |
| --- | --- |
| [Main.java](src/Main.java) | Interactive console flow |
| [tracker/api](src/tracker/api) | Tasks, periods, journals and subscriber contracts |
| [tracker/impl](src/tracker/impl) | Journal and task management |
| [subscribers](src/subscribers) | Popup, file and browser notification implementations |

## Persistence

This is an early desktop exercise. The `load` command uses a machine-specific
path in `Main.java`, and saving on exit is commented out. Use `create` to try the
current console flow; adapt persistence before relying on it to retain tasks.

## ☕ Support the project

If this project helps you learn or build, you can support its development.

<p>
  <a href="https://buymeacoffee.com/lucky_1uck">
    <img src="https://img.shields.io/badge/Support_the_project-FFDD00?style=for-the-badge&amp;logo=buymeacoffee&amp;logoColor=black" alt="Support the project" width="260" height="40">
  </a>
</p>
