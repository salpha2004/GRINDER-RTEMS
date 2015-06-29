# GRINDER-RTEMS

This repository is an extension to [GRINDER] generic fault injection framework to support [RTEMS].

Hints:
  - GRINDER should be used as a Git Submodule. This is because GRINDER gets updated and this way facilitates merging new changes to your GRINDER submodule.
  - Apply GRINDER.patch to GRINDER directory.
  - GRINDER is a Maven project. Java 7u21 and Apache Maven 3.2.5 has been tested and is working.
  - You can make and run GRINDER as follows:
```sh
GRINDER$ mvn clean
GRINDER$ mvn install -Dmaven.test.skip=true
GRINDER$ mvn package -Dmaven.test.skip=true
GRINDER$ java -jar client/core/target/grinder.client.core-0.0.1-SNAPSHOT.jar
```

#### Revision history
| Version     | Date | Description   |
| :-------: | :----: | :---: |
| 1.0 | 26.06.15 |  Initial version    |

[GRINDER]:https://github.com/DEEDS-TUD/GRINDER
[RTEMS]:https://www.rtems.org/

