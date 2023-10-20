rootProject.name = "java-project"

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.1.10"
}

gitHooks {
    commitMsg { conventionalCommits() }
    preCommit {
        tasks("checkStyleMain", "checkStyleTest", "checkStyleIntegrationTest")
    }
    hook("pre-push") {
        tasks("test", "integrationTest", "snyk-test", "sonar")
    }
    createHooks()
}
