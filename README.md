# GRINDER-RTEMS

This repository is an extension to [GRINDER] generic fault injection framework to support [RTEMS].

GRINDER should be used as a Git Submodule. This is because GRINDER gets updated and this way facilitates merging new changes to your GRINDER submodule.

#### Build Instructions
**1.** Get Maven and JDK (Maven 3.2.5 and JDK 7u21 has been tested and is working).

**2.** Get MySQL Server.

**3.** Run MySQL Server and create a user and a database:
```sh
bash$ mysql -u root
mysql> create user 'grinder'@'localhost' identified by 'grinder';
mysql> grant all privileges on *.* to 'grinder'@'localhost';
mysql> create database grinder;
mysql> quit;
```
**4.** Build grinder-rtems:
```sh
grinder-rtems$ mvn clean install package
```
**5.** Apply GRINDER.patch to GRINDER:
```sh
bash$ cd GRINDER
GRINDER$ patch -p1 < ../GRINDER.patch
```
**6.** Build and run GRINDER:
```sh
GRINDER$ mvn clean install package -Dmaven.test.skip=true
GRINDER$ java -jar client/core/target/grinder.client.core-0.0.1-SNAPSHOT.jar
```

#### Revision history
| Version     | Date | Description   |
| :-------: | :----: | :---: |
| 1.1 | 30.06.15 | Added and updated Build Instructions section |
| 1.0 | 26.06.15 |  Initial version    |


[GRINDER]:https://github.com/DEEDS-TUD/GRINDER
[RTEMS]:https://www.rtems.org/

