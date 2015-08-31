# android-template
### Includes:
+ GSON (JSON -> Java Conversion)
+ RxJava (Observable Sequences)
+ Dagger (Dependency Injection)
+ ButterKnife (View Injection)
+ Timber (Logging)
+ Retrofit (REST Interface)
+ OkHttp (Http)
+ Picasso (Image Loading)
+ Crashlytics (Automated Crash Reporting)
+ LeakCanary (Runtime Memory Leak Tracker)
+ Robolectric (Unit Testing)
+ Hamcrest (Unit Testing)
+ Mockito (Mocking)
+ Also includes optional libraries commented out in gradle.build file

### Duplicating Git Repository
Follow the instructions to duplicate the boilerplate repository into your own so you can work from there without messing around with the boilerplate code and connect your new project to its own repository
+ Create a new repository in GitHub. Do NOT initialize with a README, .gitignore, or license
+ cd parent-directory-for-repo
+ mkdir foo
+ cd foo
+ git clone --bare https://github.com/username/old-repo.git
+ cd old-repo.git
+ git push --mirror https://github.com/username/new-repo.git
+ cd ..
+ rm -rf old-repo.git
+ cd ..
+ rm -rf foo
You should now have an exact duplicate of the boilerplate repository in your own new repository. Follow the Installation and Build instructions using this new repository

### Android Installation and Build Instructions: 
+ Follow the instructions in the documentation here: https://docs.google.com/a/connect.com/document/d/1EAkZXJkptuQtjctaop_gBsIeVPAOyYd9Acg8Gsn_7KI/edit?usp=sharing
+ If you receive an error from Crashlytics, download the plugin from here: https://fabric.io/downloads/android-studio and ask Steve to be added to the Connect organization

### Unit Tests (JUnit):
+ cd your-directory
+ ./gradlew testDebug --continue --debug --stacktrace

### Static Code Analysis (Lint):
+ cd your-directory
+ ./gradlew lint

### Continuous Integration with Ship.io:
+ Create a new job
+ Schedule for 'Commit Hook'
+ Add task to 'Build an Android Project' with task 'app:build'
+ Add task to 'Run Script' and choose 'bash' with unit test command
+ Add task to 'Run Script' and choose 'bash' with static code analysis command
